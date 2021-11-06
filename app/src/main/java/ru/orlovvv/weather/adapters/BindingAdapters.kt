package ru.orlovvv.weather.adapters

import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import kotlin.math.roundToInt

@BindingAdapter("temperature")
fun bindTemperature(textView: MaterialTextView, temp: Double) {
    val tempString = temp.roundToInt().toString() + "\u00B0"
    textView.text = tempString
}