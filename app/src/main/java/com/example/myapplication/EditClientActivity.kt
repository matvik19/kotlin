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

class EditClientActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var etClientName: EditText
    private lateinit var etClientPhone: EditText
    private lateinit var spinnerTrainingType: Spinner
    private var clientId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_client)

        dbHelper = DBHelper(this)

        etClientName = findViewById(R.id.etClientName)
        etClientPhone = findViewById(R.id.etClientPhone)
        spinnerTrainingType = findViewById(R.id.spinnerTrainingType)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        clientId = intent.getIntExtra("CLIENT_ID", -1)
        if (clientId != -1) {
            loadClientData(clientId)
        }

        loadTrainingTypes()

        btnSave.setOnClickListener {
            saveClientData()
        }

        btnCancel.setOnClickListener {
            finish() // Закрывает текущую активность и возвращает на предыдущий экран
        }
    }

    private fun loadClientData(clientId: Int) {
        val db = dbHelper.readableDatabase
        val query = """
            SELECT clients.name, clients.phone, clients.training_type_id
            FROM clients
            WHERE clients.id = ?
        """
        val cursor = db.rawQuery(query, arrayOf(clientId.toString()))
        if (cursor.moveToFirst()) {
            etClientName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")))
            etClientPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")))
            val trainingTypeId = cursor.getInt(cursor.getColumnIndexOrThrow("training_type_id"))
            spinnerTrainingType.setSelection(trainingTypeId - 1)
        }
        cursor.close()
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

    private fun saveClientData() {
        val clientName = etClientName.text.toString()
        val clientPhone = etClientPhone.text.toString()
        val trainingTypeId = (spinnerTrainingType.selectedItem as TrainingType).id

        if (clientName.isEmpty() || clientPhone.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", clientName)
            put("phone", clientPhone)
            put("training_type_id", trainingTypeId)
        }

        val rowsUpdated = db.update("clients", values, "id = ?", arrayOf(clientId.toString()))
        if (rowsUpdated > 0) {
            Toast.makeText(this, "Клиент обновлён", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Ошибка при обновлении данных", Toast.LENGTH_SHORT).show()
        }
    }

    data class TrainingType(val id: Int, val name: String) {
        override fun toString(): String {
            return name
        }
    }
}
