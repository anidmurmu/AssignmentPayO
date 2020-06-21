package `in`.example.payo.piechart

import `in`.example.payo.App
import `in`.example.payo.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        PieChartGraph(pieChart, transactionInfo).initGraph()
    }
}