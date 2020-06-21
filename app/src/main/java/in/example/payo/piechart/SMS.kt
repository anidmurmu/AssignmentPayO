package `in`.example.payo.piechart

class SMS {

    var customerId: String? = null
    var time: String? = null
    var from: String? = null
    var body: String? = null
    var smsId: String? = null
    var syncId: String? = null

    override fun equals(other: Any?) : Boolean {
        return other is SMS && this.time.equals(other.time, true)
    }

    override fun hashCode(): Int {
        var result = customerId?.hashCode() ?: 0
        result = 31 * result + (time?.hashCode() ?: 0)
        return result
    }

}