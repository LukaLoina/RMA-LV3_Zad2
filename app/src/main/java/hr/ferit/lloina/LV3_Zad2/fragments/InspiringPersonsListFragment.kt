package hr.ferit.lloina.LV3_Zad2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.lloina.LV3_Zad2.adapters.InspiringPersonAdapter
import hr.ferit.lloina.LV3_Zad2.databinding.FragmentInspiringPersonListBinding
import hr.ferit.lloina.LV3_Zad2.databinding.FragmentPersonInputBinding
import hr.ferit.lloina.LV3_Zad2.persistance.InspiringPersonDAO
import hr.ferit.lloina.LV3_Zad2.persistance.InspiringPersonDatabase

class InspiringPersonsListFragment() : Fragment() {

    private lateinit var inspiringPersonListBinding : FragmentInspiringPersonListBinding
    private lateinit var inputBinding: FragmentPersonInputBinding
    private val peopleRepository: InspiringPersonDAO by lazy {
        InspiringPersonDatabase.getInstance(this.context!!).inspiringPersonDAO
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        inspiringPersonListBinding = FragmentInspiringPersonListBinding.inflate(inflater, container, false)
        inspiringPersonListBinding.rvInspiringPerson.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        inspiringPersonListBinding.rvInspiringPerson.adapter =  InspiringPersonAdapter(peopleRepository.getAll())
        return inspiringPersonListBinding.root
    }

    override fun onResume() {
        super.onResume()
        (inspiringPersonListBinding.rvInspiringPerson.adapter as InspiringPersonAdapter).refreshData(peopleRepository.getAll())
    }

    companion object{
        const val TAG = "Inspiring Persons List"
        fun create(): InspiringPersonsListFragment {
            return InspiringPersonsListFragment()
        }
    }
}