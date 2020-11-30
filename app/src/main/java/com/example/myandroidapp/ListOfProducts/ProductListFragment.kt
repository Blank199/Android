package com.example.myandroidapp.ListOfProducts

import ListAdapter
import ListViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_product_list.*
import com.example.myandroidapp.R


class ProductListFragment : Fragment() {
    private lateinit var itemListAdapter: ListAdapter
    private lateinit var itemsModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupItemList()
        fab.setOnClickListener {
            findNavController().navigate(R.id.ProductEditFragment)
        }
    }

    private fun setupItemList() {
        itemListAdapter = ListAdapter(this)
        item_list.adapter = itemListAdapter
        itemsModel = ViewModelProvider(this).get(ListViewModel::class.java)
        itemsModel.items.observe(viewLifecycleOwner) { items ->
            itemListAdapter.items = items
        }
        itemsModel.loading.observe(viewLifecycleOwner) { loading ->
            progress.visibility = if (loading) View.VISIBLE else View.GONE
        }
        itemsModel.loadingError.observe(viewLifecycleOwner) { exception ->
            if (exception != null) {
                val message = "Loading exception ${exception.message}"
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        }
        itemsModel.loadItems()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}