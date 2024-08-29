package com.sunmibarcodescanner

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ActivityEventListener
import com.facebook.react.bridge.WritableArray
import com.facebook.react.bridge.WritableNativeArray

class SunmiBarcodeScannerModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private var scanPromise: Promise? = null

  init {
    reactContext.addActivityEventListener(ActivityEventListenerImpl())
  }

  override fun getName(): String {
    return "SunmiBarcodeScanner"
  }

  @ReactMethod
  fun startScanner(promise: Promise) {
    val intent = Intent("com.sunmi.scanner.qrscanner").apply {
      putExtra("PLAY_SOUND", true)
      putExtra("PLAY_VIBRATE", false)
      putExtra("IDENTIFY_MORE_CODE", false)
      putExtra("IS_SHOW_SETTING", true)
      putExtra("IS_SHOW_ALBUM", true)
      putExtra("IDENTIFY_INVERSE", true)
      putExtra("IS_EAN_8_ENABLE", true)
      putExtra("IS_UPC_E_ENABLE", true)
      putExtra("IS_ISBN_10_ENABLE", false)
      putExtra("IS_CODE_11_ENABLE", true)
      putExtra("IS_UPC_A_ENABLE", true)
      putExtra("IS_EAN_13_ENABLE", true)
      putExtra("IS_ISBN_13_ENABLE", true)
      putExtra("IS_INTERLEAVED_2_OF_5_ENABLE", true)
      putExtra("IS_CODE_128_ENABLE", true)
      putExtra("IS_CODABAR_ENABLE", true)
      putExtra("IS_CODE_39_ENABLE", true)
      putExtra("IS_CODE_93_ENABLE", true)
      putExtra("IS_DATABAR_ENABLE", true)
      putExtra("IS_DATABAR_EXP_ENABLE", true)
      putExtra("IS_Micro_PDF417_ENABLE", true)
      putExtra("IS_MicroQR_ENABLE", true)
      putExtra("IS_OPEN_LIGHT", true)
      putExtra("SCAN_MODE", false)
      putExtra("IS_QR_CODE_ENABLE", true)
      putExtra("IS_PDF417_ENABLE", true)
      putExtra("IS_DATA_MATRIX_ENABLE", true)
      putExtra("IS_AZTEC_ENABLE", true)
      putExtra("IS_Hanxin_ENABLE", false)
    }

    val activity = currentActivity
    if (activity != null) {
      scanPromise = promise
      activity.startActivityForResult(intent, REQUEST_CODE_SCAN)
    } else {
      promise.reject("ActivityNotFound", "Activity not found")
    }
  }

  private inner class ActivityEventListenerImpl : ActivityEventListener {
    override fun onActivityResult(activity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
      if (requestCode == REQUEST_CODE_SCAN) {
        if (resultCode == Activity.RESULT_OK) {
          val codesArray: WritableArray = WritableNativeArray()
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Use the type-safe method for API 34+
           val result =  data?.extras?.getSerializable("data") as ArrayList<HashMap<String, String>>
            result.iterator().forEach { hashmap ->
             val scannedValue = hashmap["VALUE"] ?: "";
              scanPromise?.resolve(scannedValue)
            }
          } else {
            // Use the deprecated method for older APIs
            // Use the type-safe method for API 34+
            val result = data?.extras?.getSerializable("data") as ArrayList<HashMap<String, String>>
            result.iterator().forEach { hashmap ->
              val scannedValue = hashmap["VALUE"] ?: "";
              scanPromise?.resolve(scannedValue)
            }
          }
        } else {
          scanPromise?.reject("ScanFailed", "Scanning failed or canceled")
        }
        scanPromise = null
      }
    }

    override fun onNewIntent(intent: Intent) {}
  }

  companion object {
    private const val REQUEST_CODE_SCAN = 1
  }
}
