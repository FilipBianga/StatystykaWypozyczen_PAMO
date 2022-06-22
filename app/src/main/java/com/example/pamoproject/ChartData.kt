package com.example.pamoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class ChartData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    val bookList= arrayListOf<Book>()

    val queue= Volley.newRequestQueue(this)
    val url="https://script.google.com/macros/s/AKfycbz63MNDI3LU3yNst07zzKsoOHKhAjh9HvFXbcndgHwAoUm3KuYiKdJ4cHOY6anqm4VF/exec"
    val jsonObjectRequest=object : JsonObjectRequest(
        Request.Method.GET,url,null,
        Response.Listener {val data=it.getJSONArray("items")
            for(i in 0 until data.length()){
                val bookJasonObject=data.getJSONObject(i)
                val bookObject=Book(
                    bookJasonObject.getString("barcode")
                )
                bookList.add(bookObject)
            }
}, Response.ErrorListener {  }
    ){
        override fun getHeaders(): MutableMap<String, String> {
            return super.getHeaders()
        }
    }
}