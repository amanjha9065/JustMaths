package com.example.justmath

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Random


class GameActivity : AppCompatActivity() {
    lateinit var tvQuestion: TextView
    lateinit var tvAnswer: TextView
    lateinit var tvTime: TextView
    lateinit var tvScore: TextView
    lateinit var correctBtn: ImageButton
    lateinit var incorrectBtn: ImageButton
    var isResultCorrect: Boolean = false
    var seconds = 59
    private var score = 0
    private var stopTimer = false
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        tvQuestion = findViewById(R.id.question)
        tvAnswer = findViewById(R.id.answer)
        tvTime = findViewById(R.id.time_current)
        tvScore = findViewById(R.id.score)
        correctBtn = findViewById(R.id.correct)
        incorrectBtn = findViewById(R.id.incorrect)
        timer()

        correctBtn.setOnClickListener{
            verifyAnswer(true)
            generateQuestion()
        }

        incorrectBtn.setOnClickListener{
            verifyAnswer(false)
            generateQuestion()
        }
    }

    private fun generateQuestion() {
        isResultCorrect = true
        val random = Random()
        val a: Int = random.nextInt(100)
        val b: Int = random.nextInt(100)
        var result = a + b
        val f: Float = random.nextFloat()
        if (f > 0.5f) {
            result = random.nextInt(100)
            isResultCorrect = false
        }
        tvQuestion.text = "$a+$b"
        tvAnswer.text = "=$result"
    }

    @SuppressLint("SetTextI18n")
    private fun verifyAnswer(answer: Boolean) {
        if (answer == isResultCorrect) {
            score +=5
            tvScore.text = "SCORE: $score"
        } else {
            val vibrator: Vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(100)
        }
    }

    private fun timer() {
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                tvTime.text = "TIME :$seconds"
                seconds--
                if (seconds < 0) {
                    val i = Intent(this@GameActivity, ScoreActivity::class.java)
                    i.putExtra("score", score)
                    startActivity(i)
                    stopTimer = true
                }
                if (!stopTimer) {
                    handler.postDelayed(this, 1000)
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        stopTimer = false
        finish()
    }
}