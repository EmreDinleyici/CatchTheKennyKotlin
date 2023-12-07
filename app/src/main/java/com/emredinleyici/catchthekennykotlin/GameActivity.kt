package com.emredinleyici.catchthekennykotlin

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.emredinleyici.catchthekennykotlin.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    var images = ArrayList<ImageView>()
    var score = 0
    var runnable : Runnable = Runnable{}
    var handler : Handler = Handler(Looper.getMainLooper())
    private var canClick : Boolean = true
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("com.emredinleyici.catchthekennykotlin", MODE_PRIVATE)
        var highScore = sharedPreferences.getInt("highScore", 0)

        if (highScore == 0){
            binding.highScore.visibility = View.INVISIBLE
        }else{
            binding.highScore.visibility = View.VISIBLE
            binding.highScore.setText("High Score: $highScore")
        }


        images.add(binding.imageView1)
        images.add(binding.imageView2)
        images.add(binding.imageView3)
        images.add(binding.imageView4)
        images.add(binding.imageView5)
        images.add(binding.imageView6)
        images.add(binding.imageView7)
        images.add(binding.imageView8)
        images.add(binding.imageView9)

        hideImage()

        object : CountDownTimer(20000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.timeText.setText("Time: ${millisUntilFinished / 1000}")
            }

            override fun onFinish() {
                binding.timeText.setText("Time's up !!!")
                handler.removeCallbacks(runnable)
                for(i in images){
                    i.visibility = View.INVISIBLE
                }

                if(score > highScore){
                    sharedPreferences.edit().putInt("highScore", score).apply()
                }

                val alertDialog = AlertDialog.Builder(this@GameActivity)
                    .setTitle("Try Again")
                    .setMessage("Restart The Game?")
                    .setPositiveButton("Yes") { dialog, which ->
                        val intent = Intent(this@GameActivity, GameActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                    .setNegativeButton("No") { dialog, which ->
                        val intent = Intent(this@GameActivity, MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    }

                if (!isFinishing) {
                    alertDialog.show()
                }

            }

        }.start()

    }


    fun hideImage(){
        runnable = object : Runnable{
            override fun run() {
                for (i in images){
                    i.visibility = View.INVISIBLE
                }

                val random = Random
                val randomIndex = random.nextInt(9)
                images[randomIndex].visibility = View.VISIBLE

                canClick = true

                handler.postDelayed(runnable, 500)
            }

        }

        handler.post(runnable)
    }


    fun increaseScore(view : View){
        if (canClick) {
            score += 1
            binding.scoreText.text = "Score : $score"
            canClick = false  // Kullanıcının tekrar tıklamasını engelle
            for (i in images){
                i.visibility = View.INVISIBLE
            }
            handler.removeCallbacks(runnable)
            hideImage()
        }
    }


    fun restartButton(view : View){
        val intent = Intent(this@GameActivity, GameActivity::class.java)
        finish()
        startActivity(intent)
    }



}