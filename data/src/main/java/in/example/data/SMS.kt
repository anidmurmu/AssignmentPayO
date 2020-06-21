package `in`.example.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import java.util.regex.Matcher
import java.util.regex.Pattern

class SMS {

    var customerId: String? = null
    var time: String? = null
    var from: String? = null
    var body: String? = null
    var smsId: String? = null
    var syncId: String? = null

    override fun equals(other: Any?): Boolean {
        return other is SMS && this.time.equals(other.time, true)
    }

    override fun hashCode(): Int {
        var result = customerId?.hashCode() ?: 0
        result = 31 * result + (time?.hashCode() ?: 0)
        return result
    }


}

fun synSMS(context: Context) {
    val smsArrayList = ArrayList<`in`.example.data.SMS>()
    val permissionCheck =
        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
    var totalSms = 0
    var exceptionString = ""
    var income = 0.0
    var expenditure = 0.0
    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
        val uriSms = Uri.parse("content://sms/")
        val cr = context.contentResolver
        var cursor: Cursor? = null
        try {
            cursor = cr.query(uriSms, arrayOf("_id", "address", "date", "body"), null, null, null)
            totalSms = cursor?.count ?: 0
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                do {
                    val date = cursor.getString(2)
                    val dateInMillis = java.lang.Long.valueOf(date)
                    val address = cursor.getString(1)
                    val body = cursor.getString(3)
                    val smsId = cursor.getString(0)

                    if (isTransactionalSms(body)) {
                        Log.d("apple body", body)
                        val amtStr = getAmountInString(body)
                        val amount = removecurrency(amtStr)
                        if (isExpenditure(body)) {
                            expenditure += amount
                            Log.d("apple exp", amount.toString())
                        } else {
                            income += amount
                            Log.d("apple income", amount.toString())
                        }
                    }
                    val sms = SMS()
                    sms.apply {
                        //customerId = uid
                        from = address
                        this.body = body
                        this.smsId = smsId
                        /*time = try {
                            isoFromDate(dateInMillis)
                        } catch (e: Exception) {
                            date
                        }*/
                        this.syncId = syncId
                    }
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
    }
    Log.d("apple income", income.toString())
    Log.d("apple expenditure", expenditure.toString())
}

fun isTransactionalSms(body: String): Boolean {
    if (body.contains("credited") || body.contains("debited") || body.contains("withdrawn")) {
        return true
    }
    return false
}

fun getAmountInString(body: String): String {
    val p: Pattern =
        Pattern.compile("(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)")
    val m: Matcher = p.matcher(body)
    return if (m.find()) {
        m.group()
    } else {
        "0.0"
    }

}

fun removecurrency(amount: String): Double {
    return amount.replace(Regex("[A-Za-z,]"), "").toDouble()
}

fun isExpenditure(body: String): Boolean {
    if (body.contains("debited") || body.contains("withdrawn")) {
        return true
    }
    return false
}
