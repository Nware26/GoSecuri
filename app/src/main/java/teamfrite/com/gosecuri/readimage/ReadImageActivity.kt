package teamfrite.com.gosecuri.readimage

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import kotlinx.android.synthetic.main.readimage_act.*
import teamfrite.com.gosecuri.R

class ReadImageActivity : AppCompatActivity() {

    private lateinit var svScanner : SurfaceView
    private lateinit var tvText : TextView

    private lateinit var cameraSource : CameraSource
    private lateinit var textRecognizer : TextRecognizer

    private lateinit var id : IdCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.readimage_act)

        id = IdCard()

        svScanner = sv_scanner
        tvText = tv_text

        textRecognizer = TextRecognizer.Builder(this).build()
        if (textRecognizer.isOperational){
            Toast.makeText(this, "GOOD", Toast.LENGTH_LONG).show()
            startScanner()
        }
        else {
            Toast.makeText(this, "TextRecognizer is not operational : " + textRecognizer.toString(), Toast.LENGTH_LONG).show()
        }
    }
    private fun startScanner(){
        cameraSource = CameraSource.Builder(this, textRecognizer).setFacing(CameraSource.CAMERA_FACING_BACK)
            .setRequestedFps(30f)
            .setRequestedPreviewSize(1024, 768)
            .setAutoFocusEnabled(true)
            .build()
        svScanner.holder.addCallback(object : SurfaceHolder.Callback2 {
            override fun surfaceRedrawNeeded(holder: SurfaceHolder?) {}

            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                cameraSource.stop()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                if (ActivityCompat.checkSelfPermission(this@ReadImageActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    cameraSource.start(holder)
                }
                else {
                    ActivityCompat.requestPermissions(this@ReadImageActivity, arrayOf(Manifest.permission.CAMERA), 123)
                }
            }
        })
        textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
            override fun release() {}
            override fun receiveDetections(p0: Detector.Detections<TextBlock>?) {
                val textItems = p0?.detectedItems
                if (textItems != null){
                    id.CompleteValues(textItems)
                }
                tvText.text = id.toString();
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123 && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.checkSelfPermission(this@ReadImageActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    cameraSource.start(svScanner.holder)
                }
            }
            else {
                Toast.makeText(this, "Le scanner ne peut marcher sans la permission.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textRecognizer.release()
        cameraSource.stop()
        cameraSource.release()
    }

}
