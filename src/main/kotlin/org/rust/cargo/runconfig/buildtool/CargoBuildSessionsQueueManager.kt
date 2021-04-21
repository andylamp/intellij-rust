/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.cargo.runconfig.buildtool

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.progress.BackgroundTaskQueue
import com.intellij.openapi.project.Project

@Service
class CargoBuildSessionsQueueManager(project: Project) {
    val buildSessionsQueue: BackgroundTaskQueue = BackgroundTaskQueue(project, "Building...")

    companion object {
        fun getInstance(project: Project): CargoBuildSessionsQueueManager = project.service()
    }
}
