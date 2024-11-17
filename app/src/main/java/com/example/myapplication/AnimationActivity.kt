package com.example.myapplication

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AnimationActivity : AppCompatActivity() {

    private lateinit var carImageView: ImageView
    private var carAnimation: Animation? = null
    private var animationDuration: Long = 2000 //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish() // Завершить текущую активность и вернуться назад
        }

        carImageView = findViewById(R.id.car)

        // Устанавливаем скорость и запускаем анимацию
        setupAnimation()
    }

    // Метод для настройки анимации
    private fun setupAnimation() {
        // Загружаем анимацию из XML
        carAnimation = AnimationUtils.loadAnimation(this, R.anim.car_move).apply {
            duration = animationDuration // Устанавливаем скорость из переменной
        }

        // Запускаем анимацию на ImageView
        carImageView.startAnimation(carAnimation)
    }

}
