package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "fitnessDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        // Таблица для клиентов
        db.execSQL(
            "CREATE TABLE clients (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "phone TEXT," +
                    "training_type_id INTEGER," +
                    "FOREIGN KEY(training_type_id) REFERENCES training_types(id))"
        )

        // Таблица для типов тренировок
        db.execSQL(
            "CREATE TABLE training_types (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "type_name TEXT," +
                    "cost INTEGER," +
                    "duration INTEGER" +
                    ")"
        )

        // Добавляем предустановленные типы тренировок
        insertPredefinedTrainingTypes(db)
    }

    private fun insertPredefinedTrainingTypes(db: SQLiteDatabase) {
        val trainingTypes = listOf(
            Triple("Йога", 20, 60),
            Triple("Кардио", 30, 45),
            Triple("Силовая тренировка", 50, 90),
            Triple("Плавание", 40, 60)
        )

        for ((typeName, cost, duration) in trainingTypes) {
            val values = ContentValues().apply {
                put("type_name", typeName)
                put("cost", cost)
                put("duration", duration)
            }
            db.insert("training_types", null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS clients")
        db.execSQL("DROP TABLE IF EXISTS training_types")
        onCreate(db)
    }
}
