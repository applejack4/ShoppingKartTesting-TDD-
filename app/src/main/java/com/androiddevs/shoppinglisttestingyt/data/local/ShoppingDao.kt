package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(shoppingListItem: ShoppingListItem)

    @Delete
    suspend fun deleteItem(shoppingListItem: ShoppingListItem)

    @Query("SELECT * FROM shopping_list")
    fun observeShoppingItem() : LiveData<List<ShoppingListItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_list")
    fun observeTotalPrice() : LiveData<Float>
}