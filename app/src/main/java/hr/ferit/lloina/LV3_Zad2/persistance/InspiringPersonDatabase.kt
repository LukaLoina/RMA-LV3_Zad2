package hr.ferit.lloina.LV3_Zad2.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [InspiringPersonDetails::class, InspiringQuote::class], version = 1, exportSchema = false)
abstract class InspiringPersonDatabase : RoomDatabase() {
    abstract val inspiringPersonDAO: InspiringPersonDAO
    companion object{
        @Volatile
        private var INSTANCE: InspiringPersonDatabase? = null

        fun getInstance(context: Context): InspiringPersonDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, InspiringPersonDatabase::class.java, "inspiring_people")
                        .allowMainThreadQueries()
                        .build()
                }
                return instance
            }

        }
    }
}