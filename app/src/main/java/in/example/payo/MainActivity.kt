package `in`.example.payo

import `in`.example.ui.base.BaseActivity
import `in`.example.ui.base.REQUEST_SMS_PERMISSION
import android.os.Bundle


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkAppSyncRequestPermissions()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_SMS_PERMISSION -> {
                val isPermissionsAdded =
                    checkAppSyncPermissionsProvidedStatus(permissions, grantResults)
                if (!isPermissionsAdded) {
                    checkAppSyncRequestPermissions()
                }
            }
        }
    }
}
