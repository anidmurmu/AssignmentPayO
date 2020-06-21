package `in`.example.domain.usecase

import `in`.example.domain.model.TransactionalInfo
import `in`.example.domain.repository.TransactionalRepository
import android.content.Context


interface TransactionalInfoUseCase {
    fun getTransactionInfo(): TransactionalInfo
}
class TransactionInfoUseCaseImpl(private val dataSource: TransactionalRepository, private val  context: Context) : TransactionalInfoUseCase {
    override fun getTransactionInfo(): TransactionalInfo {
        return dataSource.getTransactionalIno(context)
    }
}