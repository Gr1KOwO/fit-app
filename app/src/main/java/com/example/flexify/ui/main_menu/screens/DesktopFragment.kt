package com.example.flexify.ui.main_menu.screens

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.lifecycleScope
import com.example.flexify.data.dbModel.Statistics
import com.example.flexify.databinding.DesktopFragmentBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.launch
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
                setupBarChart()
            }
        })
        statisticsViewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })

        // Настройка SwipeRefreshLayout
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
                updatePieCharts(LocalDate.now(), it) // Обновляем PieChart с данными за сегодня
                updateBarChart(it)
            }
            binding.swipeRefreshLayout.isRefreshing = false // Остановить анимацию обновления
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
        updateBarChart(selectedMonthStats)
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

    private fun setupBarChart() {
        binding.barChart.apply {
            description.isEnabled = false
            setFitBars(true)
            xAxis.granularity = 1f
            axisLeft.granularity = 1f
            axisRight.isEnabled = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updatePieCharts(date: LocalDate, statistics: List<Statistics>) {
        val selectedDateStats = statistics.find { it.date == date } ?: Statistics(0, date, 0, 0f, 0f)

        updatePieChart(binding.pieChartConsumedFood, selectedDateStats.caloriesConsumed, statisticsViewModel.caloriesForMaintenance.value ?: 0f)
        updatePieChart(binding.pieChartConsumedFoodForWeightLoss, selectedDateStats.caloriesConsumed, statisticsViewModel.caloriesForWeightLoss.value ?: 0f)
        updatePieChart(binding.pieChartConsumedFoodForWeightGain, selectedDateStats.caloriesConsumed, statisticsViewModel.caloriesForWeightGain.value ?: 0f)
    }

    private fun updatePieChart(pieChart: PieChart, totalConsumed: Float, goal: Float) {
        val entries = ArrayList<PieEntry>()
        val remaining = goal - totalConsumed
        entries.add(PieEntry(totalConsumed, "Consumed"))
        if (remaining > 0) {
            entries.add(PieEntry(remaining, "Remaining"))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextColor = Color.TRANSPARENT // Hide value text
        dataSet.valueTextSize = 12f

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateBarChart(statistics: List<Statistics>) {
        val entries = ArrayList<BarEntry>()
        val formatter = DateTimeFormatter.ofPattern("dd.MM")
        val today = LocalDate.now()
        val daysInMonth = today.lengthOfMonth()

        for (day in 1..daysInMonth) {
            val date = LocalDate.of(today.year, today.month, day)
            val statsForDay = statistics.find { it.date == date } ?: Statistics(0, date, 0, 0f, 0f)
            entries.add(BarEntry(day.toFloat(), statsForDay.caloriesSpent))
            entries.add(BarEntry(day.toFloat() + 0.5f, statsForDay.caloriesConsumed))
        }

        val dataSet = BarDataSet(entries, "Calories")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 12f

        val data = BarData(dataSet)
        data.barWidth = 0.4f

        binding.barChart.data = data
        binding.barChart.invalidate()
    }

    private fun refreshData() {
        statisticsViewModel.refreshStatistics()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }
}
