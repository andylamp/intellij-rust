/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.grazie

import com.intellij.grazie.GrazieConfig
import com.intellij.grazie.ide.inspection.grammar.GrazieInspection
import com.intellij.grazie.ide.language.LanguageGrammarChecking
import com.intellij.grazie.jlanguage.Lang
import com.intellij.openapi.application.ApplicationInfo
import com.intellij.openapi.util.BuildNumber
import com.intellij.openapi.util.Disposer
import com.intellij.testFramework.PlatformTestUtil
import org.intellij.lang.annotations.Language
import org.rust.IgnoreInPlatform
import org.rust.ide.annotator.RsAnnotationTestFixture
import org.rust.ide.inspections.RsInspectionsTestBase

class RsGrammarCheckingTest : RsInspectionsTestBase(GrazieInspection::class) {

    override fun createAnnotationFixture(): RsAnnotationTestFixture =
        RsAnnotationTestFixture(this, myFixture, inspectionClasses = listOf(inspectionClass), baseFileName = "lib.rs")

    override fun setUp() {
        super.setUp()
        val strategy = LanguageGrammarChecking.getStrategies().first { it is RsGrammarCheckingStrategy }
        val currentState = GrazieConfig.get()
        if (strategy.getID() !in currentState.enabledGrammarStrategies || currentState.enabledLanguages != enabledLanguages) {
            updateSettings { state ->
                state.copy(
                    enabledGrammarStrategies = state.enabledGrammarStrategies + strategy.getID(),
                    enabledLanguages = enabledLanguages
                )
            }
        }
        Disposer.register(testRootDisposable) {
            updateSettings { currentState }
        }
    }

    fun `test check literals`() = doTest("""
        fn foo() {
            let literal = "<TYPO>There is two apples</TYPO>";
            let raw_literal = r"<TYPO>There is two apples</TYPO>";
            let binary_literal = b"<TYPO>There is two apples</TYPO>";
        }
    """, checkInStringLiterals = true)

    fun `test check comments`() = doTest("""
        fn foo() {
            // <TYPO>There is two apples</TYPO>
            /* <TYPO>There is two apples</TYPO> */
            let literal = 123;
        }
    """, checkInComments = true)

    fun `test check doc comments`() = doTest("""
        /// <TYPO>There is two apples</TYPO>
        mod foo {
            //! <TYPO>There is two apples</TYPO>

            /** <TYPO>There is two apples</TYPO> */
            fn bar() {}
        }
    """, checkInDocumentation = true)

    // BACKCOMPAT: 2020.2. Proofreading was broken in grazie plugin for 2020.2 in injected code
    @IgnoreInPlatform(202)
    fun `test check injected Rust code in doc comments`() = doTest("""
        ///
        /// ```
        /// let literal = "<TYPO>There is two apples</TYPO>";
        /// for i in 1..10 {}
        /// ```
        pub fn foo() {}
    """, checkInStringLiterals = true)

    private fun doTest(
        @Language("Rust") text: String,
        checkInStringLiterals: Boolean = false,
        checkInComments: Boolean = false,
        checkInDocumentation: Boolean = false
    ) {
        updateSettings { state ->
            val newContext = state.checkingContext.copy(
                isCheckInStringLiteralsEnabled = checkInStringLiterals,
                isCheckInCommentsEnabled = checkInComments,
                isCheckInDocumentationEnabled = checkInDocumentation
            )
            state.copy(checkingContext = newContext)
        }
        checkByText(text)

        // BACKCOMPAT: 2020.2. GrazieInspection is always enabled for any element for 2020.2
        if (ApplicationInfo.getInstance().build >= BUILD_203) {
            updateSettings { state ->
                val newContext = state.checkingContext.copy(
                    isCheckInStringLiteralsEnabled = false,
                    isCheckInCommentsEnabled = false,
                    isCheckInDocumentationEnabled = false
                )
                state.copy(checkingContext = newContext)
            }

            checkByText(text.replace("<TYPO.*?>(.*?)</TYPO>".toRegex(), "$1"))
        }
    }

    private fun updateSettings(change: (GrazieConfig.State) -> GrazieConfig.State) {
        GrazieConfig.update(change)
        PlatformTestUtil.dispatchAllEventsInIdeEventQueue()
    }

    companion object {
        private val enabledLanguages = setOf(Lang.AMERICAN_ENGLISH)

        private val BUILD_203: BuildNumber = BuildNumber.fromString("203")!!
    }
}
