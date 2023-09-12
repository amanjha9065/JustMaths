package com.example.justmath

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val tv = findViewById<TextView>(R.id.score_value)
        val score = intent.getIntExtra("score", 0)
        tv.text = "Current Score : $score"
    }
}