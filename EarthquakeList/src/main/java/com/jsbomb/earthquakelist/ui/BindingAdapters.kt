package com.jsbomb.earthquakelist.ui

import android.content.Context
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jsbomb.earthquakelist.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("timeInLong")
fun setTime(textView: TextView, timeInLong: Long) {
    if (timeInLong != 0L) {
        textView.text = timeInLong.formatTime()
    }
}

object BindingAdapters {
    @JvmStatic
    fun magnitudeString(context: Context, magnitude: Double): String {
        return context.getString(R.string.format_magnitude, magnitude)
    }

    @JvmStatic
    fun latLongString(context: Context, longitude: Double, latitude: Double): String {
        return context.getString(R.string.format_coordinates, longitude, latitude)
    }

    @JvmStatic
    fun tsunamiString(context: Context, tsunami: Int): String {
        return context.getString(
            if (tsunami == 1) {
                R.string.yes
            } else {
                R.string.no
            }
        )
    }
}

fun Long.formatTime(): String {
    val date = Date(this)
    val simpleDateFormat = SimpleDateFormat.getDateTimeInstance() // "h:mm:ss a"

    return simpleDateFormat.format(date)
}