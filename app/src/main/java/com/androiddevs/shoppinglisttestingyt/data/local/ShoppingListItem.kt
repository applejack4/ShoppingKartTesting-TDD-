package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingListItem(var name : String,
                            var price : Float,
                            var amount : Int,
                            var imageUrl : String,
                            @PrimaryKey(autoGenerate = true) val id : Int)