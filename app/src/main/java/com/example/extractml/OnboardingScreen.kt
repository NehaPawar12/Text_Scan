package com.example.extractml

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hololo.tutorial.library.Step
import com.hololo.tutorial.library.TutorialActivity


class OnboardingScreen : TutorialActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        addFragment(/* step = */ Step.Builder().setTitle("Extract Text from the image")
                .setContent("Extract, save text and copy from the image")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(android.R.drawable.ic_menu_camera) // int drawable
                .setSummary("You can ML kit to extract text from the image, copy it and save it to your clipboard for future use.")
                .build())
    }



    override fun finishTutorial() {
        val intent = Intent(this, MainActivity::class.java)
        // Start the main activity
        startActivity(intent)
        // Finish the current activity
        finish()
    }


    override fun currentFragmentPosition(position: Int) {
        TODO("Not yet implemented")
    }


}