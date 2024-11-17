package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DatabaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        // Кнопка "Добавить клиента"
        val btnAddClient = findViewById<Button>(R.id.btnAddClient)
        btnAddClient.setOnClickListener {
            val intent = Intent(this, AddClientActivity::class.java)
            startActivity(intent)
        }

        // Кнопка "Добавить тип тренировки"
        val btnAddTrainingType = findViewById<Button>(R.id.btnAddTrainingType)
        btnAddTrainingType.setOnClickListener {
            val intent = Intent(this, AddTrainingTypeActivity::class.java)
            startActivity(intent)
        }

        // Кнопка "Просмотреть записи"
        val btnViewClients = findViewById<Button>(R.id.btnViewClients)
        btnViewClients.setOnClickListener {
            val intent = Intent(this, ViewClientsActivity::class.java)
            startActivity(intent)
        }

        // Кнопка "Назад"
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }
    }
}
