/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rustSlowTests

import com.intellij.lang.annotation.HighlightSeverity
import org.intellij.lang.annotations.Language
import org.rust.cargo.RsWithToolchainTestBase
import org.rust.cargo.project.model.cargoProjects
import org.rust.cargo.project.settings.rustSettings
import org.rust.cargo.project.workspace.FeatureState
import org.rust.cargo.project.workspace.PackageFeature
import org.rust.cargo.project.workspace.PackageOrigin
import org.rust.cargo.toolchain.ExternalLinter
import org.rust.fileTree
import org.rust.ide.annotator.RsExternalLinterUtils
import org.rust.singleProject
import org.rust.singleWorkspace
import org.rust.workspaceOrFail

class RsExternalLinterPassTest : RsWithToolchainTestBase() {

    override fun setUp() {
        super.setUp()
        project.rustSettings.modifyTemporary(testRootDisposable) { it.runExternalLinterOnTheFly = true }
    }

    fun `test no errors if everything is ok`() = doTest("""
        fn main() { println!("Hello, World!"); }
    """)

    fun `test highlights type errors`() = doTest("""
        struct X; struct Y;
        fn main() {
            let _: X = <error descr="${RsExternalLinterUtils.TEST_MESSAGE}">Y</error>;
        }
    """)

    fun `test highlights errors from macro`() = doTest("""
        fn main() {
            let mut x = 42;
            let r = &mut x;
            <error descr="${RsExternalLinterUtils.TEST_MESSAGE}">dbg!(x);</error>
            dbg!(r);
        }
    """)

    fun `test highlights errors in tests`() = doTest("""
        fn main() {}

        #[test]
        fn test() {
            let x: i32 = <error descr="${RsExternalLinterUtils.TEST_MESSAGE}">0.0</error>;
        }
    """)

    fun `test highlights clippy errors`() = doTest("""
        fn main() {
            <weak_warning descr="${RsExternalLinterUtils.TEST_MESSAGE}">if true { true } else { false }</weak_warning>;
        }
    """, externalLinter = ExternalLinter.CLIPPY)

    fun `test workspace features`() = doTest("""
        fn main() {}

        #[cfg(feature = "enabled_feature")]
        fn foo() {
            let x: i32 = <error descr="${RsExternalLinterUtils.TEST_MESSAGE}">0.0</error>;
        }

        #[cfg(feature = "disabled_feature")]
        fn foo() {
            let x: i32 = 0.0;
        }
    """)

    fun `test highlights from other files do not interfer`() {
        fileTree {
            toml("Cargo.toml", """
                [package]
                name = "hello"
                version = "0.1.0"
                authors = []
            """)

            dir("src") {
                rust("main.rs", "mod foo; fn main() {}")
                rust("foo.rs", """
                    struct X; struct Y;
                    fn foo() {
                        let _: X = Y;
                    }
                """)
            }
        }.create()
        myFixture.openFileInEditor(cargoProjectDirectory.findFileByRelativePath("src/main.rs")!!)
        val highlights = myFixture.doHighlighting(HighlightSeverity.WEAK_WARNING)
        check(highlights.isEmpty()) {
            "Did not expect any highlights, got:\n$highlights"
        }
    }

    fun `test don't try to highlight non project files`() {
        fileTree {
            toml("Cargo.toml", """
                [package]
                name = "hello"
                version = "0.1.0"
                authors = []

                [dependencies]
                rand = "0.3.15"
            """)

            dir("src") {
                rust("lib.rs", """
                    extern crate rand;

                    fn foo() {
                        let a: i32 = "
                        ";
                    }
                """)
            }
        }.create()

        val path = project.cargoProjects.singleWorkspace()
            .findPackageByName("rand")
            ?.contentRoot
            ?.findFileByRelativePath("src/lib.rs")
            ?: error("Can't find 'src/lib.rs' in 'rand' library")
        myFixture.openFileInEditor(path)

        val highlights = myFixture.doHighlighting(HighlightSeverity.ERROR)
        check(highlights.isEmpty()) {
            "Did not expect any highlights, got:\n$highlights"
        }
    }

    // https://github.com/intellij-rust/intellij-rust/issues/2503
    fun `test unique errors`() {
        fileTree {
            toml("Cargo.toml", """
                [package]
                name = "hello"
                version = "0.1.0"
                authors = []
            """)

            dir("src") {
                file("main.rs", """
                    fn main() {
                        let xs = ["foo", "bar"];
                        for x in xs {}
                    }
                """)
            }
        }.create()
        myFixture.openFileInEditor(cargoProjectDirectory.findFileByRelativePath("src/main.rs")!!)
        val highlights = myFixture.doHighlighting(HighlightSeverity.ERROR)
            .filter { it.description == RsExternalLinterUtils.TEST_MESSAGE }
        check(highlights.size == 1) {
            "Expected only one error highlights from `RsExternalLinterPass`, got:\n$highlights"
        }
    }

    private fun doTest(
        @Language("Rust") mainRs: String,
        externalLinter: ExternalLinter = ExternalLinter.DEFAULT
    ) {
        project.rustSettings.modifyTemporary(testRootDisposable) { it.externalLinter = externalLinter }
        fileTree {
            toml("Cargo.toml", """
                [package]
                name = "hello"
                version = "0.1.0"
                authors = []

                [features]
                disabled_feature = []
                enabled_feature = []
                enabled2_feature = []
            """)

            dir("src") {
                file("main.rs", mainRs)
            }
        }.create()
        val cargoProject = project.cargoProjects.singleProject()
        val pkg = cargoProject.workspaceOrFail().packages.single { it.origin == PackageOrigin.WORKSPACE }
        project.cargoProjects.modifyFeatures(cargoProject, setOf(PackageFeature(pkg, "disabled_feature")), FeatureState.Disabled)
        myFixture.openFileInEditor(cargoProjectDirectory.findFileByRelativePath("src/main.rs")!!)
        myFixture.checkHighlighting()
    }
}
