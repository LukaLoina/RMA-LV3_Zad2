package hr.ferit.lloina.LV3_Zad2.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hr.ferit.lloina.LV3_Zad2.databinding.ItemInspiringPersonBinding
import hr.ferit.lloina.LV3_Zad2.persistance.InspiringPerson


class InspiringPersonHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(person: InspiringPerson){
        val itemBinding = ItemInspiringPersonBinding.bind(itemView)
        itemBinding.tvItemInspiringPersonName.text = person.details.name
        itemBinding.tvItemInspiringPersonLife.text = person.details.born.toString() + " - " + person.details.died.toString()
        itemBinding.tvItemInspiringPersonDescription.text = person.details.description
        if(person.details.image != null) {
            itemBinding.imgPerson.setImageBitmap(person.details.image)
        }
        itemBinding.imgPerson.setOnClickListener {
            val count = person.quotes.size
            if(count == 0) {
                val snackbar = Snackbar.make(itemView, "No quotes found for this person.", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(itemView, person.quotes.random().quote, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
