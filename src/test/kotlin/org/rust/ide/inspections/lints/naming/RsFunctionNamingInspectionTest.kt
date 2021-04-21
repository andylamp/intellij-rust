/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.ide.inspections.lints.naming

import org.rust.ide.inspections.RsInspectionsTestBase
import org.rust.ide.inspections.lints.RsFunctionNamingInspection

class RsFunctionNamingInspectionTest : RsInspectionsTestBase(RsFunctionNamingInspection::class) {
    fun `test functions`() = checkByText("""
        fn fn_ok() {}
        fn <warning descr="Function `FN_BAR` should have a snake case name such as `fn_bar`">FN_BAR</warning>() {}

        extern "C" {
            fn OK();
        }
    """)

    fun `test functions suppression`() = checkByText("""
        #[allow(non_snake_case)]
        fn FN_BAR() {}
    """)

    fun `test functions fix`() = checkFixByText("Rename to `fun_foo`", """
        fn <warning descr="Function `FUN_FOO` should have a snake case name such as `fun_foo`">F<caret>UN_FOO</warning>() {}
        fn fun_use() {
            FUN_FOO();
        }
    """, """
        fn fun_foo() {}
        fn fun_use() {
            fun_foo();
        }
    """)

    fun `test function with raw identifier`() = checkFixByText("Rename to `extern`", """
        fn <warning descr="Function `Extern` should have a snake case name such as `extern`">r#Extern/*caret*/</warning>() {}
        fn main() {
            r#Extern();
        }
    """, """
        fn r#extern() {}
        fn main() {
            r#extern();
        }
    """)

    fun `test no_mangle function`() = checkByText("""
        #[no_mangle]
        pub fn <warning descr="Function `Foo` should have a snake case name such as `foo`">Foo</warning>() {}
    """)

    fun `test no_mangle extern function`() = checkByText("""
        #[no_mangle]
        pub unsafe extern fn Foo() {}
    """)

    fun `test functions not support case`() = checkByText("""
        fn 函数() {}

        extern "C" {
            fn 函数();
        }
    """)
}
