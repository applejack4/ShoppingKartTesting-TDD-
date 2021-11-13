package com.androiddevs.shoppinglisttestingyt.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingListItem
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import com.androiddevs.shoppinglisttestingyt.other.Resource

class FakeShoppingRepository : ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingListItem>()

    private val observableShoppingItemTest = MutableLiveData<List<ShoppingListItem>>(shoppingItems)

    private val observableTotalPriceTest = MutableLiveData<Float>()

    private var shouldReturnErrorNetwork = false

    private fun refreshLiveData() {
        observableShoppingItemTest.postValue(shoppingItems)
        observableTotalPriceTest.postValue(getTotalPrice())
    }

    fun getTotalPrice() : Float{
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    fun setShouldReturnErrorNetwork(value : Boolean){
        shouldReturnErrorNetwork = value
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingListItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingListItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingListItem>> {
        return observableShoppingItemTest
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPriceTest
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnErrorNetwork){
            Resource.error("Error", null)
        }else{
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }
}