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

const val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

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

    fun getDisplayMetrics(): DisplayMetrics {
        val display = windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        return metrics
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        /*observerViews?.let {
            for (observer in it) {
                observer.onSaveInstanceState(outState)
            }
        }*/
    }

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
        val readPhoneStatePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        val accessLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val readContactsPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        /*val readSMSPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)*/
        /*val receiveSMSPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)*/
        val accessFineLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val receiveBootCompletedPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED)
        /*val accountsPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)*/
        val callPhonePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)

        val listPermissionsNeeded = ArrayList<String>()

        if (readPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (accessLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (readContactsPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS)
        }

        /*if (readSMSPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS)
        }*/

        /*if (receiveSMSPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS)
        }*/
        if (accessFineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (receiveBootCompletedPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_BOOT_COMPLETED)
        }

/*        if (accountsPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.GET_ACCOUNTS)
        }*/
/*
        if (readCalendarPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CALENDAR)
        }
*/

        if (callPhonePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE)
        }

        if (shouldRequestPermissions) {
            if (listPermissionsNeeded.isNotEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(),
                        REQUEST_ID_MULTIPLE_PERMISSIONS
                )
            }
        }
        return listPermissionsNeeded.isEmpty()
    }

    fun checkAppSyncPermissionsProvidedStatus(permissions: Array<String>, grantResults: IntArray): Boolean {
        val perms = mutableMapOf<String, Int>()
        perms[Manifest.permission.READ_PHONE_STATE] = PackageManager.PERMISSION_GRANTED
        perms[Manifest.permission.ACCESS_COARSE_LOCATION] = PackageManager.PERMISSION_GRANTED
        perms[Manifest.permission.READ_CONTACTS] = PackageManager.PERMISSION_GRANTED
        /*perms[Manifest.permission.READ_SMS] = PackageManager.PERMISSION_GRANTED*/
        /*perms[Manifest.permission.RECEIVE_SMS] = PackageManager.PERMISSION_GRANTED*/
        perms[Manifest.permission.ACCESS_FINE_LOCATION] = PackageManager.PERMISSION_GRANTED
        perms[Manifest.permission.RECEIVE_BOOT_COMPLETED] = PackageManager.PERMISSION_GRANTED
//        perms[Manifest.permission.GET_ACCOUNTS] = PackageManager.PERMISSION_GRANTED
//        perms[Manifest.permission.READ_CALENDAR] = PackageManager.PERMISSION_GRANTED
        perms[Manifest.permission.CALL_PHONE] = PackageManager.PERMISSION_GRANTED

        if (grantResults.isNotEmpty()) {
            for (i in permissions.indices)
                perms[permissions[i]] = grantResults[i]

            var allPermissionsAdded = true

            for (o in perms.entries) {
                val pair = o as Map.Entry<*, *>
                allPermissionsAdded = allPermissionsAdded && pair.value as Int == PackageManager.PERMISSION_GRANTED
            }

            return allPermissionsAdded
        }

        return false
    }

    interface AppSyncStatusCallBack {
        fun updateCustomerLogStatus()
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

    /*override fun showServerError(errorCode: String, userId: String, versionCode: String) {
        if (dynamicViewHandler == null) {
            dynamicViewHandler = DynamicViewHandler(this)
        }
        val view = dynamicViewHandler!!.show(R.layout.layout_server_error)
        val params = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        params.setMargins(0, 0, 0, 0)
        view.layoutParams = params
        val errorCodeText = view.findViewById(R.id.errorCode) as BaseTextView
        val userIdText = view.findViewById(R.id.userId) as BaseTextView
        val versionCodeText = view.findViewById(R.id.textAppVersion) as BaseTextView
        errorCodeText.text = getString(R.string.errorCode, errorCode)
        userIdText.text = getString(R.string.userId, userId)
        versionCodeText.text = getString(R.string.version, versionCode)
        closeKeyBoard()
    }*/

    override fun hideServerError() {
        dynamicViewHandler?.hide()
    }

    override fun isViewAvailable() = !isFinishing
}
