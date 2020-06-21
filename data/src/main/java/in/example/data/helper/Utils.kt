package `in`.example.data.helper

import `in`.example.domain.model.TransactionalInfo
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.core.content.ContextCompat
import java.util.regex.Matcher
import java.util.regex.Pattern

fun getTransactionalSMSInfo(context: Context): TransactionalInfo {
    val permissionCheck =
        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
    var income = 0.0
    var expenditure = 0.0
    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
        val uriSms = Uri.parse("content://sms/")
        val cr = context.contentResolver
        var cursor: Cursor? = null
        try {
            cursor = cr.query(uriSms, arrayOf("_id", "address", "date", "body"), null, null, null)
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                do {
                    val body = cursor.getString(3)
                    if (isTransactionalSms(body)) {
                        val amtStr = getAmountInString(body)
                        val amount = removeCurrency(amtStr)
                        if (isExpenditure(body)) {
                            expenditure += amount
                        } else {
                            income += amount
                        }
                    }
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
    }
    return TransactionalInfo(income, expenditure)
}

fun isTransactionalSms(body: String): Boolean {
    if (body.contains("credited") || body.contains("debited") || body.contains("withdrawn")) {
        return true
    }
    return false
}

fun getAmountInString(body: String): String {
    val p: Pattern = Pattern.compile("(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)")
    val m: Matcher = p.matcher(body)
    return if (m.find()) {
        m.group()
    } else {
        "0.0"
    }

}

fun removeCurrency(amount: String): Double {
    return amount.replace(Regex("[A-Za-z,]"), "").toDouble()
}

fun isExpenditure(body: String): Boolean {
    if (body.contains("debited") || body.contains("withdrawn")) {
        return true
    }
    return false
}