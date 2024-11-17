package com.example.myapplication

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddClientActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var spinnerTrainingType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_client)

        dbHelper = DBHelper(this)

        val etClientName = findViewById<EditText>(R.id.etClientName)
        val etClientPhone = findViewById<EditText>(R.id.etClientPhone)
        spinnerTrainingType = findViewById(R.id.spinnerTrainingType)
        val btnSaveClient = findViewById<Button>(R.id.btnSaveClient)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener { finish() }

        loadTrainingTypes()

        btnSaveClient.setOnClickListener {
            val clientName = etClientName.text.toString()
            val clientPhone = etClientPhone.text.toString()
            val trainingTypeId = (spinnerTrainingType.selectedItem as TrainingType).id

            if (clientName.isEmpty() || clientPhone.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("name", clientName)
                put("phone", clientPhone)
                put("training_type_id", trainingTypeId)
            }

            val newRowId = db.insert("clients", null, values)

            if (newRowId == -1L) {
                Toast.makeText(this, "Ошибка при добавлении клиента", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Клиент добавлен", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun loadTrainingTypes() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query("training_types", arrayOf("id", "type_name"), null, null, null, null, null)

        val trainingTypes = mutableListOf<TrainingType>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val typeName = cursor.getString(cursor.getColumnIndexOrThrow("type_name"))
            trainingTypes.add(TrainingType(id, typeName))
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, trainingTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTrainingType.adapter = adapter
    }

    data class TrainingType(val id: Int, val name: String) {
        override fun toString(): String {
            return name
        }
    }
}
