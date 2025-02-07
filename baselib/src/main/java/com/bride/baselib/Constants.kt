package com.bride.baselib

import java.util.Locale

object Constants {
    const val KEY_LANG = "language"

    const val LANG_ENGLISH = 0
    const val LANG_SPANISH = 1
    const val LANG_SIMPLIFIED_CHINESE = 2
    const val LANG_TRADITIONAL_CHINESE = 3
    const val LANG_KOREAN = 4

    fun getLocale(langInt: Int): Locale {
        return when(langInt) {
            LANG_ENGLISH -> Locale.ENGLISH
            LANG_SPANISH -> Locale("es", "ES")
            LANG_SIMPLIFIED_CHINESE -> Locale.SIMPLIFIED_CHINESE
            LANG_TRADITIONAL_CHINESE -> Locale.TRADITIONAL_CHINESE
            LANG_KOREAN -> Locale.KOREAN
            else -> Locale.ENGLISH
        }
    }
}