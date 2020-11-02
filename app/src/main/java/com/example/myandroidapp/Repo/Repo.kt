package com.example.myandroidapp.Repo

import com.example.myandroidapp.Domain.Product
import com.example.myandroidapp.RestApi.Api

object Repo {

    suspend fun returnAll(): List<Product> {
        var cachedItems: MutableList<Product>? = null

        cachedItems = mutableListOf()
        val items = Api.service.find()
        cachedItems?.addAll(items)
        return cachedItems as List<Product>

    }
}