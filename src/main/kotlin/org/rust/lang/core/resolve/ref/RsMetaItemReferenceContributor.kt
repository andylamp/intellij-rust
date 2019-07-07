/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.lang.core.resolve.ref

import com.intellij.psi.*
import com.intellij.util.ProcessingContext
import org.rust.lang.core.RsPsiPattern
import org.rust.lang.core.psi.RsFunction
import org.rust.lang.core.psi.RsMetaItem
import org.rust.lang.core.psi.RsTraitItem
import org.rust.lang.core.psi.ext.RsElement
import org.rust.lang.core.psi.ext.name
import org.rust.lang.core.resolve.collectResolveVariants
import org.rust.lang.core.resolve.processAttributeProcMacroResolveVariants
import org.rust.lang.core.resolve.processDeriveTraitResolveVariants

class RsMetaItemReferenceContributor : PsiReferenceContributor() {

    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            RsPsiPattern.derivedTraitMetaItem,
            RsDeriveTraitReferenceProvider(),
            PsiReferenceRegistrar.DEFAULT_PRIORITY
        )
        // FIXME: We assume that attribute proc macros are used only as top level attributes
        // (so we ignore the fact that attribute proc macro can be nested inside `cfg_attr`)
        registrar.registerReferenceProvider(
            RsPsiPattern.nonStdOuterAttributeMetaItem,
            RsAttributeProcMacroReferenceProvider(),
            PsiReferenceRegistrar.LOWER_PRIORITY
        )
    }
}

class RsAttributeProcMacroReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        val metaItem = element as? RsMetaItem ?: return PsiReference.EMPTY_ARRAY
        return arrayOf(RsAttributeProcMacroReferenceImpl(metaItem))
    }
}

class RsAttributeProcMacroReferenceImpl(element: RsMetaItem) : RsReferenceBase<RsMetaItem>(element) {
    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> =
        cachedMultiResolve().toTypedArray()

    override val RsMetaItem.referenceAnchor: PsiElement? get() = element.identifier

    override fun isReferenceTo(element: PsiElement): Boolean =
        element is RsFunction && super.isReferenceTo(element)

    override fun multiResolve(): List<RsElement> =
        cachedMultiResolve().mapNotNull { it.element as? RsElement }

    private fun cachedMultiResolve(): List<PsiElementResolveResult> {
        return RsResolveCache.getInstance(element.project)
            .resolveWithCaching(element, ResolveCacheDependency.LOCAL_AND_RUST_STRUCTURE, Resolver).orEmpty()
    }

    private object Resolver : (RsMetaItem) -> List<PsiElementResolveResult> {
        override fun invoke(ref: RsMetaItem): List<PsiElementResolveResult> {
            return resolve(ref).map { PsiElementResolveResult(it) }
        }

        private fun resolve(element: RsMetaItem): List<RsElement> {
            val attributeMacroName = element.name ?: return emptyList()
            return collectResolveVariants(attributeMacroName) { processAttributeProcMacroResolveVariants(element, it) }
        }
    }
}

private class RsDeriveTraitReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        val metaItem = element as? RsMetaItem ?: return PsiReference.EMPTY_ARRAY
        return arrayOf(RsDeriveTraitReferenceImpl(metaItem))
    }
}

/**
 * Equivalent to [RsMetaItem.getReference] in the case of `#[derive(Foo)]` meta item, but with better
 * performance because it doesn't call contributors from other plugins.
 * (e.g. `MavenFilteredPropertyPsiReferenceProvider` performs switches to AST)
 */
val RsMetaItem.deriveReference: RsReference?
    get() = if (RsPsiPattern.derivedTraitMetaItem.accepts(this)) RsDeriveTraitReferenceImpl(this) else null

private class RsDeriveTraitReferenceImpl(
    element: RsMetaItem
) : RsReferenceBase<RsMetaItem>(element) {

    override val RsMetaItem.referenceAnchor: PsiElement? get() = element.identifier

    fun resolveInner(): List<RsElement> {
        val traitName = element.name ?: return emptyList()
        return collectResolveVariants(traitName) { processDeriveTraitResolveVariants(element, traitName, it) }
    }

    override fun isReferenceTo(element: PsiElement): Boolean =
        (element is RsTraitItem || element is RsFunction) && super.isReferenceTo(element)

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> =
        cachedMultiResolve().toTypedArray()

    override fun multiResolve(): List<RsElement> =
        cachedMultiResolve().mapNotNull { it.element as? RsElement }

    private fun cachedMultiResolve(): List<PsiElementResolveResult> {
        return RsResolveCache.getInstance(element.project)
            .resolveWithCaching(element, ResolveCacheDependency.LOCAL_AND_RUST_STRUCTURE, Resolver).orEmpty()
    }

    private object Resolver : (RsMetaItem) -> List<PsiElementResolveResult> {
        override fun invoke(ref: RsMetaItem): List<PsiElementResolveResult> {
            return (ref.deriveReference as RsDeriveTraitReferenceImpl).resolveInner().map { PsiElementResolveResult(it) }
        }
    }
}
