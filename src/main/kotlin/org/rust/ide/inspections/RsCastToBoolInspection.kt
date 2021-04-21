/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.ide.inspections

import org.rust.lang.core.psi.RsCastExpr
import org.rust.lang.core.psi.RsVisitor
import org.rust.lang.core.types.ty.TyBool
import org.rust.lang.core.types.ty.TyPrimitive
import org.rust.lang.core.types.ty.TyUnit
import org.rust.lang.core.types.type
import org.rust.lang.utils.RsDiagnostic
import org.rust.lang.utils.addToHolder

class RsCastToBoolInspection : RsLocalInspectionTool() {

    override fun buildVisitor(holder: RsProblemsHolder, isOnTheFly: Boolean) = object : RsVisitor() {
        override fun visitCastExpr(castExpr: RsCastExpr) {
            // It is allowed to cast a bool to a bool, so if the expression's type is of bool, ignore this cast.
            // Casts from non primitive types (and the unit type) emit another error, so we ignore those as well.
            val exprType = castExpr.expr.type
            if (exprType === TyBool || exprType !is TyPrimitive || exprType === TyUnit) return

            if (castExpr.typeReference.type === TyBool) {
                RsDiagnostic.CastAsBoolError(castExpr).addToHolder(holder)
            }
        }
    }

}
