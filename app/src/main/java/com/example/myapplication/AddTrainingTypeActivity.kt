package com.example.myapplication

import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddTrainingTypeActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_training_type)

        dbHelper = DBHelper(this)

        val etTrainingName = findViewById<EditText>(R.id.etTrainingName)
        val etTrainingCost = findViewById<EditText>(R.id.etTrainingCost)
        val etTrainingDuration = findViewById<EditText>(R.id.etTrainingDuration)
        val btnSaveTrainingType = findViewById<Button>(R.id.btnSaveTrainingType)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener { finish() }

        btnSaveTrainingType.setOnClickListener {
            val trainingName = etTrainingName.text.toString()
            val trainingCost = etTrainingCost.text.toString().toIntOrNull()
            val trainingDuration = etTrainingDuration.text.toString().toIntOrNull()

            if (trainingName.isEmpty() || trainingCost == null || trainingDuration == null) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("type_name", trainingName)
                put("cost", trainingCost)
                put("duration", trainingDuration)
            }

            val newRowId = db.insert("training_types", null, values)

            if (newRowId == -1L) {
                Toast.makeText(this, "Ошибка при добавлении тренировки", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Тип тренировки добавлен", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
