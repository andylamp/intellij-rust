/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.ide.inspections

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ui.MultipleCheckboxOptionsPanel
import com.intellij.psi.PsiElementVisitor
import org.rust.ide.inspections.import.AutoImportFix
import org.rust.ide.inspections.import.AutoImportHintFix
import org.rust.ide.settings.RsCodeInsightSettings
import org.rust.lang.core.psi.RsMetaItem
import org.rust.lang.core.psi.RsMethodCall
import org.rust.lang.core.psi.RsPath
import org.rust.lang.core.psi.RsVisitor
import org.rust.lang.core.psi.ext.RsReferenceElement
import org.rust.lang.core.psi.ext.ancestorStrict
import org.rust.lang.core.psi.ext.isUnresolved
import org.rust.lang.core.psi.ext.qualifier
import javax.swing.JComponent

class RsUnresolvedReferenceInspection : RsLocalInspectionTool() {

    var ignoreWithoutQuickFix: Boolean = true

    override fun getDisplayName() = "Unresolved reference"

    override fun buildVisitor(holder: RsProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor =
        object : RsVisitor() {

            override fun visitPath(path: RsPath) {
                if (path.reference == null) return

                // Don't show unresolved reference error in attributes for now
                if (path.ancestorStrict<RsMetaItem>() != null) return

                val isPathUnresolved = path.isUnresolved
                val qualifier = path.qualifier

                val context = when {
                    qualifier == null && isPathUnresolved -> AutoImportFix.findApplicableContext(holder.project, path)
                    qualifier != null && isPathUnresolved -> {
                        // There is not sense to highlight path as unresolved
                        // if qualifier cannot be resolved as well
                        if (qualifier.isUnresolved) return
                        null
                    }
                    // Despite the fact that path is (multi)resolved by our resolve engine, it can be unresolved from
                    // the view of the rust compiler. Specifically we resolve associated items even if corresponding
                    // trait is not in the scope, so here we suggest importing such traits
                    (qualifier != null || path.typeQual != null) && !isPathUnresolved ->
                        AutoImportFix.findApplicableContextForAssocItemPath(holder.project, path)
                    else -> null
                }

                if (isPathUnresolved || context != null) {
                    holder.registerProblem(path, context)
                }
            }

            override fun visitMethodCall(methodCall: RsMethodCall) {
                val isMethodResolved = methodCall.reference.multiResolve().isNotEmpty()
                val context = AutoImportFix.findApplicableContext(holder.project, methodCall)

                if (!isMethodResolved || context != null) {
                    holder.registerProblem(methodCall, context)
                }
            }
        }

    private fun RsProblemsHolder.registerProblem(
        element: RsReferenceElement,
        context: AutoImportFix.Context?
    ) {
        val candidates = context?.candidates
        if (candidates.isNullOrEmpty() && ignoreWithoutQuickFix) return

        val referenceName = element.referenceName
        val description = if (referenceName == null) "Unresolved reference" else "Unresolved reference: `$referenceName`"
        var fix: LocalQuickFix? = null
        if (candidates != null && candidates.isNotEmpty()) {
            fix = if (RsCodeInsightSettings.getInstance().showImportPopup) {
                AutoImportHintFix(element, context.type, candidates[0].info.usePath, candidates.size > 1)
            } else {
                AutoImportFix(element, context.type)
            }
        }

        val highlightedElement = element.referenceNameElement ?: element
        registerProblem(
            highlightedElement,
            description,
            ProblemHighlightType.LIKE_UNKNOWN_SYMBOL,
            *listOfNotNull(fix).toTypedArray()
        )
    }

    override fun createOptionsPanel(): JComponent = MultipleCheckboxOptionsPanel(this).apply {
        addCheckbox("Ignore unresolved references without quick fix", "ignoreWithoutQuickFix")
    }
}
