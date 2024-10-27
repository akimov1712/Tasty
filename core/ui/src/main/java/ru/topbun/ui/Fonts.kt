package ru.topbun.ui

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

object Fonts {

    private fun createFont(fontRes: Int) = FontFamily(Font(fontRes))

    object SF {

        val BOLD = createFont(R.font.sf_bold)
        val SEMI_BOLD = createFont(R.font.sf_semibold)
        val REGULAR = createFont(R.font.sf_regular)
        val MEDIUM = createFont(R.font.sf_medium)

    }

}