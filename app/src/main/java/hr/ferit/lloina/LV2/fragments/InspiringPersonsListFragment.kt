package hr.ferit.lloina.LV2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.lloina.LV2.adapters.InspiringPersonAdapter
import hr.ferit.lloina.LV2.databinding.FragmentInspiringPersonListBinding
import hr.ferit.lloina.LV2.databinding.FragmentPersonInputBinding
import hr.ferit.lloina.LV2.model.InspiringPerson
import hr.ferit.lloina.LV2.repository.PeopleRepository

class InspiringPersonsListFragment() : Fragment() {

    private lateinit var inspiringPersonListBinding : FragmentInspiringPersonListBinding
    private lateinit var inputBinding: FragmentPersonInputBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        inspiringPersonListBinding = FragmentInspiringPersonListBinding.inflate(inflater, container, false)
        inspiringPersonListBinding.rvInspiringPerson.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        inspiringPersonListBinding.rvInspiringPerson.adapter =  InspiringPersonAdapter(PeopleRepository.inspiringPersonList)
        return inspiringPersonListBinding.root
    }

    override fun onResume() {
        super.onResume()
        (inspiringPersonListBinding.rvInspiringPerson.adapter as InspiringPersonAdapter).refreshData(PeopleRepository.inspiringPersonList)
    }

    companion object{
        const val TAG = "Inspiring Persons List"
        fun create(): InspiringPersonsListFragment {
            return InspiringPersonsListFragment()
        }
    }
}