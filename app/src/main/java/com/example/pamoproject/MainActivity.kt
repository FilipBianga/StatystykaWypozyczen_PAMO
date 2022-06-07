package com.example.pamoproject

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var captureManager: CaptureManager
    private var flashState: Boolean = false

    private var scanState: Boolean = false
    private lateinit var scanBG: Drawable
    lateinit var beepManager: BeepManager
    private var lastScan = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                        txtResultScan.text = it.text
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