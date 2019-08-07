package com.cloniamix.lesson11engurazovkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statisticsView.setDayStatistics(getStatistics())

        statisticsView.setOnClickListener { statisticsView.startMyAnimate() }
    }

    override fun onResume() {
        super.onResume()
        statisticsView.startMyAnimate()
    }

    private fun getStatistics(): ArrayList<StatisticsView.DayStatistic> {
        val statistics: ArrayList<StatisticsView.DayStatistic> = ArrayList()

        statistics.add(StatisticsView.DayStatistic("05.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("16.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("18.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("20.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("21.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("22.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("23.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("25.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("26.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("29.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("30.05", (Math.random() * 100).toInt()))
        statistics.add(StatisticsView.DayStatistic("31.05", (Math.random() * 100).toInt()))
        return statistics
    }
}
