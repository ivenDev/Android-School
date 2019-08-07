package com.cloniamix.lesson11engurazovkotlin

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.util.TypedValue
import android.view.MotionEvent
import kotlin.collections.ArrayList


class StatisticsView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var dateColor: Int
    private var columnColor: Int

    private var animation: ValueAnimator? = null

    private val maxColumnsCount = 9
    private val modelText = "55.55"

    private val verticalIndent =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.toFloat(), resources.displayMetrics)

    private lateinit var textPaint: Paint
    private var textBounds = Rect()

    private var statistics: List<DayStatistic> = ArrayList()
    private val animatedValues: ArrayList<Int> = ArrayList()
    private val rectF = RectF()


    init {
        val a: TypedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StatisticsView,
            0, 0
        )

        try {
            dateColor = a.getInteger(R.styleable.StatisticsView_statistics_dateColor, Color.WHITE)
            columnColor = a.getInteger(R.styleable.StatisticsView_statistics_columnColor, Color.WHITE)

        } finally {
            a.recycle()
        }
        init()
    }

    private fun init() {
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        val spSize = 11
        val scaledSizeInPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            spSize.toFloat(), resources.displayMetrics
        )
        textPaint.textSize = scaledSizeInPixels
    }

    /**
     * Добавляет новый показатель дня в список статистики по дням, если колличество показателей не превышает максимальное количество,
     * иначе удаляет первый элемент, а затем добавляет новый
     * и перерисовывает вью
     *
     * @param newStatistics список DayStatistic-с с данными даты и значением показателя в этот день
     *
     */
    fun setDayStatistics(newStatistics: ArrayList<DayStatistic>) {
        if (newStatistics.size < maxColumnsCount) {
            addData(newStatistics)

        } else {
            while (newStatistics.size > maxColumnsCount) {
                newStatistics.remove(newStatistics[0])
            }
            addData(newStatistics)
        }

        invalidate()
    }

    private fun addData(newStatistics: ArrayList<DayStatistic>) {
        statistics = newStatistics
        for (dayStatistic in newStatistics) {
            animatedValues.add(dayStatistic.statisticValue)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val text = modelText
        textPaint.getTextBounds(text, 0, text.length, textBounds)

        var maxStatisticValue = 0

        for (dayStatistic in statistics) {
            maxStatisticValue = Math.max(maxStatisticValue, dayStatistic.statisticValue)
        }

        val scaledValueInDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            maxStatisticValue.toFloat(),
            resources.displayMetrics
        )


        val minW = paddingLeft + paddingRight + maxColumnsCount * (textBounds.width() + textBounds.width() + 1 / 2)
        val w = View.resolveSizeAndState(minW, widthMeasureSpec, 0)

        val minH = paddingBottom + paddingTop +
                (textBounds.height() + textPaint.fontMetrics.descent.toInt()) * 2 +
                scaledValueInDp.toInt() +
                4 * verticalIndent.toInt()

        val h = View.resolveSizeAndState(minH, heightMeasureSpec, 0)

        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (dayStatistic in statistics) {
            val index = statistics.indexOf(dayStatistic)

            textPaint.color = dateColor
            textPaint.textAlign = Paint.Align.LEFT

            val fm = textPaint.fontMetrics
            val horizontalIndent: Float =
                (width - (statistics.size * textBounds.width())) / (statistics.size + 1).toFloat()

            // Рисует дату
            canvas?.drawText(
                dayStatistic.date,
                horizontalIndent + index * (horizontalIndent + textBounds.width()),
                height - fm.descent - verticalIndent,
                textPaint
            )

            val lineWidth = 4
            val lineWidthInDp =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidth.toFloat(), resources.displayMetrics)
            //textPaint.strokeWidth = lineWidthInDp
            textPaint.color = columnColor

            val valueInDp: Float = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                animatedValues[index].toFloat(),
                resources.displayMetrics
            )
            rectF.left = horizontalIndent + textBounds.width() / 2 + index * (horizontalIndent + textBounds.width())
            rectF.top = height - (textBounds.height() + fm.descent + 2 * verticalIndent)
            rectF.right =
                horizontalIndent + textBounds.width() / 2 + index * (horizontalIndent + textBounds.width()) + lineWidthInDp
            rectF.bottom = height - (textBounds.height() + fm.descent + 2 * verticalIndent + valueInDp)

            //Рисует столбик
            canvas?.drawRoundRect(
                rectF,
                lineWidthInDp,
                lineWidthInDp,
                textPaint
            )

            textPaint.textAlign = Paint.Align.CENTER

            //Рисует значение
            canvas?.drawText(
                "${statistics[index].statisticValue}",
                horizontalIndent + textBounds.width() / 2 + index * (horizontalIndent + textBounds.width()),
                height - (textBounds.height() + fm.descent + 3 * verticalIndent + valueInDp),
                textPaint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when (event?.action) {
            MotionEvent.ACTION_DOWN -> true
            MotionEvent.ACTION_UP -> {
                startMyAnimate()
                true
            }
            else -> {
                false
            }
        }
    }

    /**
     * Метод запуска анимации
     */
    fun startMyAnimate() {
        toggleMyAnimation()
        /*invalidate()*/
    }

    private fun toggleMyAnimation() {
        if (animation != null) {
            animation?.cancel()
            animation = null
        }

        for (statistic in statistics) {
            animation = ValueAnimator.ofInt(0, statistic.statisticValue)
            animation?.duration = 1000
            animation?.addUpdateListener {
                val index = statistics.indexOf(statistic)
                animatedValues[index] = it.animatedValue as Int
                invalidate()
            }
            animation?.start()
        }
    }

    /**
     * Класс статистики дня
     *
     * @param date Дата в формате String
     * @param statisticValue Значение показателя в этот день
     **/
    data class DayStatistic(
        val date: String,
        val statisticValue: Int
    )
}
