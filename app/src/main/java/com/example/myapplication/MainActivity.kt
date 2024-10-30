package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var checkBoxLeatherSeats: CheckBox
    private lateinit var checkBoxAirbags: CheckBox
    private lateinit var checkBoxNavigation: CheckBox
    private lateinit var resultTextView: TextView
    private lateinit var calculateButton: Button
    private val basePrice = 900000 // базовая цена автомобиля

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Настройка окна и отступов
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Инициализация элементов управления
        checkBoxLeatherSeats = findViewById(R.id.checkBoxLeatherSeats)
        checkBoxAirbags = findViewById(R.id.checkBoxAirbags)
        checkBoxNavigation = findViewById(R.id.checkBoxNavigation)
        resultTextView = findViewById(R.id.resultTextView)
        calculateButton = findViewById(R.id.calculateButton)

        // Обработчик для кнопки "Рассчитать цену"
        calculateButton.setOnClickListener {
            calculatePrice()
        }
    }

    // Метод для расчета цены автомобиля
    private fun calculatePrice() {
        var finalPrice = basePrice

        if (checkBoxLeatherSeats.isChecked) {
            finalPrice += 1000
        }
        if (checkBoxAirbags.isChecked) {
            finalPrice += 500
        }
        if (checkBoxNavigation.isChecked) {
            finalPrice += 700
        }

        resultTextView.text = "Итоговая цена: $finalPrice Руб."
    }
}
