package com.example.pamoproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class ViewScanActivity : AppCompatActivity() {

    lateinit var readProgressLayout: RelativeLayout
    lateinit var readProgressBar : ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerAdapter:ViewScan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_scan)
        recyclerView=findViewById(R.id.recyclerView)
        readProgressLayout=findViewById(R.id.readProgressLayout)
        readProgressBar=findViewById(R.id.readProgressBar)
        layoutManager= LinearLayoutManager(this)

        val bookList= arrayListOf<Book>()

        val queue= Volley.newRequestQueue(this)
        val url="https://script.google.com/macros/s/AKfycbzPQtomLZiDH4tdMZ9SFlqZfV_sSW1xvN5KiUy-3jGo-89yE0kWoaQ7nMhenkf20cPk/exec"
        val jsonObjectRequest=object : JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener {
                val data=it.getJSONArray("items")
                for(i in 0 until data.length()){
                    val bookJasonObject=data.getJSONObject(i)
                    val bookObject=Book(
                        bookJasonObject.getString("scannedData"),
                        bookJasonObject.getString("ctime")
                    )
                    bookList.add(bookObject)
                }
                readProgressLayout.visibility= View.GONE
                readProgressBar.visibility= View.GONE
                recyclerAdapter= ViewScan(this,bookList)
                recyclerView.adapter=recyclerAdapter
                recyclerView.layoutManager=layoutManager
            }, Response.ErrorListener {  }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }
        }
        queue.add(jsonObjectRequest)

    }

}