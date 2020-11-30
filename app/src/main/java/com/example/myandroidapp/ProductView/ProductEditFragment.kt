package com.example.myandroidapp.ProductView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.myandroidapp.R
import kotlinx.android.synthetic.main.fragment_product_edit.*


class ProductEditFragment : Fragment() {
    companion object {
        const val ITEM_ID = "ITEM_ID"
    }

    private lateinit var viewModel: ProductEditViewModel
    private var itemId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ITEM_ID)) {
                itemId = it.getString(ITEM_ID)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_edit, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        product_name.setText(itemId)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        fab.setOnClickListener {
            viewModel.saveOrUpdateItem(product_name.text.toString(),product_price.text.toString(),product_stock.text.toString())
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(ProductEditViewModel::class.java)
        viewModel.item.observe(viewLifecycleOwner) { item ->
            product_name.setText(item.name)
        }
        viewModel.item.observe(viewLifecycleOwner) { item ->
            product_price.setText(item.price)
        }
        viewModel.item.observe(viewLifecycleOwner) { item ->
            product_stock.setText(item.stock)
        }
        viewModel.fetching.observe(viewLifecycleOwner) { fetching ->
            progress.visibility = if (fetching) View.VISIBLE else View.GONE
        }
        viewModel.fetchingError.observe(viewLifecycleOwner) { exception ->
            if (exception != null) {
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.completed.observe(viewLifecycleOwner, Observer { completed ->
            if (completed) {
                findNavController().navigateUp()
            }
        })
        val id = itemId
        if (id != null) {
            viewModel.loadItem(id)
        }
    }
}
