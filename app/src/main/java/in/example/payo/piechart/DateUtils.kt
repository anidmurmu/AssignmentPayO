package `in`.example.payo.piechart

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    /**
     * Transform Calendar to ISO 8601 string.
     */
    fun isoFromDate(calendar: Calendar): String {
        val date = calendar.time
        @SuppressLint("SimpleDateFormat") val formatted =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH)
                .format(date)
        return formatted.substring(0, 22) + ":" + formatted.substring(22)
    }
}