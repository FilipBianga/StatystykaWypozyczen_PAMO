package com.example.pamoproject

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate


data class Score(
    val ctime:String,
    val scannedData: Int,
)
class BarChartActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private var scoreList = ArrayList<Score>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)

        barChart = findViewById(R.id.activity_main_barchart)

        scoreList = getScoreList()

        initBarChart()



        val entries: ArrayList<BarEntry> = ArrayList()


        for (i in scoreList.indices) {
            val score = scoreList[i]
            entries.add(BarEntry(i.toFloat(), score.scannedData.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "Ilość wypożyczeń")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = BarData(barDataSet)
        barChart.data = data

        barChart.invalidate()

    }

    private fun initBarChart() {



        barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = barChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)


        barChart.axisRight.isEnabled = false


        barChart.legend.isEnabled = false



        barChart.description.isEnabled = false



        barChart.animateY(3000)


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
            return if (index < scoreList.size) {
                scoreList[index].ctime

            } else {
                ""
            }
        }
    }

    private fun getScoreList(): ArrayList<Score> {
        scoreList.add(Score("Sekretny dziennik młodego lekarza", 56))
        scoreList.add(Score("Prawdziwe historie polskich lekarzy", 75))
        scoreList.add(Score("Farmakologia po prostu", 85))
        scoreList.add(Score("Starosłowiański Masaż Brzucha", 45))
        scoreList.add(Score("Zbrodnia i Medycyna", 63))

        return scoreList
    }


}