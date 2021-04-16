package hr.ferit.lloina.LV2.adapters

import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.lloina.LV2.R
import hr.ferit.lloina.LV2.activities.MainActivity
import hr.ferit.lloina.LV2.fragments.InspiringPersonInputFragment
import hr.ferit.lloina.LV2.model.InspiringPerson
import hr.ferit.lloina.LV2.repository.PeopleRepository

class InspiringPersonAdapter(inspiringPersons : List<InspiringPerson>) : RecyclerView.Adapter<InspiringPersonHolder>() {

    private val inspiringPersons : MutableList<InspiringPerson> = mutableListOf()
    init {
        refreshData(inspiringPersons)
    }

    public fun refreshData(inspiringPersons: List<InspiringPerson>) {
        this.inspiringPersons.clear()
        this.inspiringPersons.addAll(inspiringPersons)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspiringPersonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inspiring_person, parent, false)
        val holder = InspiringPersonHolder(view)
        view.setOnLongClickListener {
            val position = holder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                InspiringPersonInputFragment.editPerson(position)
                MainActivity.setPage(2)
            }
            true
        }
        return holder
    }

    override fun getItemCount(): Int = inspiringPersons.size

    override fun onBindViewHolder(holder: InspiringPersonHolder, position: Int) {
        val person = inspiringPersons[position]
        holder.bind(person)
    }
}