/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.toml.inspections

import org.rust.ide.inspections.RsInspectionsTestBase
import org.toml.ide.inspections.TomlUnresolvedReferenceInspection

class CargoTomlUnresolvedPathReferenceInspectionTest : RsInspectionsTestBase(TomlUnresolvedReferenceInspection::class) {
    fun `test build script found`() = checkByFileTree("""
        //- main.rs
        fn main() {}

        //- foo.rs
        fn foo() {}

        //- Cargo.toml
        [package]
        name = "example"
        build = "foo.rs"/*caret*/
    """)

    fun `test build script not found`() = checkByFileTree("""
        //- main.rs
        fn main() {}

        //- Cargo.toml
        [package]
        name = "example"
        build = "<warning descr="Cannot resolve file 'foo.rs'">foo.rs</warning>"/*caret*/
    """)

    fun `test build script directory`() = checkByFileTree("""
        //- main.rs
        fn main() {}

        //- bar/foo.rs
        fn foo() {}

        //- Cargo.toml
        [package]
        name = "example"
        build = "bar"/*caret*/
    """)

    fun `test workspace member found`() = checkByFileTree("""
        //- foo/Cargo.toml
        [package]
        name = "foo"

        //- Cargo.toml
        [workspace]
        members = [
            "foo"/*caret*/
        ]
    """)

    fun `test workspace member not found`() = checkByFileTree("""
        //- Cargo.toml
        [workspace]
        members = [
            "<warning descr="Cannot resolve file 'foo'">foo</warning>"/*caret*/
        ]
    """)

    fun `test workspace member file`() = checkByFileTree("""
        //- foo.rs
        fn foo() {}

        //- Cargo.toml
        [workspace]
        members = [
            "foo.rs"/*caret*/
        ]
    """)

    fun `test dependency member found`() = checkByFileTree("""
        //- foo/Cargo.toml
        [package]
        name = "foo"

        //- Cargo.toml
        [dependencies]
        foo = { path = "foo" }/*caret*/
    """)

    fun `test dependency member not found`() = checkByFileTree("""
        //- Cargo.toml
        [dependencies]
        foo = { path = "<warning descr="Cannot resolve file 'foo'">foo</warning>" }/*caret*/
    """)

    fun `test dependency member file`() = checkByFileTree("""
        //- foo.rs
        fn foo() {}

        //- Cargo.toml
        [dependencies]
        foo = { path = "foo.rs" }/*caret*/
    """)
}
