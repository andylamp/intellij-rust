/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.ide.formatter

class RsSingleImportRemoveBracesFormatProcessorTest : RsFormatterTestBase() {

    fun `test remove braces if single import`() = doTextTest("use getopts::{optopt};", "use getopts::optopt;")

    fun `test remove braces if single import with extra comma`() =
        doTextTest("use getopts::{optopt,};", "use getopts::optopt;")

    fun `test won't remove braces if import with left comment`() = checkNotChanged("use getopts::{/*comment*/optopt};")

    fun `test won't remove braces if import with right comment`() = checkNotChanged("use getopts::{optopt/*comment*/};")

    fun `test wont remove braces if multi import`() = checkNotChanged(
        "use getopts::{optopt, optarg};"
    )

    fun `test won't remove braces for single self`() = checkNotChanged(
        "use getopts::{self};"
    )

    fun `test remove braces with multiple imports`() = doTextTest(
        """
        use getopts::{optopt};
        use std::io::{self, Read, Write};
        use std::Vec::{Vec};
        use std::Vec::{Vec,};
    """, """
        use getopts::optopt;
        use std::io::{self, Read, Write};
        use std::Vec::Vec;
        use std::Vec::Vec;
    """)
}
