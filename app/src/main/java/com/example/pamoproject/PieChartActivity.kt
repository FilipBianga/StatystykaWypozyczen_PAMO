package com.example.pamoproject

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class PieChartActivity : AppCompatActivity() {
    private var pieChart: PieChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart)
        pieChart = findViewById(R.id.activity_main_piechart)
        setupPieChart()
        loadPieChartData()

    }
    private fun setupPieChart() {
        pieChart!!.isDrawHoleEnabled = true
        pieChart!!.setUsePercentValues(true)
        pieChart!!.setEntryLabelTextSize(12f)
        pieChart!!.setEntryLabelColor(Color.BLACK)
        pieChart!!.centerText = "Najczęściej wypożyczane ksiązki"
        pieChart!!.setCenterTextSize(24f)
        pieChart!!.description.isEnabled = false
        val l = pieChart!!.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }

    private fun loadPieChartData() {

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(0.2f, "Sekretny dziennik młodego lekarza"))
        entries.add(PieEntry(0.15f, "Prawdziwe historie polskich lekarzy"))
        entries.add(PieEntry(0.10f, "Farmakologia po prostu"))
        entries.add(PieEntry(0.25f, "Starosłowiański Masaż Brzucha "))
        entries.add(PieEntry(0.3f, "Zbrodnia i Medycyna"))
        val colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "Ksiązki")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChart!!.data = data
        pieChart!!.invalidate()
        pieChart!!.animateY(1400, Easing.EaseInOutQuad)

    }
}