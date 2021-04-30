package hr.ferit.lloina.LV3_Zad2.persistance

import androidx.room.*

@Dao
interface InspiringPersonDAO {
    @Insert
    fun insert(person : InspiringPersonDetails): Long

    @Insert
    fun insert(quote: InspiringQuote): Long

    @Update
    fun update(person : InspiringPersonDetails)

    @Transaction
    @Query("SELECT * from inspiring_people WHERE id = :key")
    fun get(key : Long): InspiringPerson?

    @Transaction
    @Query("SELECT * FROM inspiring_people")
    fun getAll() : List<InspiringPerson>

    @Query("DELETE from inspiring_people WHERE id = :key")
    fun delete(key : Long) : Int

    @Query("DELETE from inspiring_quotes WHERE id = :key")
    fun deleteQuote(key : Long) : Int
}

