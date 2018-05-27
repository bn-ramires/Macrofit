package com.example.fujiwara.macrocalc.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.example.fujiwara.macrocalc.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val chart = PieChart(this) // Don't forget to change this to new XML
        chart.isRotationEnabled = true
        chart.isDrawHoleEnabled = false
        chart.setHardwareAccelerationEnabled(true)
        chart.animateY(1000)
        setContentView(chart)

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(65F, getString(R.string.fat_label)))
        entries.add(PieEntry(300F, getString(R.string.carb_label)))
        entries.add(PieEntry(140F, getString(R.string.protein_label)))


        val dataSet = PieDataSet(entries, "Label")
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 20F
        dataSet.sliceSpace = 5F

        val colors = ArrayList<Int>()
        colors.add(ContextCompat.getColor(this, R.color.fat_pie_chart_color))
        colors.add(ContextCompat.getColor(this, R.color.carb_pie_chart_color))
        colors.add(ContextCompat.getColor(this, R.color.protein_pie_chart_color))

        dataSet.colors = colors

        val pieData = PieData(dataSet)
        chart.data = pieData
        chart.invalidate()
    }
}