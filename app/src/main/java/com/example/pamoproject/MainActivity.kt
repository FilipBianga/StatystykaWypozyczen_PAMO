@file:Suppress("DEPRECATION")

package com.example.pamoproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView


class MainActivity : AppCompatActivity() {

    lateinit var btnScanActivity: CardView
    lateinit var btnViewScan: CardView
    lateinit var btnChartPieActivity: CardView
    lateinit var btnBartChartActivity: CardView

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
        btnChartPieActivity.setOnClickListener {
            val intent= Intent( this@MainActivity,PieChartActivity::class.java)
            startActivity(intent)
        }
        btnBartChartActivity.setOnClickListener {
            val intent= Intent( this@MainActivity,BarChartActivity::class.java)
            startActivity(intent)
        }
        }
}