package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16):String{
    var stringBuffer:String = this.trim()
    return if (stringBuffer.length<=count) stringBuffer
    else {
        stringBuffer = stringBuffer.substring(0, count)
        if (stringBuffer.last().toString() == " ") {
            stringBuffer = stringBuffer.trim()
        }
        "$stringBuffer..."
    }
}

fun String.stripHtml():String{
    var stringBuffer: String = this.trim()
    stringBuffer = stringBuffer.substringAfter("<")
    stringBuffer = stringBuffer.substringAfter(">")
    stringBuffer = stringBuffer.substringBefore("<")
    while (stringBuffer.contains("  ")){
        stringBuffer = stringBuffer.replace("  "," ")
    }
    return stringBuffer
}