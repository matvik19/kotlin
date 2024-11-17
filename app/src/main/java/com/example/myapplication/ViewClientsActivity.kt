package com.example.myapplication

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ViewClientsActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_clients)

        dbHelper = DBHelper(this)
        val dataContainer = findViewById<LinearLayout>(R.id.dataContainer)

        val clients = loadClientsWithTrainingTypes()
        if (clients.isEmpty()) {
            Toast.makeText(this, "Нет данных для отображения", Toast.LENGTH_SHORT).show()
        } else {
            displayClients(clients, dataContainer)
        }

        // Кнопка "Назад"
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    private fun loadClientsWithTrainingTypes(): List<ClientWithTraining> {
        val db = dbHelper.readableDatabase
        val query = """
            SELECT clients.id AS client_id, clients.name, clients.phone, 
                   training_types.type_name, training_types.cost, training_types.duration
            FROM clients
            JOIN training_types ON clients.training_type_id = training_types.id
        """
        val cursor: Cursor = db.rawQuery(query, null)

        val clients = mutableListOf<ClientWithTraining>()
        while (cursor.moveToNext()) {
            val clientId = cursor.getInt(cursor.getColumnIndexOrThrow("client_id"))
            val clientName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val clientPhone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
            val trainingType = cursor.getString(cursor.getColumnIndexOrThrow("type_name"))
            val trainingCost = cursor.getInt(cursor.getColumnIndexOrThrow("cost"))
            val trainingDuration = cursor.getInt(cursor.getColumnIndexOrThrow("duration"))

            clients.add(ClientWithTraining(clientId, clientName, clientPhone, trainingType, trainingCost, trainingDuration))
        }
        cursor.close()
        return clients
    }

    private fun deleteClient(clientId: Int) {
        val db = dbHelper.writableDatabase
        val rowsDeleted = db.delete("clients", "id = ?", arrayOf(clientId.toString()))
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Клиент удалён", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Ошибка при удалении клиента", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    private fun updateTotalCostView(container: LinearLayout, totalCost: Int) {
        // Удаляем старое представление общей стоимости, если оно существует
        val existingTotalCostView = container.findViewWithTag<TextView>("totalCostView")
        if (existingTotalCostView != null) {
            container.removeView(existingTotalCostView)
        }

        // Создаём новое представление для общей стоимости
        val totalCostView = TextView(this).apply {
            text = "Общая стоимость всех тренировок: $totalCost ₽"
            textSize = 18f
            setPadding(16, 16, 16, 16)
            tag = "totalCostView" // Устанавливаем тег для идентификации этого TextView
        }

        // Добавляем представление общей стоимости в контейнер
        container.addView(totalCostView)
    }


    private fun displayClients(clients: List<ClientWithTraining>, container: LinearLayout) {
        container.removeAllViews()

        var totalCost = 0 // Переменная для подсчёта общей стоимости тренировок

        for (client in clients) {
            // Создаём TextView для отображения данных клиента
            val clientView = TextView(this).apply {
                text = "Имя: ${client.name}\n" +
                        "Телефон: ${client.phone}\n" +
                        "Тренировка: ${client.trainingType}\n" +
                        "Стоимость: ${client.trainingCost}\n" +
                        "Длительность: ${client.trainingDuration} минут\n"
                textSize = 16f
                setPadding(16, 16, 16, 16)
            }

            // Горизонтальный контейнер для кнопок
            val buttonContainer = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 8, 0, 16) // Отступы между элементами
            }

            // Кнопка "Редактировать"
            val editButton = Button(this).apply {
                text = "Редактировать"
                textSize = 12f
                setOnClickListener {
                    val intent = Intent(this@ViewClientsActivity, EditClientActivity::class.java)
                    intent.putExtra("CLIENT_ID", client.clientId) // Передаём ID клиента
                    startActivity(intent)
                }
            }

            // Кнопка "Удалить"
            val deleteButton = Button(this).apply {
                text = "Удалить"
                textSize = 12f
                setOnClickListener {
                    deleteClient(client.clientId) // Удаление записи из базы данных
                    container.removeView(clientView) // Удаляем визуально
                    container.removeView(buttonContainer) // Удаляем кнопки
                    totalCost -= client.trainingCost // Обновляем общую стоимость
                    updateTotalCostView(container, totalCost) // Обновляем отображение общей стоимости
                    Toast.makeText(this@ViewClientsActivity, "Запись удалена", Toast.LENGTH_SHORT).show()
                }
            }

            // Добавляем стоимость текущей тренировки к общей стоимости
            totalCost += client.trainingCost

            // Добавляем кнопки в горизонтальный контейнер
            buttonContainer.addView(editButton)
            buttonContainer.addView(deleteButton)

            // Добавляем TextView и контейнер кнопок в основной контейнер
            container.addView(clientView)
            container.addView(buttonContainer)
        }

        // Отображаем общую стоимость тренировок
        updateTotalCostView(container, totalCost)
    }


    data class ClientWithTraining(
        val clientId: Int,
        val name: String,
        val phone: String,
        val trainingType: String,
        val trainingCost: Int,
        val trainingDuration: Int
    )
}
