package `in`.example.payo.piechart

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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_financial_pie_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val NoOfEmp = mutableListOf<PieEntry>()

        NoOfEmp.add(PieEntry(945f, 0f))
        NoOfEmp.add(PieEntry(1040f, 1f))
        NoOfEmp.add(PieEntry(1133f, 2f))
        NoOfEmp.add(PieEntry(1240f, 3f))
        NoOfEmp.add(PieEntry(1369f, 4f))
        NoOfEmp.add(PieEntry(1487f, 5f))
        NoOfEmp.add(PieEntry(1501f, 6f))
        NoOfEmp.add(PieEntry(1645f, 7f))
        NoOfEmp.add(PieEntry(1578f, 8f))
        NoOfEmp.add(PieEntry(1695f, 9f))
        val dataSet = PieDataSet(NoOfEmp, "Number Of Employees")

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