package com.example.extractml

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {

    lateinit var result:EditText//we will use this to show the result later.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //supportActionBar?.show()

        val camera = findViewById<ImageView>(R.id.camera)
        val erase = findViewById<ImageView>(R.id.edit)
        val copy = findViewById<ImageView>(R.id.copy)

        result = findViewById(R.id.resulttv)

        camera.setOnClickListener {
            //open the camera and store the image
            //on clicked image we will run the ML algorithm to exrtact the text.

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if(intent.resolveActivity(packageManager) != null){
                //I want to receive image and send it to ML algorithm
                startActivityForResult(intent,123)
            }
            else{
                //show a toast
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            }
        }
        erase.setOnClickListener {
            //erase the text
            result.setText("")
        }
        copy.setOnClickListener {
            //copy the text
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("text",result.text.toString())
            clipBoard.setPrimaryClip(clip)
            Toast.makeText(this,"Copied to clipboard",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 123 && resultCode == RESULT_OK){
            val extras = data?.extras
            val bitmap = extras?.get("data") as Bitmap
            detectText(bitmap)
        }
    }

    private fun detectText(bitmap: Bitmap) {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        val image = InputImage.fromBitmap(bitmap, 0)

        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                // Task completed successfully
                // ...
                result.setText(visionText.text.toString())
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()

            }

    }

}