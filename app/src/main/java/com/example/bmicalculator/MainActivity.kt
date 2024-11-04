package com.example.bmicalculator

import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val weightText = findViewById<EditText>(R.id.etWeight)
        val heightText = findViewById<EditText>(R.id.etHeight)
        val submitButton = findViewById<Button>(R.id.btnCalculate)

        submitButton.setOnClickListener{
            val cardResult = findViewById<CardView>(R.id.cvResult)
            val weight = weightText.text.toString()
            val height = heightText.text.toString()
            if (validateInput(weight, height)) {
                cardResult.visibility = VISIBLE
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                val bmi2Digit = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2Digit)
            }
        }
    }

    private fun validateInput(weight: String?, height: String?):Boolean{
        return when {
            weight.isNullOrEmpty() -> {
                Toast.makeText(this, "Weight is empty", Toast.LENGTH_SHORT).show()
                return false
            }

            height.isNullOrEmpty() -> {
                Toast.makeText(this, "Height is empty", Toast.LENGTH_SHORT).show()
                return false
            }

            else -> {
                return true
            }
        }
    }

    private fun displayResult(bmi:Float){
        val index = findViewById<TextView>(R.id.tvIndex)
        val result = findViewById<TextView>(R.id.tvResult)
        val info = findViewById<TextView>(R.id.tvInfo)

        index.text = bmi.toString()
        info.text = "(Normal range is 18.5 - 24.9)"

        var resultText = ""
        var color = 0

        when {
            bmi < 18.50 -> {
                resultText = "UnderWeight"
                color = R.color.underWeight
            }
            bmi in 18.50..24.99 -> {
                resultText = "You are healthy!"
                color = R.color.healthy
            }
            bmi in 25.00..29.99 -> {
                resultText = "Overweight"
                color = R.color.overWeight
            }
            bmi > 29.99 -> {
                resultText = "Obese 😞"
                color = R.color.obese
            }
        }

        result.setTextColor(ContextCompat.getColor(this, color))
        result.text = resultText
    }
}