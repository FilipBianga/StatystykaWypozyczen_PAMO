package com.example.pamoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView


class MainActivity : AppCompatActivity() {

    lateinit var btnScanActivity: CardView
    lateinit var btnViewScan: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnScanActivity = findViewById(R.id.btnScanActivity)
        btnViewScan = findViewById(R.id.btnViewScan)


        btnScanActivity.setOnClickListener {
            val intent = Intent(this@MainActivity, ScanActivity::class.java)
            startActivity(intent)
        }

        btnViewScan.setOnClickListener {
            val intent = Intent(this@MainActivity, ViewScanActivity::class.java)
            startActivity(intent)
        }
        }
}