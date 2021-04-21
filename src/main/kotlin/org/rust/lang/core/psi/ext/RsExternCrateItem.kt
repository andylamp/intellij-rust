/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.lang.core.psi.ext

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.search.SearchScope
import com.intellij.psi.stubs.IStubElementType
import org.rust.ide.icons.RsIcons
import org.rust.lang.core.macros.RsExpandedElement
import org.rust.lang.core.psi.RsExternCrateItem
import org.rust.lang.core.psi.RsPsiImplUtil
import org.rust.lang.core.resolve.ref.RsExternCrateReferenceImpl
import org.rust.lang.core.resolve.ref.RsReference
import org.rust.lang.core.stubs.RsExternCrateItemStub

val RsExternCrateItem.nameWithAlias: String get() = alias?.name ?: referenceName
val RsExternCrateItemStub.nameWithAlias: String get() = alias?.name ?: name

val RsExternCrateItem.hasMacroUse: Boolean get() = EXTERN_CRATE_HAS_MACRO_USE_PROP.getByPsi(this)

val EXTERN_CRATE_HAS_MACRO_USE_PROP: StubbedAttributeProperty<RsExternCrateItem, RsExternCrateItemStub> =
    StubbedAttributeProperty({ it.hasAttribute("macro_use") }, RsExternCrateItemStub::mayHaveMacroUse)

abstract class RsExternCrateItemImplMixin : RsStubbedNamedElementImpl<RsExternCrateItemStub>,
                                            RsExternCrateItem {

    constructor(node: ASTNode) : super(node)

    constructor(stub: RsExternCrateItemStub, elementType: IStubElementType<*, *>) : super(stub, elementType)

    override fun getReference(): RsReference = RsExternCrateReferenceImpl(this)

    override val referenceNameElement: PsiElement get() = checkNotNull(identifier ?: self) {
        "Extern crate must contain identifier: $this $text at ${containingFile.virtualFile.path}"
    }

    override val referenceName: String get() = greenStub?.name ?: super.referenceName

    override fun getName(): String? = referenceName

    override fun getNameIdentifier(): PsiElement? = referenceNameElement

    override fun getIcon(flags: Int) = RsIcons.CRATE

    override fun getContext(): PsiElement? = RsExpandedElement.getContextImpl(this)

    override fun getUseScope(): SearchScope = RsPsiImplUtil.getDeclarationUseScope(this) ?: super.getUseScope()
}
