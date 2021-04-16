package hr.ferit.lloina.LV2.repository

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.drawable.toBitmap
import hr.ferit.lloina.LV2.R
import hr.ferit.lloina.LV2.model.InspiringPerson


object PeopleRepository {
    val inspiringPersonList = mutableListOf<InspiringPerson>()
    var currentPerson : Int? = null
}