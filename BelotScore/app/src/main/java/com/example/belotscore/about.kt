package com.example.belotscore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class about : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val btn1 = findViewById<Button>(R.id.button2)

        btn1.setOnClickListener{
            finish()
            val intent = Intent(this,MainActivity().javaClass)
            startActivity(intent)
        }
    }
}