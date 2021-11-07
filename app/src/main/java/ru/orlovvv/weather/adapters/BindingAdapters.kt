package ru.orlovvv.weather.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.textview.MaterialTextView
import ru.orlovvv.weather.R
import ru.orlovvv.weather.utils.Res
import kotlin.math.roundToInt

@BindingAdapter("temperature")
fun bindTemperature(textView: MaterialTextView, temp: Double) {
    textView.text = beautifyTemperature(temp)
}

@BindingAdapter("temperatureLike")
fun bindTemperatureLike(textView: MaterialTextView, temp: Double) {
    val feelLikeString = Res.getString(R.string.feels_like) + " " + beautifyTemperature(temp)
    textView.text = feelLikeString
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