package com.example.flexify.ui.main_menu.screens

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.flexify.R
import com.example.flexify.data.dbModel.Statistics
import com.example.flexify.databinding.DesktopFragmentBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.android.support.AndroidSupportInjection
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class DesktopFragment : Fragment() {
    private lateinit var binding: DesktopFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val statisticsViewModel by createViewModelLazy(
        StatisticsViewModel::class,
        { this.viewModelStore },
        factoryProducer = { viewModelFactory }
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DesktopFragmentBinding.inflate(inflater, container, false)
        statisticsViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                binding.fullName.text = it.fullName
                setupPieCharts()
                setupLineChart()
            }
        })
        statisticsViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        statisticsViewModel.statistics.observe(viewLifecycleOwner, Observer { statistics ->
            statistics?.let {
                updatePieCharts(LocalDate.now(), it)
                updateLineChart(it)
            }
            binding.swipeRefreshLayout.isRefreshing = false
        })

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(selectedDate)
            onMonthSelected(selectedDate)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onDateSelected(date: LocalDate) {
        val statistics = statisticsViewModel.statistics.value ?: return

        val selectedDateStats = statistics.find { it.date == date }
        if (selectedDateStats != null) {
            updatePieCharts(date, listOf(selectedDateStats))
        } else {
            updatePieCharts(date, emptyList())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onMonthSelected(date: LocalDate) {
        val statistics = statisticsViewModel.statistics.value ?: return
        val selectedMonthStats = statistics.filter { it.date.month == date.month && it.date.year == date.year }
        updateLineChart(selectedMonthStats)
    }

    private fun setupPieCharts() {
        setupPieChart(binding.pieChartConsumedFood, "Consumed Food")
        setupPieChart(binding.pieChartConsumedFoodForWeightLoss, "Weight Loss")
        setupPieChart(binding.pieChartConsumedFoodForWeightGain, "Weight Gain")
    }

    private fun setupPieChart(pieChart: PieChart, title: String) {
        pieChart.apply {
            description.isEnabled = false
            isRotationEnabled = true
            setUsePercentValues(false)
            setEntryLabelColor(Color.TRANSPARENT)
            setHoleColor(Color.WHITE)
            legend.isEnabled = false
        }

        val titleView = TextView(context)
        titleView.text = title
        titleView.textSize = 16f
        titleView.setTextColor(Color.BLACK)
        pieChart.addView(titleView)
    }

    private fun setupLineChart() {
        binding.lineChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            setPinchZoom(true)
            xAxis.granularity = 1f
            axisLeft.granularity = 1f
            axisRight.isEnabled = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatePieCharts(date: LocalDate, statistics: List<Statistics>) {
        val selectedDateStats = statistics.find { it.date == date } ?: Statistics(0, date, 0, 0f, 0f)

        updatePieChart(binding.pieChartConsumedFood, selectedDateStats.caloriesConsumed, statisticsViewModel.caloriesForMaintenance.value ?: 0f, binding.TitleConsumedFood, R.string.TitleConsumedFood)
        updatePieChart(binding.pieChartConsumedFoodForWeightLoss, selectedDateStats.caloriesConsumed, statisticsViewModel.caloriesForWeightLoss.value ?: 0f, binding.TitleWeightLoss, R.string.TitleWeightLoss)
        updatePieChart(binding.pieChartConsumedFoodForWeightGain, selectedDateStats.caloriesConsumed, statisticsViewModel.caloriesForWeightGain.value ?: 0f, binding.TitleWeightGain, R.string.TitleWeightGain)
    }

    private fun updatePieChart(
        pieChart: PieChart,
        totalConsumed: Float,
        goal: Float,
        textView: TextView,
        textResId: Int
    ) {
        val entries = ArrayList<PieEntry>()
        val remaining = goal - totalConsumed
        entries.add(PieEntry(totalConsumed, "Consumed"))
        if (remaining > 0) {
            entries.add(PieEntry(remaining, "Remaining"))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextColor = Color.TRANSPARENT
        dataSet.valueTextSize = 12f

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate()
        textView.text = resources.getString(textResId, totalConsumed, goal)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateLineChart(statistics: List<Statistics>) {
        val consumedEntries = ArrayList<Entry>()
        val spentEntries = ArrayList<Entry>()
        val formatter = DateTimeFormatter.ofPattern("dd.MM")
        val today = LocalDate.now()
        val daysInMonth = today.lengthOfMonth()

        for (day in 1..daysInMonth) {
            val date = LocalDate.of(today.year, today.month, day)
            val statsForDay = statistics.find { it.date == date } ?: Statistics(0, date, 0, 0f, 0f)
            consumedEntries.add(Entry(day.toFloat(), statsForDay.caloriesConsumed))
            spentEntries.add(Entry(day.toFloat(), statsForDay.caloriesSpent))
        }

        val consumedDataSet = LineDataSet(consumedEntries, "Consumed Calories")
        consumedDataSet.color = ColorTemplate.MATERIAL_COLORS[0]
        consumedDataSet.valueTextColor = Color.BLACK
        consumedDataSet.valueTextSize = 12f

        val spentDataSet = LineDataSet(spentEntries, "Spent Calories")
        spentDataSet.color = ColorTemplate.MATERIAL_COLORS[1]
        spentDataSet.valueTextColor = Color.BLACK
        spentDataSet.valueTextSize = 12f

        val data = LineData(consumedDataSet, spentDataSet)

        binding.lineChart.data = data
        binding.lineChart.invalidate()
    }

    private fun refreshData() {
        statisticsViewModel.refreshStatistics()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }
}
