/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.cargo.util

import com.intellij.openapi.util.SystemInfo
import org.rust.stdext.isExecutable
import java.nio.file.Path

fun Path.hasExecutable(toolName: String): Boolean = pathToExecutable(toolName).isExecutable()

fun Path.pathToExecutable(toolName: String): Path {
    val exeName = if (SystemInfo.isWindows) "$toolName.exe" else toolName
    return resolve(exeName).toAbsolutePath()
}
