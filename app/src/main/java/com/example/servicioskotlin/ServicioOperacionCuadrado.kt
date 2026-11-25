package com.example.servicioskotlin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServicioOperacionCuadrado : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val numero = intent?.getDoubleExtra("numero", 0.0) ?: 0.0
        val cuadrado = numero * numero

        Log.d("ServicioOperacionCuadrado", "Número recibido: $numero")
        Log.d("ServicioOperacionCuadrado", "El cuadrado de $numero es $cuadrado")

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}