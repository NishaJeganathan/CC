package com.example.cheesechase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.example.cheesechase.ui.theme.CheeseChaseTheme

class SecondActivity:ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheeseChaseTheme {
                val rect=RectangleView(this)
                setContentView(rect)
            }
        }

    }
}