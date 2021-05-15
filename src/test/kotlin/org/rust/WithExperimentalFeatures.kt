/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust

import java.lang.annotation.Inherited

/**
 * @see org.rust.ide.experiments.RsExperiments
 * @see org.rust.openapiext.runWithEnabledFeatures
 */
@Inherited
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class WithExperimentalFeatures(vararg val features: String)
