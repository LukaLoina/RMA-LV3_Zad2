package hr.ferit.lloina.LV2.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hr.ferit.lloina.LV2.R
import hr.ferit.lloina.LV2.databinding.ItemInspiringPersonBinding
import hr.ferit.lloina.LV2.model.InspiringPerson


class InspiringPersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    fun bind(person: InspiringPerson){
        val itemBinding = ItemInspiringPersonBinding.bind(itemView)
        itemBinding.tvItemInspiringPersonName.text = person.name
        itemBinding.tvItemInspiringPersonLife.text = person.born.toString() + " - " + person.died.toString()
        itemBinding.tvItemInspiringPersonDescription.text = person.description
        if(person.image != null) {
            itemBinding.imgPerson.setImageBitmap(person.image)
        }
        itemBinding.imgPerson.setOnClickListener {
            if(person.quotes.size == 0) {
                val snackbar = Snackbar.make(itemView, "No quotes found for this person.", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(itemView, person.getRandomQuote(), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
