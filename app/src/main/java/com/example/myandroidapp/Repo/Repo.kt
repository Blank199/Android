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

    suspend fun findOne(id: Int): Product{

        val item = cachedItems?.find { it.id == id }
        if (item != null) {
            return item
        }
        return Api.service.findOne(id)

    }

    fun update(product: Product): Product? {
        return null
    }

    fun save(item: Product): Product? {
        return null
    }
}