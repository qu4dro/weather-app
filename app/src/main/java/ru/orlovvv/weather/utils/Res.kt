package ru.orlovvv.weather.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.orlovvv.weather.WeatherApplication

object Res {

    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return WeatherApplication.instance.getString(stringRes, *formatArgs)
    }

    fun getColor(@ColorRes colorRes: Int): Int {
        return WeatherApplication.instance.getColor(colorRes)
    }

    fun getDrawable(@DrawableRes drawableRes: Int): Drawable? {
        return WeatherApplication.instance.getDrawable(drawableRes)
    }

    fun getContext(): Context {
        return WeatherApplication.instance.applicationContext
    }
}