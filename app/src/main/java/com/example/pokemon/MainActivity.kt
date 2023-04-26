package com.example.pokemon

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ImageView>(R.id.backImageView).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            isBackButtonEnable(false)
        }
    }

    fun isBackButtonEnable(isEnable: Boolean) {
        findViewById<ImageView>(R.id.backImageView).isVisible = isEnable
    }
}