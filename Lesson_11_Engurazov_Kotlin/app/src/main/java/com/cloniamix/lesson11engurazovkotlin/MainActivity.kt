package com.cloniamix.lesson11engurazovkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statisticsView.setDayStatistic(StatisticsView.DayStatistic("05.05", (Math.random()*100).toInt()))
        statisticsView.setDayStatistic(StatisticsView.DayStatistic("16.05", (Math.random()*100).toInt()))
        statisticsView.setDayStatistic(StatisticsView.DayStatistic("18.05", (Math.random()*100).toInt()))
        statisticsView.setDayStatistic(StatisticsView.DayStatistic("20.05", (Math.random()*100).toInt()))
        statisticsView.setDayStatistic(StatisticsView.DayStatistic("21.05", (Math.random()*100).toInt()))
        statisticsView.setDayStatistic(StatisticsView.DayStatistic("22.05", (Math.random()*100).toInt()))
        statisticsView.setDayStatistic(StatisticsView.DayStatistic("23.05", (Math.random()*100).toInt()))
        statisticsView.setDayStatistic(StatisticsView.DayStatistic("25.05", (Math.random()*100).toInt()))
        statisticsView.setDayStatistic(StatisticsView.DayStatistic("26.05", (Math.random()*100).toInt()))
        statisticsView.setDayStatistic(StatisticsView.DayStatistic("29.05", (Math.random()*100).toInt()))

        statisticsView.setOnClickListener { statisticsView.startMyAnimate() }
    }

    override fun onResume() {
        super.onResume()
        statisticsView.startMyAnimate()
    }
}
