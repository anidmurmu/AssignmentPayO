package `in`.example.payo.piechart

import `in`.example.data.TransactionalRepositoryImpl
import `in`.example.domain.model.TransactionalInfo
import `in`.example.domain.usecase.TransactionInfoUseCaseImpl
import android.content.Context
import androidx.lifecycle.ViewModel

class FinancialPieChartViewModel(context: Context) : ViewModel() {

    val repo = TransactionalRepositoryImpl()
    val usecase = TransactionInfoUseCaseImpl(repo, context)

    fun getTransactionalInfo(): TransactionalInfo {
        return usecase.getTransactionInfo()
    }
}