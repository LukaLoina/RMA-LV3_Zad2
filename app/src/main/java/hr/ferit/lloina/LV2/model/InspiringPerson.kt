package hr.ferit.lloina.LV2.model

import android.graphics.Bitmap

class InspiringPerson(var name: String, var description : String,var born : String, var died : String, var quotes: MutableList<String>, var image : Bitmap? = null) {
    fun getRandomQuote() : String = this.quotes.random()
}