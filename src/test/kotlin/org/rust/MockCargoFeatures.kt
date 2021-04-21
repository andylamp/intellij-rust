/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust

import java.lang.annotation.Inherited

/**
 * Allows setting cargo features for a specific test ([RsTestBase]).
 *
 * Use it like `@MockCargoFeatures("package_name/feature_name")`.
 * `package_name` can be omitted. In this case "test-package" assumed.
 *
 * @see RsTestBase.setupMockCfgOptions
 */
@Inherited
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MockCargoFeatures(vararg val features: String)
