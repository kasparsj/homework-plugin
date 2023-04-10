package homework.plugin

import org.apache.cordova.CordovaPlugin
import org.apache.cordova.CallbackContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class Homework : CordovaPlugin() {

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
        "echo" -> {
            val msg = args?.getString(0) ?: ""
            echo(msg, callbackContext)
            return true
        }
        else -> return false
    }

    return false
  }

  private fun echo(
    message: String,
    callbackContext: CallbackContext
  ) {
    if (message.isNotEmpty()) {
      callbackContext?.success(message);
    } else {
      callbackContext?.error("Expected one non-empty string argument.");
    }
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