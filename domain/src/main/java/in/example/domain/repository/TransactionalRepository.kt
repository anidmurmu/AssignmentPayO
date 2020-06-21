package `in`.example.domain.repository

import `in`.example.domain.model.TransactionalInfo
import android.content.Context

interface TransactionalRepository {
    fun getTransactionalIno(context: Context): TransactionalInfo
}