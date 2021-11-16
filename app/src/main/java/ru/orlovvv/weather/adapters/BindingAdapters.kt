package ru.orlovvv.weather.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.textview.MaterialTextView
import ru.orlovvv.weather.R
import ru.orlovvv.weather.data.model.cache.LocationCache
import ru.orlovvv.weather.data.model.other.Forecastday
import ru.orlovvv.weather.data.model.other.Hour
import ru.orlovvv.weather.utils.Constants.DATE_PATTERN
import ru.orlovvv.weather.utils.Constants.DATE_PATTERN_WITHOUT_HOURS
import ru.orlovvv.weather.utils.DateUtils
import ru.orlovvv.weather.utils.Res
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

@BindingAdapter("hoursList")
fun bindHourlyForecastList(recyclerView: RecyclerView, data: List<Hour>?) {
    val adapter = recyclerView.adapter as HourlyForecastAdapter
    adapter.submitList(data)
}

@BindingAdapter("daysList")
fun bindDailyForecastList(recyclerView: RecyclerView, data: List<Forecastday>?) {
    val adapter = recyclerView.adapter as DailyForecastAdapter
    adapter.submitList(data)
}

@BindingAdapter("locationsList")
fun bindLocationsList(recyclerView: RecyclerView, data: List<LocationCache>?) {
    val adapter = recyclerView.adapter as LocationAdapter
    adapter.submitList(data)
}

@BindingAdapter("temperature")
fun bindTemperature(textView: MaterialTextView, temp: Double) {
    textView.text = beautifyTemperature(temp)
}

@BindingAdapter("temperatureLike")
fun bindTemperatureLike(textView: MaterialTextView, temp: Double) {
    val feelLikeString = Res.getString(R.string.feels_like) + " " + beautifyTemperature(temp)
    textView.text = feelLikeString
}

@BindingAdapter("hour")
fun bindHour(textView: MaterialTextView, date: String) {
    val hourString = DateUtils.formatDateToHour(date)
    textView.text = hourString
}

@BindingAdapter("today")
fun bindToday(textView: MaterialTextView, parameter: Int?) {
    textView.text = DateUtils.getToday("EEEE")
}

@BindingAdapter("date")
fun bindDay(textView: MaterialTextView, date: String) {
    val dayString = DateUtils.formatDateToDayString(date)
    textView.text = dayString
}

@BindingAdapter("percent")
fun bindPercent(textView: MaterialTextView, value: Int) {
    val percentString = "$value%"
    textView.text = percentString
}

@BindingAdapter("percentDouble")
fun bindPercentDouble(textView: MaterialTextView, value: Double) {
    val percentString = value.roundToInt().toString() + "%"
    textView.text = percentString
}

@BindingAdapter("speed")
fun bindSpeed(textView: MaterialTextView, speed: Double) {
    val speedString = speed.roundToInt().toString() + " " + Res.getString(R.string.kmh)
    textView.text = speedString
}

@BindingAdapter("locationName")
fun bindLocationName(textView: MaterialTextView, fullName: String?) {
    if (fullName == null) {
        textView.text = Res.getString(R.string.select_location)
    } else {
        val name = fullName.substringBefore(",")
        textView.text = name
    }

}

@BindingAdapter("max", "min")
fun bindMaxAndMinTemp(textView: MaterialTextView, max: Double, min: Double) {
    val tempString =
        Res.getString(R.string.max) + ": " + beautifyTemperature(max) + ", " + Res.getString(R.string.min) + ": " + beautifyTemperature(
            min
        )
    textView.text = tempString
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, urlToImage: String?) {
    Glide.with(imgView.context)
        .asBitmap()
        .fitCenter()
        .dontAnimate()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .format(DecodeFormat.PREFER_RGB_565)
        .load("https:$urlToImage")
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                imgView.setImageBitmap(resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
//                imgView.setImageResource(R.drawable.ic_broken_image)
            }
        })
}

private fun beautifyTemperature(temp: Double) = temp.roundToInt().toString() + "\u00B0"