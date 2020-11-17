package ro.ubbcluj.cs.ilazar.myapp2.todo.items

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myandroidapp.Domain.Product
import com.example.myandroidapp.Repo.Repo
import kotlinx.coroutines.launch


class ListViewModel : ViewModel() {
    private val mutableItems = MutableLiveData<List<Product>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val items: LiveData<List<Product>> = mutableItems
    val loading: LiveData<Boolean> = mutableLoading
    val loadingError: LiveData<Exception> = mutableException

    fun createItem(position: Int): Unit {
        val list = mutableListOf<Product>()
        list.addAll(mutableItems.value!!)
        list.add(Product(position, "Item " + position, 1, 1))
        mutableItems.value = list
    }

    fun loadItems() {
        viewModelScope.launch {

            mutableLoading.value = true
            mutableException.value = null
            try {
                mutableItems.value = Repo.returnAll()

                mutableLoading.value = false
            } catch (e: Exception) {

                mutableException.value = e
                mutableLoading.value = false
            }
        }
    }
}
