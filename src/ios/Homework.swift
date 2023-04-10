import WebKit

@objc(Homework) class Homework : CDVPlugin {
  @objc(alert:) func alert(command: CDVInvokedUrlCommand) {
    var pluginResult = CDVPluginResult(
      status: CDVCommandStatus_ERROR
    )

    let title = command.arguments[0] as? String ?? ""
    let msg = command.arguments[1] as? String ?? ""

    if msg.count > 0 {
      /* UIAlertController is iOS 8 or newer only. */
      let toastController: UIAlertController =
        UIAlertController(
            title: title,
          message: msg,
          preferredStyle: .alert
        )
        toastController.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))

      self.viewController?.present(
        toastController,
        animated: true,
        completion: nil
      )

//      let duration = Double(NSEC_PER_SEC) * 3.0
//
//       dispatch_after(
//         dispatch_time(
//           DISPATCH_TIME_NOW,
//           Int64(duration)
//         ),
//         dispatch_get_main_queue(),
//         {
//           toastController.dismissViewControllerAnimated(
//             true,
//             completion: nil
//           )
//         }
//       )
//
      pluginResult = CDVPluginResult(
        status: CDVCommandStatus_OK,
        messageAs: msg
      )
    }

      self.commandDelegate!.send(
      pluginResult,
      callbackId: command.callbackId
    )
  }

  @objc(callJS:) func callJS(command: CDVInvokedUrlCommand) {
          if let vc = self.viewController as? CDVViewController, let webView = vc.webView as? WKWebView {
              webView.evaluateJavaScript("alert('called from native')", completionHandler: nil)
          }
      }
}
