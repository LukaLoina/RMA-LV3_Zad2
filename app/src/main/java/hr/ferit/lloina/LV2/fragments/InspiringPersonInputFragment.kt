package hr.ferit.lloina.LV2.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.media.ImageReader
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import hr.ferit.lloina.LV2.R
import hr.ferit.lloina.LV2.activities.MainActivity
import hr.ferit.lloina.LV2.databinding.FragmentPersonInputBinding
import hr.ferit.lloina.LV2.model.InspiringPerson
import hr.ferit.lloina.LV2.repository.PeopleRepository
import java.util.*

class InspiringPersonInputFragment() : Fragment(){
    lateinit var inspiringPersonInput : FragmentPersonInputBinding;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        inspiringPersonInput = FragmentPersonInputBinding.inflate(inflater, container, false)
        inspiringPersonInput.btnPersonInput.setOnClickListener { addPerson() }
        inspiringPersonInput.btnDelete.setOnClickListener { removePerson() }
        inspiringPersonInput.imbtnImage.setOnClickListener { chooseImage() }

        inspiringPersonInput.tvBirthDate.setOnClickListener {
            val c = Calendar.getInstance()
            val datePicker = DatePickerDialog(this.context!!, {view, year, month, day -> inspiringPersonInput.tvBirthDate.text = "$day.$month.$year" }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)  )
            datePicker.show()
        }

        inspiringPersonInput.tvDeathDate.setOnClickListener {
            val c = Calendar.getInstance()
            val datePicker = DatePickerDialog(this.context!!, {view, year, month, day -> inspiringPersonInput.tvDeathDate.text = "$day.$month.$year" }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)  )
            datePicker.show()
        }

        populateIfSelected()

        return inspiringPersonInput.root
    }

    private fun removePerson() {
        val id = PeopleRepository.currentPerson
        if(id != null){
            PeopleRepository.inspiringPersonList.removeAt(id)
            PeopleRepository.currentPerson=null;
        }
        clearInput()
        MainActivity.setPage(0)
    }

    override fun onResume() {
        super.onResume()
        populateIfSelected()
    }

    private fun populateIfSelected(){
        if(PeopleRepository.currentPerson == -1) {
            clearInput()
            PeopleRepository.currentPerson = null
        } else if(PeopleRepository.currentPerson != null){
            var person = PeopleRepository.inspiringPersonList.get(PeopleRepository.currentPerson!!)
            inspiringPersonInput.etName.setText(person.name)
            inspiringPersonInput.tvBirthDate.setText(person.born)
            inspiringPersonInput.tvDeathDate.setText(person.died)
            inspiringPersonInput.etDescription.setText(person.description)
            inspiringPersonInput.etCitations.setText(person.quotes.joinToString( "\n\n"))
            inspiringPersonInput.imbtnImage.setImageBitmap(person.image)
        }
    }

    private fun clearInput() {
        inspiringPersonInput.etName.text.clear()
        inspiringPersonInput.tvBirthDate.setText("from")
        inspiringPersonInput.tvDeathDate.setText("to")
        inspiringPersonInput.etDescription.text.clear()
        inspiringPersonInput.etCitations.text.clear()
        inspiringPersonInput.imbtnImage.setImageResource(R.drawable.unknown_person)
    }

    private fun addPerson(){
        val name = inspiringPersonInput.etName.text.toString()
        val description = inspiringPersonInput.etDescription.text.toString()
        val born = inspiringPersonInput.tvBirthDate.text.toString()
        val died = inspiringPersonInput.tvDeathDate.text.toString()
        val image = inspiringPersonInput.imbtnImage.drawToBitmap()
        val quotes = inspiringPersonInput.etCitations.text.toString().lines().filter { it.isNotEmpty() }.toMutableList()

        if(PeopleRepository.currentPerson == null) {
            val person = InspiringPerson(name, description, born, died, quotes)
            person.image = image
            PeopleRepository.inspiringPersonList.add(person);
        } else {
            var person = PeopleRepository.inspiringPersonList.get(PeopleRepository.currentPerson!!)
            person.name = name
            person.description = description
            person.born = born
            person.died = died
            person.image = image
            person.quotes = quotes
            editPerson(null)
        }

        clearInput()
        MainActivity.setPage(0)

    }

    private fun chooseImage(){
        val intent = Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
        Log.d("DEBUG", "something")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(data == null || data.data == null) return
            val ins = this.context?.contentResolver?.openInputStream(data.data!!)
            val image = BitmapFactory.decodeStream(ins)
            inspiringPersonInput.imbtnImage.setImageBitmap(image);
        }
    }

    companion object{
        const val TAG = "Inspiring Persons Input"
        fun create(): InspiringPersonInputFragment {
            return InspiringPersonInputFragment()
        }

        fun editPerson(id : Int?) {
            PeopleRepository.currentPerson = id;
        }

        fun clearPerson() {
            editPerson(-1)
        }
    }
}