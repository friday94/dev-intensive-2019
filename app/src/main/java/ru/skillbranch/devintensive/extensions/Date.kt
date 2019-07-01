package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String="HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value:Int, units: TimeUnits = TimeUnits.SECOND): Date{
    var time = this.time

    time += when(units){
        TimeUnits.SECOND-> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR-> value * HOUR
        TimeUnits.DAY-> value * DAY
        else -> throw IllegalStateException("invalid unit")
    }
    this.time = time
    return this
}



fun Date.humanizeDiff(date: Date = Date()): String {
    var diff = Date().time - date.time
    println("Текущая дата = ${Date().time}")
    println("Пришла дата =  ${date.time}")
    diff/=1000L
    val diffMinute = diff/60%10
    val diffMinuteHead = diff/600
    println("Остаток минут = $diffMinute")
    println("Остаток минут голова = $diffMinuteHead")
    println("разница времени = $diff")
    return when (diff){
        in 0..1 -> "только что"
        in 1..45 -> "несколько секунд назад"
        in 45..75 -> "минуту назад"
        in 75..45*60 -> if((diffMinute>=1) && (diffMinute<=4)) "${diff/60} минуты назад" else "${diff/60} минут назад"
        in 45*60..75*60 -> "час назад"
        in 75*60..22*3600 -> "${diff/3600} часов назад"
        in 22*3600..26*3600 -> "день назад"
        in 26*3600..360*86400 -> "${diff/86400} дней назад"
        else -> "более года назад"
    }
    return ""
}

enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}