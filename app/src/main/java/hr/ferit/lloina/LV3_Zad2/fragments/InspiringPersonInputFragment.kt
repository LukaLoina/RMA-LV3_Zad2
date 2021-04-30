
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import hr.ferit.lloina.LV3_Zad2.R
import hr.ferit.lloina.LV3_Zad2.activities.MainActivity
import hr.ferit.lloina.LV3_Zad2.databinding.FragmentPersonInputBinding
import hr.ferit.lloina.LV3_Zad2.persistance.InspiringPersonDetails
import hr.ferit.lloina.LV3_Zad2.persistance.InspiringPersonDatabase
import hr.ferit.lloina.LV3_Zad2.persistance.InspiringQuote
import hr.ferit.lloina.LV3_Zad2.repository.PeopleRepository
import java.util.*

class InspiringPersonInputFragment() : Fragment(){
    lateinit var inspiringPersonInput : FragmentPersonInputBinding;
    var newImage : Bitmap? = null

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
            InspiringPersonDatabase.getInstance(this.context!!).inspiringPersonDAO.delete(id)
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
        if(PeopleRepository.currentPerson == -1L) {
            clearInput()
            PeopleRepository.currentPerson = null
        } else if(PeopleRepository.currentPerson != null) {
            val db = InspiringPersonDatabase.getInstance(this.context!!).inspiringPersonDAO
            val person = db.get(PeopleRepository.currentPerson!!) ?: return
            inspiringPersonInput.etName.setText(person.details.name)
            inspiringPersonInput.tvBirthDate.setText(person.details.born)
            inspiringPersonInput.tvDeathDate.setText(person.details.died)
            inspiringPersonInput.etDescription.setText(person.details.description)
            val quotes = person.quotes.map { it.quote }
            val text = quotes.joinToString("\n\n")
            inspiringPersonInput.etCitations.setText(text)
            if (newImage == null) {
                inspiringPersonInput.imbtnImage.setImageBitmap(person.details.image)
            } else {
                inspiringPersonInput.imbtnImage.setImageBitmap(newImage)
            }
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
        val db = InspiringPersonDatabase.getInstance(this.context!!).inspiringPersonDAO
        val name = inspiringPersonInput.etName.text.toString()
        val description = inspiringPersonInput.etDescription.text.toString()
        val born = inspiringPersonInput.tvBirthDate.text.toString()
        val died = inspiringPersonInput.tvDeathDate.text.toString()
        val image = inspiringPersonInput.imbtnImage.drawToBitmap()
        val quotes = inspiringPersonInput.etCitations.text.toString().lines().filter { it.isNotEmpty() }.toMutableList()

        if(PeopleRepository.currentPerson == null) {
            val person = InspiringPersonDetails()
            person.name = name
            person.description = description
            person.born = born
            person.died = died
            person.image = image
            val personId = db.insert(person);
            quotes.map { InspiringQuote(personId, it) }.forEach{db.insert(it)}
        } else {
            val person = db.get(PeopleRepository.currentPerson!!) ?: return
            person.details.name = name
            person.details.description = description
            person.details.born = born
            person.details.died = died
            person.details.image = image
            person.quotes.filter { it.quote !in quotes }.forEach { db.deleteQuote(it.id) }
            quotes.filter { it !in person.quotes.map{ it.quote } }.forEach { db.insert(InspiringQuote(person.details.id, it)) }
            db.update(person.details)
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
            newImage = image;
            inspiringPersonInput.imbtnImage.setImageBitmap(image);
        }
    }

    companion object{
        const val TAG = "Inspiring Persons Input"
        fun create(): InspiringPersonInputFragment {
            return InspiringPersonInputFragment()
        }

        fun editPerson(id : Long?) {
            PeopleRepository.currentPerson = id;
        }

        fun clearPerson() {
            editPerson(-1)
        }
    }
}