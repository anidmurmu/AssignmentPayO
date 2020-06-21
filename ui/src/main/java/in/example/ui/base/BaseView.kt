package `in`.example.ui.base


interface BaseView {

    fun closeKeyBoard()

    fun showProgress()

    fun hideProgress()

    fun showMessage(message: String? = "There is an error")

    fun isViewAlive(): Boolean

    fun showInternetError()

    fun hideInternetError()

    fun hideServerError()

    fun isViewAvailable(): Boolean

    fun isViewInteractive(): Boolean
}
