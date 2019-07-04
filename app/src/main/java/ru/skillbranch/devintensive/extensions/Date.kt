package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String = "HH:mm:ss dd.MM.yy"): String {
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
    var diff =  date.time - this.time
    diff/=1000L
    var diffMinute = diff/60%10
    var diffMinuteHead = diff/600
    var diffHour = diff/3600%10
    var diffHourHead = diff/36000
    var diffDay = diff/86400%10
    var diffDayHead = diff/864000
    if (diff > 0 ) {
        return when (diff) {
            in 0..1 -> "только что"
            in 1..45 -> "несколько секунд назад"
            in 45..75 -> "минуту назад"
            in 75..45 * 60 -> if ((diffMinute >= 2) && (diffMinute <= 4)) "${diff / 60} минуты назад" else
                if ((diffMinuteHead >= 2) && (diffMinuteHead <= 4) && (diffMinute == 1L)) "${diff / 60} минуту назад" else "${diff / 60} минут назад"
            in 45 * 60..75 * 60 -> "час назад"
            in 75 * 60..22 * 3600 -> if ((diffHour >= 2) && (diffHour <= 4) && (diffHourHead != 1L)) "${diff / 3600} часа назад" else
                if ((diffHourHead == 1L) || (diffHourHead==0L))"${diff / 3600} часов назад" else "${diff / 3600} час назад"
            in 22 * 3600..26 * 3600 -> "день назад"
            in 26 * 3600..360 * 86400 -> if ((diffDay >= 2) && (diffDay <= 4)) "${diff / 86400} дня назад" else
                if ((diffDayHead != 1L) && (diffDay != 1L)) "${diff / 86400} дней назад" else "${diff / 86400} день назад"
            else -> "более года назад"
        }
    }
    else {
        diff *=-1
        diffMinute *= -1
        diffMinuteHead *= -1
        diffHour *= -1
        diffHourHead *= -1
        diffDay *= -1
        diffDayHead *= -1
        return when (diff) {
            in 0..1 -> "только что"
            in 1..45 -> "через несколько секунд"
            in 45..75 -> "через минуту"
            in 75..45 * 60 -> if ((diffMinute >= 2) && (diffMinute <= 4)) "через ${diff / 60} минуты" else
                if ((diffMinuteHead >= 2) && (diffMinuteHead <= 4) && (diffMinute == 1L)) "через ${diff / 60} минуту" else "через ${diff / 60} минут"
            in 45 * 60..75 * 60 -> "через час"
            in 75 * 60..22 * 3600 -> if ((diffHour >= 2) && (diffHour <= 4) && (diffHourHead != 1L)) "через ${diff / 3600} часа" else
                if (diffHourHead == 1L) "через ${diff / 3600} часов" else "через ${diff / 3600} час"
            in 22 * 3600..26 * 3600 -> "через день"
            in 26 * 3600..360 * 86400 -> if ((diffDay >= 2) && (diffDay <= 4)) "через ${diff / 86400} дня" else
                if ((diffDayHead != 1L) && (diffDay != 1L)) "через ${diff / 86400} дней" else "через ${diff / 86400} день"
            else -> "более чем через год"
        }
    }
}

fun TimeUnits.plural(value: Int): String{
    val temp: Int = if (value >100) value%100 else value
    when (this){
        TimeUnits.SECOND -> {
            return if ((temp%10>=2) && (temp%10<=4)) "$value секунды" else
                if ((temp%10==1) && (temp/10!=1)) "$value секунду" else "$value секунд"
        }
        TimeUnits.MINUTE -> {
            return if ((temp%10>=2) && (temp%10<=4)) "$value минуты" else
                if ((temp%10==1) && (temp/10!=1)) "$value минуту" else "$value минут"
        }
        TimeUnits.HOUR -> {
            return if ((temp%10>=2) && (temp%10<=4)) "$value часа" else
                if ((temp%10==1) && (temp/10!=1)) "$value час" else "$value часов"
        }
        TimeUnits.DAY -> {
            return if ((temp%10>=2) && (temp%10<=4)) "$value дня" else
                if ((temp%10==1) && (temp/10!=1)) "$value день" else "$value дней"
        }
    }
}


enum class TimeUnits{
    SECOND,
    MINUTE,
    HOUR,
    DAY
}