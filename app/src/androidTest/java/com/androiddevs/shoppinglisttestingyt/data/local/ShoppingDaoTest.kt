package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @set:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao : ShoppingDao

    @Before
    fun provideDatabase(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java)
            .allowMainThreadQueries().build()

        dao = database.getShoppingItems()
    }

    @After
    fun teardown(){
        database.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun insertShoppingItemTest() = runBlockingTest {
        val shoppingItem = ShoppingListItem("banana", 5f, 10, "url:uu", 1)
        dao.insertItem(shoppingItem)

        val allShoppingItems = dao.observeShoppingItem().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingListItem("banana", 5f, 10, "url:uu", 1)
        dao.insertItem(shoppingItem)
        dao.deleteItem(shoppingItem)

        val allItems = dao.observeShoppingItem().getOrAwaitValue()

            assertThat(allItems).contains(shoppingItem)
    }

    @Test
    fun testTotalPriceSum() = runBlockingTest {
        val shoppingItem1 = ShoppingListItem("banana", 2f, 20, "url:uu", 1)
        val shoppingItem2 = ShoppingListItem("lemon", 7f, 60, "url:uu", 2)
        val shoppingItem3 = ShoppingListItem("grape", 9f, 90, "url:uu", 3)

        dao.insertItem(shoppingItem1)
        dao.insertItem(shoppingItem2)
        dao.insertItem(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo(2f * 20 + 7f * 60 + 9f * 90)
    }
}