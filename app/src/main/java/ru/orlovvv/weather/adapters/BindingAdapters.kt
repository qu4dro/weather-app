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
import ru.orlovvv.weather.data.model.other.Hour
import ru.orlovvv.weather.utils.Constants.DATE_PATTERN
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
    val hourString = formatDateToHour(date)
    textView.text = hourString
}

@BindingAdapter("today")
fun bindToday(textView: MaterialTextView, parameter: Int?) {
    val calendar = Calendar.getInstance()
    val date = calendar.time
    textView.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
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

private fun formatDateToHour(date: String) : String {
    val outputFormat: DateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    val inputFormat: DateFormat = SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH)
    return outputFormat.format(inputFormat.parse(date))
}

private fun formatDateToDayString(date: String) : String {
    val inputFormat: DateFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
    return inputFormat.format(date)
}