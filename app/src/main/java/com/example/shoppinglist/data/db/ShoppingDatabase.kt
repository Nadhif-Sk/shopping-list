package com.example.shoppinglist.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.data.db.entities.ShoppingItem

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingDatabase: RoomDatabase() {

    abstract fun getShoppingDao(): ShoppingDao

    companion object{
        @Volatile
        private var instances: ShoppingDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instances
            ?: synchronized(LOCK){
            instances
                ?: createDatabase(
                    context
                )
                    .also{ instances = it}
        }

        private fun createDatabase(context : Context) =
            Room.databaseBuilder(context.applicationContext,
            ShoppingDatabase::class.java, "ShoppingDB.db").build()
    }
}
