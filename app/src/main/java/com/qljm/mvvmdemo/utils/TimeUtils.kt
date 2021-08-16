package com.qljm.mvvmdemo.utils

import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by ruirui on 2019/8/21
 */
object TimeUtils {
    /** 年-月-日 时:分:秒 显示格式  */ // 备注:如果使用大写HH标识使用24小时显示格式,如果使用小写hh就表示使用12小时制格式。
    private var DATE_TO_STRING_DETAIAL_PATTERN = "yyyy-MM-dd HH:mm:ss"


    /**
     * 结束时间距离当前时间
     */
    fun havetimes(endTime: String): Long {
        try {
            // val times =  haveLongTimes(endTime)-haveLongTimes(startTime)
            val times = haveLongTimes(endTime) - System.currentTimeMillis()
            return if (times > 0) times else (0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 结束时间距离当前时间
     */
    fun havetimes(endTime: String, addTime: Long): Long {
        try {
            // val times =  haveLongTimes(endTime)-haveLongTimes(startTime)
            val times = haveLongTimes(endTime) + addTime - System.currentTimeMillis()
            return if (times > 0) times else (0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     * 倒计时半小时
     */
    fun havetimesDown(endTime: String): Long {
        val times = System.currentTimeMillis() - haveLongTimes(endTime)
        return 30 * 60 * 1000 - times
    }


    /**
     * 将给定的时间转成时间戳
     */
    private fun haveLongTimes(times: String): Long {
        try {
            if (TextUtils.equals(times, "0")) {
                return 0
            }
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("GMT+8")
            val end = formatter.parseObject(times) as Date
            return end.time

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }


    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    fun getStringDate(): String {
        val currentTime = Date()
        val formatter = SimpleDateFormat(DATE_TO_STRING_DETAIAL_PATTERN)
        return formatter.format(currentTime)
    }


    /**
     * 将给定的时间转成时间Date
     */
    fun getDate(times: String): Date {
        try {
            if (TextUtils.equals(times, "0")) {
                return Date()
            }
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
            formatter.timeZone = TimeZone.getTimeZone("GMT+8")
            return formatter.parseObject(times) as Date
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Date()
    }

    fun timeSplit(times: String): String {
        val splits = times.split(" ")
        if (splits.size > 1) {
            val ymd = splits[0]
            val hms = splits[1]
            val ymdSplit = ymd.split("-")
            if (ymdSplit.size > 2) {
                val m = ymdSplit[1] + "月"
                val d = ymdSplit[2] + "日"
                val hmsSplit = hms.split(":")
                if (hmsSplit.size > 1) {
                    val hh = hmsSplit[0] + ":"
                    val mm = hmsSplit[1]
                    return m + d + hh + mm
                }

            }

        }

        return ""


    }


    /**
     * 秒转分
     */
    fun Metamerism(timeLong: String): String {
        var Metamerismtime = ""
        var branchtime = ""
        var secondtime = ""

        try {
            var timeLong = timeLong.toInt()
            val minutes: Int = timeLong / 60
            val remainingSeconds: Int = timeLong % 60
            if (minutes < 10) {
                branchtime = "0$minutes"
            } else {
                branchtime = minutes.toString()
            }
            if (remainingSeconds < 10) {
                secondtime = "0$remainingSeconds"
            } else {
                secondtime = remainingSeconds.toString()
            }

            Metamerismtime = "$branchtime:$secondtime"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Metamerismtime
    }


    //计算播放时间
    fun calculateTime(time: Int): String? {

        val minute: Int
        val second: Int
        if (time > 60) {
            minute = time / 60
            second = time % 60
            //分钟再0~9
            return if (minute in 0..9) {
                //判断秒
                if (second in 0..9) {
                    "0$minute:0$second"
                } else {
                    "0$minute:$second"
                }
            } else {
                //分钟大于10再判断秒
                if (second in 0..9) {
                    "$minute:0$second"
                } else {
                    "$minute:$second"
                }
            }
        } else if (time < 60) {
            second = time
            return if (second in 0..9) {
                "00:0$second"
            } else {
                "00:$second"
            }
        }
        return null
    }

    /**
     * 时间差
     *
     * @param date
     * @return
     */
    fun getTimeFormatText(date: Date?): String? {
        val minute = 60 * 1000.toLong() // 1分钟
        val hour = 60 * minute // 1小时
        val day = 24 * hour // 1天
        val month = 31 * day // 月
        val year = 12 * month // 年
        if (date == null) {
            return null
        }
        val diff = Date().time - date.time
        var r: Long = 0
        if (diff > year) {
            r = diff / year
            return r.toString() + "年前"
        }
        if (diff > month) {
            r = diff / month
            return r.toString() + "个月前"
        }
        if (diff > day) {
            r = diff / day
            return r.toString() + "天前"
        }
        if (diff > hour) {
            r = diff / hour
            return r.toString() + "小时前"
        }
        if (diff > minute) {
            r = diff / minute
            return r.toString() + "分钟前"
        }
        return "刚刚"
    }
}
