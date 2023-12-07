package com.emredinleyici.catchthekennykotlin

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.emredinleyici.catchthekennykotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }


    fun playGame(view : View){
        val intent = Intent(this@MainActivity, GameActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun howTo(view : View){
        val alert = AlertDialog.Builder(this@MainActivity)
            .setTitle("How to Play")
            .setMessage("Kenny will randomly appear on the screen for 20 seconds. Your goal is to click on Kenny each time he appears, and your score will increase with each successful click.")
            .setPositiveButton("Ok", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    Toast.makeText(this@MainActivity, "Let's play !", Toast.LENGTH_SHORT).show()
                }
            }).show()
    }


}