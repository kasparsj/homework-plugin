package homework.plugin

import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CallbackContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import org.apache.cordova.CordovaInterface
import org.apache.cordova.CordovaWebView

class Homework : CordovaPlugin() {

  private var webView: CordovaWebView? = null

  override fun initialize(cordova: CordovaInterface, webView: CordovaWebView) {
    super.initialize(cordova, webView)
    this.webView = webView
  }

  override fun execute(
    action: String,
    args: JSONArray,
    callbackContext: CallbackContext
  ): Boolean {
    when (action) {
        "alert" -> {
            val context = cordova.activity
            val title = args?.getString(0) ?: ""
            val message = args?.getString(1) ?: ""
            showAlert(context, title, message)
            callbackContext?.success()
            return true
        }
        "callJS" -> {
            val message = "Hello from native!"
            webView.sendJavascript("alert('$message')")
            callbackContext?.success()
            return true
        }
        else -> return false
    }

    return false
  }

  fun showAlert(context: Context, title: String, message: String) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
      dialog.dismiss()
    })
    val dialog = builder.create()
    dialog.show()
  }
}