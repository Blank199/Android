package com.example.myandroidapp.Repo

import android.util.Log

import com.example.myandroidapp.Domain.Product
import com.example.myandroidapp.RestApi.Api

object Repo {

    private var cachedItems: MutableList<Product>? = null;

    suspend fun returnAll(): List<Product> {


        cachedItems = mutableListOf()
        val items = Api.service.findAll()
        cachedItems?.addAll(items)
        return cachedItems as List<Product>

    }

    suspend fun size() : Int {
        if(cachedItems != null) {
            val list =  cachedItems as List<Product>;
            return list.size;
        }
        return 1;
    }

    suspend fun findOne(id: String): Product{

        val item = cachedItems?.find { it.id == id }
        if (item != null) {
            return item
        }
        return Api.service.findOne(id)

    }

    suspend fun update(product: Product): Product? {
        val updatedProduct: Product = Api.service.update(product)
        val index = cachedItems?.indexOfFirst { it.id == product.id }
        if (index != null) {
            cachedItems?.set(index, updatedProduct)
        }
        return updatedProduct
    }

    suspend fun save(item: Product): Product? {
        val createdItem = Api.service.create(item)
        cachedItems?.add(createdItem)
        return createdItem
    }
}