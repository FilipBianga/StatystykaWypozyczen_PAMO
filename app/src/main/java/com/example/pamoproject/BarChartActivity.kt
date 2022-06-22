package com.example.pamoproject

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


//data class Score(
//    val data:String,
//    val scan: Int,
//)
class BarChartActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
//        val bookList = arrayListOf<Book>()
//    private var scoreList = ArrayList<Score>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        barChart = findViewById(R.id.activity_main_barchart)

//        bookList = getScoreList()


                initBarChart()


        //now draw bar chart with dynamic data
        val entries: ArrayList<BarEntry> = ArrayList()

        //you can replace this data object with  your custom object
        for (i in bookList.indices) {
            val score = bookList[i]
            entries.add(BarEntry(i.toFloat(), score.barcode.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "Statystyka Wypożyczeń")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = BarData(barDataSet)
        barChart.data = data

        barChart.invalidate()

    }

    private fun initBarChart() {


//        hide grid lines
        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        barChart.axisRight.isEnabled = false

        //remove legend
        barChart.legend.isEnabled = false


        //remove description label
        barChart.description.isEnabled = false


        //add animation
        barChart.animateY(3000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }


    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d(TAG, "getAxisLabel: index $index")
            return if (index < bookList.size) ({
                bookList[index].data
            }).toString() else {
                ""
            }
        }
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

    // simulate api call
    // we are initialising it directly
//    private fun getScoreList():ArrayList<Book> {
//        return bookList
//    }

