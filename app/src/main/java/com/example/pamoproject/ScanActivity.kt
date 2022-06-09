@file:Suppress("DEPRECATION")

package com.example.pamoproject

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import kotlinx.android.synthetic.main.activity_scan.*
import java.util.*
import kotlin.collections.HashMap

class ScanActivity : AppCompatActivity() {
    private lateinit var captureManager: CaptureManager
    private var flashState: Boolean = false
    var scannedData: String = ""

    private var scanState: Boolean = false
    private lateinit var scanBG: Drawable
    lateinit var beepManager: BeepManager
    private var lastScan = Date()

    lateinit var btnMenu: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        btnMenu = findViewById(R.id.btnMenu)

        btnMenu.setOnClickListener {
            val intent = Intent(this@ScanActivity, MainActivity::class.java)
            startActivity(intent)
        }

        title = "Barcode Scanner"

        captureManager = CaptureManager(this, barcodeView)
        captureManager.initializeFromIntent(intent, savedInstanceState)

        beepManager = BeepManager(this)
        beepManager.isVibrateEnabled = true

        scanBG = btnScan.background

        var callback = object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let {
                    val current = Date()
                    val diff = current.time - lastScan.time
                    if(diff >= 1000) {
                        scannedData = it.text
                        txtResultScan.text = scannedData

                        val url="https://script.google.com/macros/s/AKfycbz63MNDI3LU3yNst07zzKsoOHKhAjh9HvFXbcndgHwAoUm3KuYiKdJ4cHOY6anqm4VF/exec"
                        val stringRequest = object : StringRequest(Request.Method.POST, url, Response.Listener {
                            Toast.makeText(this@ScanActivity, "Success", Toast.LENGTH_SHORT).show()
                        },
                            Response.ErrorListener {
                                Toast.makeText(this@ScanActivity, it.toString(), Toast.LENGTH_SHORT).show()
                            }
                        ){
                            override fun getParams(): MutableMap<String, String>? {
                                val params=HashMap<String,String>()
                                params["sdata"] = scannedData
                                return params
                            }
                        }
                        val queue :RequestQueue = Volley.newRequestQueue(this@ScanActivity)
                        queue.add(stringRequest)

                        lastScan = current
                        beepManager.playBeepSoundAndVibrate()

                        animateBackground()
                    }
                }
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {

            }
        }

        btnScan.setOnClickListener(View.OnClickListener {
            if(!scanState) {
                scanState = !scanState
                btnScan.setBackgroundColor(ContextCompat.getColor(this, R.color.lightGreen))
                txtResultScan.text = "scanning..."
                barcodeView.decodeContinuous(callback)
            } else {
                scanState = !scanState
                btnScan.background = scanBG
                barcodeView.barcodeView.stopDecoding()
            }
        })

        btnFlash.setOnClickListener {
            if(flashState) {
                flashState = false
                barcodeView.setTorchOff()
            } else {
                flashState = true
                barcodeView.setTorchOn()
            }
        }
    }

    private fun animateBackground() {
        val colorFrom = resources.getColor(R.color.darkPink)
        val colorTo = resources.getColor(R.color.lightGreen)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 250 //msec

        colorAnimation.addUpdateListener {
                animator -> txtResultScan.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }
}