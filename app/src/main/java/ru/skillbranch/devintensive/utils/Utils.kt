package ru.skillbranch.devintensive.utils

import kotlin.math.ln

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")
        var firstName = parts?.getOrNull(0)
        var lastName = parts?.getOrNull(1)
        if ((firstName == "") || (firstName == " ")) firstName = null
        if ((lastName == "") || (lastName == " ")) lastName = null
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val tFirstName = payload?.replace(
            Regex("[абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ ]")
        ) {
            when (it.value) {
                "а" -> "a"
                "б" -> "b"
                "в" -> "v"
                "г" -> "g"
                "д" -> "d"
                "е" -> "e"
                "ё" -> "e"
                "ж" -> "zh"
                "з" -> "z"
                "и" -> "i"
                "й" -> "i"
                "к" -> "k"
                "л" -> "l"
                "м" -> "m"
                "н" -> "n"
                "о" -> "o"
                "п" -> "p"
                "р" -> "r"
                "с" -> "s"
                "т" -> "t"
                "у" -> "u"
                "ф" -> "f"
                "х" -> "h"
                "ц" -> "c"
                "ч" -> "ch"
                "ш" -> "sh"
                "щ" -> "sh'"
                "ъ" -> ""
                "ы" -> "i"
                "ь" -> ""
                "э" -> "e"
                "ю" -> "yu"
                "я" -> "ya"
                "А" -> "A"
                "Б" -> "B"
                "В" -> "V"
                "Г" -> "G"
                "Д" -> "D"
                "Е" -> "E"
                "Ё" -> "E"
                "Ж" -> "Zh"
                "З" -> "Z"
                "И" -> "I"
                "Й" -> "I"
                "К" -> "K"
                "Л" -> "L"
                "М" -> "M"
                "Н" -> "N"
                "О" -> "O"
                "П" -> "P"
                "Р" -> "R"
                "С" -> "S"
                "Т" -> "T"
                "У" -> "U"
                "Ф" -> "F"
                "Х" -> "H"
                "Ц" -> "C"
                "Ч" -> "Ch"
                "Ш" -> "Sh"
                "Щ" -> "Sh'"
                "Ъ" -> ""
                "Ы" -> "I"
                "Ь" -> ""
                "Э" -> "E"
                "Ю" -> "Yu"
                "Я" -> "Ya"
                " " -> divider
                else -> it.value
            }
        }
        return tFirstName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var firstInitial: Char? = null
        var lastInitial: Char? = null
        var fName = firstName
        var lName = lastName
        if ((fName == "") || (fName == " ") || (fName == null)) fName = null
        else firstInitial = firstName?.first()?.toUpperCase()
        if ((lName == "") || (lName == " ") || (lName == null)) lName = null
        else lastInitial = lastName?.first()?.toUpperCase()
        if ((fName == null) && (lName == null)) return null
        if ((fName != null) && (lName == null)) return firstInitial.toString()
        if ((fName == null) && (lName != null)) return lastInitial.toString()
        if ((fName != null) && (lName != null)) return "$firstInitial$lastInitial"
        return null
    }
}