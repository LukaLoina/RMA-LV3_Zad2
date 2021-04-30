package hr.ferit.lloina.LV3_Zad2.persistance

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.io.ByteArrayOutputStream

@Entity(tableName = "inspiring_people")
@TypeConverters(Converters::class)
class InspiringPersonDetails(){

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "description")
    var description : String = ""

    @ColumnInfo(name = "born")
    var born : String = ""

    @ColumnInfo(name = "died")
    var died : String = ""

    @ColumnInfo(name = "image")
    var image : Bitmap? = null

    fun getRandomQuote() : String = ""//this.quotes.random()
}

@Entity(tableName = "inspiring_quotes", foreignKeys =[
    ForeignKey(entity = InspiringPersonDetails::class, parentColumns = ["id"], childColumns = ["personId"], onDelete = CASCADE)
])
class InspiringQuote() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var quote: String = ""
    var personId: Long = 0

    constructor(personId : Long, quote : String) : this() {
        this.personId = personId
        this.quote = quote
    }
}

data class InspiringPerson(
        @Embedded val details : InspiringPersonDetails,
        @Relation(parentColumn = "id", entityColumn = "personId")
    val quotes: MutableList<InspiringQuote> = mutableListOf()
)

// https://clintpaul.medium.com/what-is-typeconverter-in-room-how-to-use-it-properly-e7b4847012b4
class Converters {
    @TypeConverter
    fun toBitmap(bytes: ByteArray?): Bitmap? {
        if(bytes == null) return null
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    @TypeConverter
    fun fromBitmap(bmp: Bitmap?): ByteArray? {
        if(bmp == null) return null
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}