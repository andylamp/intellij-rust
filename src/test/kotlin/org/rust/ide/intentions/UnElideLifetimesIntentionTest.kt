/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.ide.intentions

class UnElideLifetimesIntentionTest : RsIntentionTestBase(UnElideLifetimesIntention::class) {
    fun `test unavailable without references`() = doUnavailableTest("""
        fn bar/*caret*/(x: i32) -> i32 {}
    """)

    fun `test unavailable in block body`() = doUnavailableTest("""
        fn bar(x: &i32) {/*caret*/}
    """)

    fun `test unavailable in doc comment`() = doUnavailableTest("""
        /// ```
        /// /*caret*/
        /// ```
        fn bar(x: &i32) {}
    """)

    fun `test unavailable without args`() = doUnavailableTest("""
        fn bar(/*caret*/) {}
    """)

    fun `test unavailable with explicit lifetime`() = doUnavailableTest("""
        fn bar<'a>(x: &'a /*caret*/ i32) {}
    """)

    fun `test simple`() = doAvailableTest("""
        fn foo(p: &/*caret*/ i32) -> & i32 { p }
    """, """
        fn foo<'a>(p: &/*caret*/'a i32) -> &'a i32 { p }
    """)

    fun `test mut ref`() = doAvailableTest("""
        fn foo(p: &/*caret*/mut i32) -> & i32 { p }
    """, """
        fn foo<'a>(p: &/*caret*/'a mut i32) -> &'a i32 { p }
    """)

    fun `test nested ref`() = doAvailableTest("""
        fn foo(p: &&/*caret*/ i32) -> & i32 { unimplemented!() }
    """, """
        fn foo<'a>(p: &'a &/*caret*/i32) -> &'a i32 { unimplemented!() }
    """)

    fun `test generic type`() = doAvailableTest("""
        fn foo<T>(p1:/*caret*/ &i32, p2: T) -> & i32 { p }
    """, """
        fn foo<'a, T>(p1:/*caret*/ &'a i32, p2: T) -> &'a i32 { p }
    """)

    fun `test lifetime type as parameter`() = doAvailableTest("""
        struct S<'a> { x: &'a u32 }
        fn make_s(x:/*caret*/ S) { unimplemented!() }
    """, """
        struct S<'a> { x: &'a u32 }
        fn make_s<'a>(x:/*caret*/ S<'a>) { unimplemented!() }
    """)

    fun `test lifetime type as return value`() = doAvailableTest("""
        struct S<'a> { x: &'a i32 }
        fn make_s(x:/*caret*/ &i32) -> S { unimplemented!() }
    """, """
        struct S<'a> { x: &'a i32 }
        fn make_s<'a>(x:/*caret*/ &'a i32) -> S<'a> { unimplemented!() }
    """)

    fun `test struct parameter with multiple lifetimes`() = doAvailableTest("""
        struct S<'a, 'b> { x: &'a u32, y: &'b u32 }
        fn make_s(x:/*caret*/ S) { unimplemented!() }
    """, """
        struct S<'a, 'b> { x: &'a u32, y: &'b u32 }
        fn make_s<'a, 'b>(x:/*caret*/ S<'a, 'b>) { unimplemented!() }
    """)

    fun `test struct return type with multiple lifetimes`() = doUnavailableTest("""
        struct S<'a, 'b> { x: &'a u32, y: &'b u32 }
        fn make_s(x:/*caret*/ &i32) -> S { unimplemented!() }
    """)

    fun `test lifetime type complex struct`() = doAvailableTest("""
        struct S<'a, T, X> { x: &'a T, y: X }
        fn make_s<X>(x:/*caret*/ S<u32, X>) -> S<u32, X> { unimplemented!() }
    """, """
        struct S<'a, T, X> { x: &'a T, y: X }
        fn make_s<'a, X>(x:/*caret*/ S<'a, u32, X>) -> S<'a, u32, X> { unimplemented!() }
    """)

    fun `test unknown`() = doAvailableTest("""
        fn foo(p1: &i32,/*caret*/ p2: &i32) -> &i32 { p2 }
    """, """
        fn foo<'a, 'b>(p1: &'a i32, p2: &'b i32) -> &'<selection>_</selection> i32 { p2 }
    """)

    fun `test method decl`() = doAvailableTest("""
        trait Foo {
            fn /*caret*/bar(&self, x: &i32, y: &i32, z: i32) -> &i32;
        }
    """, """
        trait Foo {
            fn /*caret*/bar<'a, 'b, 'c>(&'a self, x: &'b i32, y: &'c i32, z: i32) -> &'a i32;
        }
    """)

    fun `test method impl`() = doAvailableTest("""
        trait Foo {
            fn bar(&self, x: &i32, y: &i32, z: i32) -> &i32;
        }
        struct S {}
        impl Foo for S {
            fn /*caret*/bar(&self, x: &i32, y: &i32, z: i32) -> &i32 {
                unimplemented!()
            }
        }
    """, """
        trait Foo {
            fn bar(&self, x: &i32, y: &i32, z: i32) -> &i32;
        }
        struct S {}
        impl Foo for S {
            fn /*caret*/bar<'a, 'b, 'c>(&'a self, x: &'b i32, y: &'c i32, z: i32) -> &'a i32 {
                unimplemented!()
            }
        }
    """)
}
