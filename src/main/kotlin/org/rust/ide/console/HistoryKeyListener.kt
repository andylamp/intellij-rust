/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.ide.console

import com.intellij.codeInsight.lookup.LookupManager
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.ex.util.EditorUtil
import com.intellij.openapi.project.Project
import org.rust.openapiext.runWriteCommandAction
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import kotlin.math.max
import kotlin.math.min

class HistoryKeyListener(
    private val project: Project,
    private val consoleEditor: EditorEx,
    private val history: CommandHistory
) : KeyAdapter(), HistoryUpdateListener {

    private var historyPos: Int = 0
    private var prevCaretOffset: Int = -1
    private var unfinishedCommand: String = ""

    override fun onNewEntry(entry: CommandHistory.Entry) {
        // reset history positions
        historyPos = history.size
        prevCaretOffset = -1
        unfinishedCommand = ""
    }

    private enum class HistoryMove {
        UP, DOWN
    }

    override fun keyReleased(e: KeyEvent) {
        if (e.isShiftDown || e.isAltDown) return
        when (e.keyCode) {
            KeyEvent.VK_UP -> moveHistoryCursor(HistoryMove.UP)
            KeyEvent.VK_DOWN -> moveHistoryCursor(HistoryMove.DOWN)
            KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> prevCaretOffset = consoleEditor.caretModel.offset
        }
    }

    private fun moveHistoryCursor(move: HistoryMove) {
        if (history.size == 0) return
        if (LookupManager.getInstance(project).activeLookup != null) return

        val caret = consoleEditor.caretModel
        val document = consoleEditor.document

        val curOffset = caret.offset
        val curLine = document.getLineNumber(curOffset)
        val totalLines = document.lineCount
        val isMultiline = totalLines > 1

        when (move) {
            HistoryMove.UP -> {
                if (curLine != 0 || (isMultiline && prevCaretOffset != 0)) {
                    prevCaretOffset = curOffset
                    return
                }

                if (historyPos == history.size) {
                    unfinishedCommand = document.text
                }

                historyPos = max(historyPos - 1, 0)
                project.runWriteCommandAction {
                    document.setText(history[historyPos].entryText)
                    EditorUtil.scrollToTheEnd(consoleEditor)
                    prevCaretOffset = 0
                    caret.moveToOffset(0)
                }
            }
            HistoryMove.DOWN -> {
                if (historyPos == history.size) return

                if (curLine != totalLines - 1 || (isMultiline && prevCaretOffset != document.textLength)) {
                    prevCaretOffset = curOffset
                    return
                }

                historyPos = min(historyPos + 1, history.size)
                project.runWriteCommandAction {
                    val command =
                        if (historyPos == history.size) unfinishedCommand
                        else history[historyPos].entryText
                    document.setText(command)
                    prevCaretOffset = document.textLength
                    EditorUtil.scrollToTheEnd(consoleEditor)
                }
            }
        }
    }
}
