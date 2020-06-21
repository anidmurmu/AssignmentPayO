package `in`.example.data.transaction

import `in`.example.data.helper.getTransactionalSMSInfo
import `in`.example.domain.model.TransactionalInfo
import `in`.example.domain.repository.TransactionalRepository
import android.content.Context

class TransactionalRepositoryImpl : TransactionalRepository {
    override fun getTransactionalIno(context: Context): TransactionalInfo {
        return getTransactionalSMSInfo(context)
    }
}