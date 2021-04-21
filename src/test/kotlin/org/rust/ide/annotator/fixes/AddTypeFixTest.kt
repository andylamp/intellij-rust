/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.ide.annotator.fixes

import org.rust.ide.annotator.RsAnnotatorTestBase
import org.rust.ide.annotator.RsSyntaxErrorsAnnotator

class AddTypeFixTest : RsAnnotatorTestBase(RsSyntaxErrorsAnnotator::class) {
    fun `test const`() = checkFixByText("Add type i32", """
        <error>const CONST/*caret*/ = 1;</error>
    """, """
        const CONST: i32/*caret*/ = 1;
    """)

    fun `test static`() = checkFixByText("Add type i32", """
        <error>static STATIC/*caret*/ = 1;</error>
    """, """
        static STATIC: i32/*caret*/ = 1;
    """)

    fun `test unknown type`() = checkFixByText("Add type _", """
        <error>const CONST/*caret*/ = S;</error>
    """, """
        const CONST: _/*caret*/ = S;
    """)

    fun `test partially unknown type`() = checkFixByText("Add type (_, _)", """
        <error>const CONST/*caret*/ = (S, S);</error>
    """, """
        const CONST: (_, _)/*caret*/ = (S, S);
    """)

    fun `test missing expr`() = checkFixIsUnavailable("Add type", """
        <error>const CONST/*caret*/;</error>
    """)
}
