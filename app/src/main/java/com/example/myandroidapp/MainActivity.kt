package com.example.myandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myandroidapp.Domain.Product
import com.example.myandroidapp.Repo.Repo
import com.example.myandroidapp.RestApi.Api

/*class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}
*/

suspend fun main(){
    println(Repo.returnAll())
    println(Repo.findOne(2))
}


