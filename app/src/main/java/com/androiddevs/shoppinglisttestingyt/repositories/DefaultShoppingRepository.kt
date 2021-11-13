package com.androiddevs.shoppinglisttestingyt.repositories

import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDao
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingListItem
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabayAPI
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import com.androiddevs.shoppinglisttestingyt.other.Constants.API
import com.androiddevs.shoppinglisttestingyt.other.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingListItem) {
        return shoppingDao.insertItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingListItem) {
        return shoppingDao.deleteItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingListItem>> {
        return shoppingDao.observeShoppingItem()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Shit went down", null)
            }else{
                Resource.error("UnKnown Error", null)
            }
        }catch (e : Exception){
            return Resource.error("Couldn't connect.", null)
        }
    }


}