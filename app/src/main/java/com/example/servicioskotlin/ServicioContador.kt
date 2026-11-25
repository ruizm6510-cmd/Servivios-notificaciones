package com.example.servicioskotlin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServicioContador : Service() {

    private var hiloContador: Thread? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ServicioContador", "ServicioContador arrancado (startId: $startId)")

        // Detener hilo anterior si ya estuviera corriendo
        hiloContador?.interrupt()

        hiloContador = Thread {
            try {
                for (i in 1..10) {
                    if (Thread.currentThread().isInterrupted) break
                    Log.d("ServicioContador", "Valor: $i")
                    Thread.sleep(1000)
                }
                Log.d("ServicioContador", "Conteo finalizado")
            } catch (e: InterruptedException) {
                Log.d("ServicioContador", "Hilo del contador interrumpido")
            }
        }.apply {
            start()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d("ServicioContador", "ServicioContador detenido")
        hiloContador?.interrupt()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}