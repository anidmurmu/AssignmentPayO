package `in`.example.payo.piechart

import `in`.example.data.transaction.TransactionalRepositoryImpl
import `in`.example.domain.model.TransactionalInfo
import `in`.example.domain.usecase.TransactionInfoUseCaseImpl
import android.content.Context
import androidx.lifecycle.ViewModel

class FinancialPieChartViewModel(context: Context) : ViewModel() {

    private val repo =
        TransactionalRepositoryImpl()
    private val usecase = TransactionInfoUseCaseImpl(repo, context)

    fun getTransactionalInfo(): TransactionalInfo {
        return usecase.getTransactionInfo()
    }
}