/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.ide.inspections.lints

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import org.rust.ide.inspections.RsProblemsHolder
import org.rust.lang.core.psi.RsPatFieldFull
import org.rust.lang.core.psi.RsPsiFactory
import org.rust.lang.core.psi.RsVisitor

class RsNonShorthandFieldPatternsInspection : RsLintInspection() {
    override fun getLint(element: PsiElement): RsLint = RsLint.NonShorthandFieldPatterns

    override fun buildVisitor(holder: RsProblemsHolder, isOnTheFly: Boolean): RsVisitor = object : RsVisitor() {
        override fun visitPatFieldFull(o: RsPatFieldFull) {
            val identifier = o.identifier?.text ?: return
            val binding = o.pat.text
            if (identifier != binding) return

            holder.registerLintProblem(
                o,
                "The `$identifier:` in this pattern is redundant",
                object : LocalQuickFix {
                    override fun getFamilyName(): String = "Use shorthand field pattern: `$identifier`"

                    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
                        val field = descriptor.psiElement as RsPatFieldFull
                        val patBinding = RsPsiFactory(field.project).createPatBinding(field.pat.text)
                        field.parent.addBefore(patBinding, field)
                        field.delete()
                    }
                }
            )
        }
    }

}
