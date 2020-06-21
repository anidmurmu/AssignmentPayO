package `in`.example.payo.piechart

import `in`.example.domain.model.TransactionalInfo
import android.graphics.Color
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class PieChartGraph(private val pieChart: PieChart, private val transactionInfo: TransactionalInfo) {

    fun initGraph() {
        val income = transactionInfo.income.toFloat()
        val expenditure = transactionInfo.expenditure.toFloat()

        if (income > 0 || expenditure > 0) {

            val transaction = income + expenditure

            val transactionList = mutableListOf<PieEntry>()
            transactionList.apply {
                add(PieEntry(income, "income"))
                add(PieEntry(expenditure, "expenditure"))
            }

            val dataSet = PieDataSet(transactionList, "Transactions")
            val data = PieData(dataSet)
            pieChart.data = data
            pieChart.centerText = "Transactions"
            pieChart.setCenterTextSize(15f)
            pieChart.setCenterTextColor(Color.rgb(193, 37, 82))
            dataSet.colors = mutableListOf(Color.rgb(0, 255, 0), Color.rgb(255, 0, 0))
            dataSet.valueTextSize = 20f
            dataSet.valueTextColor = Color.rgb(255, 255, 255)
            pieChart.animateXY(5000, 5000)
        } else {
            val transactionList = mutableListOf<PieEntry>()
            transactionList.add(PieEntry(100f, "no sms"))
            val dataSet = PieDataSet(transactionList, "No Transactions SMS")
            val data = PieData(dataSet)
            pieChart.data = data
        }
    }
}