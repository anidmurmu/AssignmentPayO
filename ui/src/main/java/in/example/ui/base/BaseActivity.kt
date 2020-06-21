package `in`.example.ui.base

import `in`.example.ui.R
import `in`.example.ui.helper.DynamicViewHandler
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

const val REQUEST_SMS_PERMISSION = 1

abstract class BaseActivity : AppCompatActivity(), BaseView {

    private var isViewAlive: Boolean = false
    private var isViewInteractive: Boolean = false

    private var dynamicViewHandler: DynamicViewHandler? = null

    //protected var observerViews: List<ObserverView>? = null

    override fun closeKeyBoard() {
        if (currentFocus != null && currentFocus?.windowToken != null) {
            val inputManager = getSystemService(Context
                    .INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    fun showToast(msg: String?) {
        msg?.also {
            //window?.decorView?.showToast(it)
        }
    }

    override fun showProgress() {
        if (dynamicViewHandler == null) {
            dynamicViewHandler = DynamicViewHandler(this)
        }
        dynamicViewHandler!!.show(R.layout.progress)
        closeKeyBoard()
    }

    override fun hideProgress() {
        dynamicViewHandler?.hide()
    }

    override fun showMessage(message: String?) {
        showToast(message)
    }

    override fun isViewAlive() = isViewAlive

    override fun isViewInteractive() = isViewInteractive

    override fun onStart() {
        super.onStart()
        isViewInteractive = true
    }

    override fun onStop() {
        super.onStop()
        isViewInteractive = false
        /*observerViews?.let {
            for (observer in it) {
                observer.onStop()
            }
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isViewAlive = true
    }

    override fun onDestroy() {
        super.onDestroy()
        isViewAlive = false
    }

    fun checkAppSyncRequestPermissions(shouldRequestPermissions: Boolean = true): Boolean {
        val readSMSPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)

        val listPermissionsNeeded = ArrayList<String>()

        if (readSMSPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS)
        }

        if (shouldRequestPermissions) {
            if (listPermissionsNeeded.isNotEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(),
                        REQUEST_SMS_PERMISSION
                )
            }
        }
        return listPermissionsNeeded.isEmpty()
    }

    fun checkAppSyncPermissionsProvidedStatus(permissions: Array<String>, grantResults: IntArray): Boolean {
        val perms = mutableMapOf<String, Int>()
        perms[Manifest.permission.READ_SMS] = PackageManager.PERMISSION_GRANTED

        if (grantResults.isNotEmpty()) {
            for (i in permissions.indices)
                perms[permissions[i]] = grantResults[i]

            var allPermissionsAdded = true

            //check if all entries in the map has permissions granted.
            for (o in perms.entries) {
                val pair = o as Map.Entry<*, *>
                allPermissionsAdded = allPermissionsAdded && pair.value as Int == PackageManager.PERMISSION_GRANTED
            }

            return allPermissionsAdded
        }

        return false
    }

    override fun showInternetError() {
        if (dynamicViewHandler == null) {
            dynamicViewHandler = DynamicViewHandler(this)
        }
        val view = dynamicViewHandler!!.show(R.layout.layout_internet_error)
        val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        params.setMargins(0, 0, 0, 0)
        view.layoutParams = params
        closeKeyBoard()
    }

    override fun hideInternetError() {
        dynamicViewHandler?.hide()
    }

    override fun hideServerError() {
        dynamicViewHandler?.hide()
    }

    override fun isViewAvailable() = !isFinishing
}
