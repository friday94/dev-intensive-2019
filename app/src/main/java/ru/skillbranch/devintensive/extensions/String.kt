package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16):String{
    var stringBuffer:String = this
    stringBuffer = stringBuffer.substring(0,count+1)
    if (stringBuffer.last().toString() == " " ){
        stringBuffer = stringBuffer.trim()
    }
    return "$stringBuffer..."
}