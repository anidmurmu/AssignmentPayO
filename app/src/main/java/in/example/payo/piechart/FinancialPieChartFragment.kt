package `in`.example.payo.piechart

import `in`.example.payo.App
import `in`.example.payo.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_financial_pie_chart.*


class FinancialPieChartFragment : Fragment() {
    lateinit var viewModel: FinancialPieChartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_financial_pie_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = FinancialPieChartViewModel(App.instance)
        val transactionInfo = viewModel.getTransactionalInfo()

        val income = transactionInfo.income.toFloat()
        val expenditure = transactionInfo.expenditure.toFloat()

        val transaction = income + expenditure

        val transactionList = mutableListOf<PieEntry>()
        transactionList.apply {
            add(PieEntry(3000f, 0f))
            add(PieEntry(expenditure, 1f))
        }

        val dataSet = PieDataSet(transactionList, "Transaction")

        val year = mutableListOf<Any>()

        year.add("2008")
        year.add("2009")
        year.add("2010")
        year.add("2011")
        year.add("2012")
        year.add("2013")
        year.add("2014")
        year.add("2015")
        year.add("2016")
        year.add("2017")
        //val data = PieData(year, dataSet)
        val data = PieData(dataSet)
        pieChart.data = data
        dataSet.setColors(*ColorTemplate.COLORFUL_COLORS)
        pieChart.animateXY(5000, 5000)
    }
}