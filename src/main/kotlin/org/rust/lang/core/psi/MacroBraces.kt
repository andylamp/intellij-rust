/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.lang.core.psi

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import com.intellij.psi.tree.IElementType
import org.rust.lang.core.psi.RsElementTypes.*

@Suppress("unused")
enum class MacroBraces(
    val openText: String,
    val closeText: String,
    val openToken: IElementType,
    val closeToken: IElementType
) {
    @SerializedName("Parenthesis")
    @JsonProperty("Parenthesis")
    PARENS("(", ")", LPAREN, RPAREN),

    @SerializedName("Bracket")
    @JsonProperty("Bracket")
    BRACKS("[", "]", LBRACK, RBRACK),

    @SerializedName("Brace")
    @JsonProperty("Brace")
    BRACES("{", "}", LBRACE, RBRACE);

    fun wrap(text: CharSequence): String =
        openText + text + closeText

    val needsSemicolon: Boolean
        get() = this != BRACES

    companion object {
        fun fromToken(token: IElementType): MacroBraces? = when (token) {
            LPAREN, RPAREN -> PARENS
            LBRACK, RBRACK -> BRACKS
            LBRACE, RBRACE -> BRACES
            else -> null
        }

        fun fromOpenToken(token: IElementType): MacroBraces? = when (token) {
            LPAREN -> PARENS
            LBRACK -> BRACKS
            LBRACE -> BRACES
            else -> null
        }

        fun fromTokenOrFail(token: IElementType): MacroBraces =
            fromToken(token) ?: error("Given token is not a brace: $token")
    }
}
