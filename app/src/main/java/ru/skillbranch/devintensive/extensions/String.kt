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
    var stringBuffer: String = this

    stringBuffer = stringBuffer.replace("<(.*?)>".toRegex(),"")
    stringBuffer = stringBuffer.replace("&amp;","")
    stringBuffer = stringBuffer.replace("&lt;","")
    stringBuffer = stringBuffer.replace("&gt;","")
    stringBuffer = stringBuffer.replace("&#39;","")
    stringBuffer = stringBuffer.replace("&quot;","")
    while (stringBuffer.contains("  ")){
        stringBuffer = stringBuffer.replace("  "," ")
    }
    return stringBuffer
}