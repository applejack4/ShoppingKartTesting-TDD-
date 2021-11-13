package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShoppingListItem::class], version = 1)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun getShoppingItems() : ShoppingDao
}