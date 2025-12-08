//package com.example.lda.eCourtUi.utils
//
//import android.content.Context
//import androidx.core.content.ContextCompat
//import com.example.lda.R
//import com.github.mikephil.charting.charts.BarChart
//import com.github.mikephil.charting.components.AxisBase
//import com.github.mikephil.charting.components.XAxis
//import com.github.mikephil.charting.data.BarData
//import com.github.mikephil.charting.data.BarDataSet
//import com.github.mikephil.charting.data.BarEntry
//import com.github.mikephil.charting.formatter.ValueFormatter
//import java.text.SimpleDateFormat
//import java.util.*
//
//fun setupSummaryChart(barChart: BarChart,numberOfMonths:Int,context: Context) {
//    // --- 1) Generate next 6 months ---
//    val months = mutableListOf<String>()
//    val calendar = Calendar.getInstance()
//    val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
//
//    for (i in 0 until numberOfMonths) {
//        months.add(monthFormat.format(calendar.time))
//        calendar.add(Calendar.MONTH, 1)
//    }
//
//    // --- 2) Dummy data (replace with API/DB values) ---
//    val upcomingValues = listOf(5f, 3f, 7f, 2f, 4f, 6f)
//    val interimValues = listOf(2f, 6f, 4f, 5f, 3f, 7f)
//    val finalValues = listOf(1f, 2f, 3f, 2f, 4f, 5f)
//
//    val entriesUpcoming = ArrayList<BarEntry>()
//    val entriesInterim = ArrayList<BarEntry>()
//    val entriesFinal = ArrayList<BarEntry>()
//
//    for (i in months.indices) {
//        entriesUpcoming.add(BarEntry(i.toFloat(), upcomingValues[i]))
//        entriesInterim.add(BarEntry(i.toFloat(), interimValues[i]))
//        entriesFinal.add(BarEntry(i.toFloat(), finalValues[i]))
//    }
//
//    // --- 3) Create datasets ---
//    val dsUpcoming = BarDataSet(entriesUpcoming, "Upcoming Hearing").apply {
//        color = ContextCompat.getColor(context,R.color.primary) // green
//    }
//    val dsInterim = BarDataSet(entriesInterim, "Interim Order").apply {
//        color = ContextCompat.getColor(context,R.color.secondary)  // amber
//    }
//    val dsFinal = BarDataSet(entriesFinal, "Final Order").apply {
//        color = ContextCompat.getColor(context,R.color.tertiary)  // red
//    }
//
//    // --- 4) BarData and grouping config ---
//    val data = BarData(dsUpcoming, dsInterim, dsFinal)
//
//    val groupSpace = 0.2f   // space between groups
//    val barSpace = 0.05f    // space between bars in group
//    val barWidth = 0.25f    // each bar’s width
//    // (barWidth + barSpace) * 3 + groupSpace = 1.0
//    data.barWidth = barWidth
//    barChart.data = data
//
//
//
//    // --- 5) X-Axis config ---
//    val xAxis = barChart.xAxis
//    xAxis.position = XAxis.XAxisPosition.BOTTOM
//    xAxis.setDrawGridLines(false)
//    val groupWidth = barChart.barData.getGroupWidth(groupSpace, barSpace)
//
//    xAxis.granularity = groupWidth
//    xAxis.setCenterAxisLabels(true) // ✅ important: center labels in group
//    xAxis.isGranularityEnabled = true
//    xAxis.labelCount = months.size
//
//    xAxis.valueFormatter = object : ValueFormatter() {
//        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
//            val index = (value / groupWidth).toInt()
//            return if (index in months.indices) months[index] else ""
//        }
//    }
//    xAxis.labelRotationAngle = 0f
//
//
//
//
//
//    // --- 6) Y-Axis config ---
//    barChart.axisRight.isEnabled = false
//    barChart.axisLeft.axisMinimum = 0f
//
//    // --- 7) Group the bars ---
//    val groupCount = months.size
//    val start = 0f
//    barChart.xAxis.axisMinimum = start
//    barChart.xAxis.axisMaximum =
//        start + barChart.barData.getGroupWidth(groupSpace, barSpace) * groupCount
//    barChart.groupBars(start, groupSpace, barSpace)
//
//    // --- 8) Chart styling ---
//    barChart.description.isEnabled = false
//    barChart.legend.isEnabled = true
//    barChart.setVisibleXRangeMaximum(6f) // show 6 months
//    barChart.animateY(1000)
//    barChart.invalidate()
//
//}
