/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.cargo.runconfig.command

import com.intellij.openapi.actionSystem.AnActionEvent
import org.rust.cargo.icons.CargoIcons
import org.rust.cargo.runconfig.getAppropriateCargoProject
import org.rust.cargo.toolchain.CargoCommandLine
import org.rust.cargo.toolchain.run

class RunClippyAction : RunCargoCommandActionBase(CargoIcons.CLIPPY) {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val cargoProject = getAppropriateCargoProject(e.dataContext) ?: return
        CargoCommandLine.forProject(cargoProject, "clippy").run(project, cargoProject)
    }
}
