package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    private val historyFileName = "history.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Поля для работы с элементами интерфейса
        val basePrice = 100000 // Базовая цена автомобиля
        val checkBoxLeatherSeats = findViewById<CheckBox>(R.id.checkBoxLeatherSeats)
        val checkBoxAirbags = findViewById<CheckBox>(R.id.checkBoxAirbags)
        val checkBoxNavigation = findViewById<CheckBox>(R.id.checkBoxNavigation)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val historyTextView = findViewById<TextView>(R.id.historyTextView)
        val clearHistoryButton = findViewById<Button>(R.id.clearHistoryButton)

        // Загрузка истории расчетов при запуске
        loadHistory(historyTextView)

        // Обработчик кнопки "Рассчитать цену"
        calculateButton.setOnClickListener {
            var totalPrice = basePrice

            // Добавляем стоимость опций, если они выбраны
            if (checkBoxLeatherSeats.isChecked) totalPrice += 1000
            if (checkBoxAirbags.isChecked) totalPrice += 500
            if (checkBoxNavigation.isChecked) totalPrice += 700

            // Отображаем итоговую цену
            val result = "Итоговая цена: $totalPrice"
            resultTextView.text = result

            // Записываем результат в файл и обновляем историю
            saveResultToFile(result)
            loadHistory(historyTextView)
        }

        // Обработчик кнопки "Очистить историю"
        clearHistoryButton.setOnClickListener {
            clearHistory()
            historyTextView.text = "История расчетов:"
            Toast.makeText(this, "История очищена", Toast.LENGTH_SHORT).show()
        }

        // Переход в AnimationActivity
        val animationButton: Button = findViewById(R.id.buttonOpenAnimation)
        animationButton.setOnClickListener {
            val intent = Intent(this, AnimationActivity::class.java)
            startActivity(intent)
        }

        val btnDatabase = findViewById<Button>(R.id.btnDatabase)
        btnDatabase.setOnClickListener {
            val intent = Intent(this, DatabaseActivity::class.java)
            startActivity(intent)
        }
    }

    // Сохранение результата в файл
    private fun saveResultToFile(result: String) {
        val file = File(filesDir, historyFileName)
        file.appendText("$result\n")
    }

    // Загрузка истории расчетов из файла
    private fun loadHistory(historyTextView: TextView) {
        val file = File(filesDir, historyFileName)
        if (file.exists()) {
            val history = file.readText()
            historyTextView.text = "История расчетов:\n$history"
        } else {
            historyTextView.text = "История расчетов:"
        }
    }

    // Очистка истории расчетов
    private fun clearHistory() {
        val file = File(filesDir, historyFileName)
        if (file.exists()) {
            file.delete()
        }
    }
}
