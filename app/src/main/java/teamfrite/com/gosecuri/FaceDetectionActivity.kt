package teamfrite.com.gosecuri

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions

class FaceDetectionActivity : AppCompatActivity() {


    private val Camera = findViewById<SurfaceView>(R.id.surfaceView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_detection)



        val bm = BitmapFactory.decodeResource(resources, R.drawable.arthurp)

        // High-accuracy landmark detection and face classification
        val highAccuracyOpts = FirebaseVisionFaceDetectorOptions.Builder()
            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            .build()

        // Real-time contour detection of multiple faces
        val realTimeOpts = FirebaseVisionFaceDetectorOptions.Builder()
            .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
            .build()

        val image = FirebaseVisionImage.fromBitmap(bm)

        val detector = FirebaseVision.getInstance()
            .getVisionFaceDetector(realTimeOpts)

        val result = detector.detectInImage(image)
            .addOnSuccessListener { faces ->
                // Task completed successfully
                // ...
                Toast.makeText(this, "face detected", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener(
                object : OnFailureListener {
                    override fun onFailure(e: Exception) {
                        // Task failed with an exception
                        // ...
                    }
                })
    }
}
