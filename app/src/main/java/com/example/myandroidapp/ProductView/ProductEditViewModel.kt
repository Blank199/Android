package com.example.myandroidapp.ProductView

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandroidapp.Domain.Product
import com.example.myandroidapp.Repo.Repo
import kotlinx.coroutines.launch


class ProductEditViewModel : ViewModel() {
    private val mutableItem = MutableLiveData<Product>().apply { value = Product("0", "","0","0") }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val item: LiveData<Product> = mutableItem
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    fun loadItem(itemId: String) {
        viewModelScope.launch {
            mutableFetching.value = true
            mutableException.value = null
            try {
                mutableItem.value = Repo.findOne(itemId)
                mutableFetching.value = false
            } catch (e: Exception) {
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

    fun saveOrUpdateItem(name: String, price: String, stock: String) {
        viewModelScope.launch {
            val item = mutableItem.value ?: return@launch
            item.name = name
            item.price = price
            item.stock = stock
            mutableFetching.value = true
            mutableException.value = null
            try {
                if (item.id != "0") {
                    mutableItem.value = Repo.update(item)
                } else {
                    mutableItem.value = Repo.save(item)
                }
                mutableCompleted.value = true
                mutableFetching.value = false
            } catch (e: Exception) {
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }
}
