package com.example.servicioskotlin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ServicioDescarga : Service() {

    private var hiloDescarga: Thread? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ServicioDescarga", "Iniciando simulación de descarga...")

        hiloDescarga?.interrupt()

        hiloDescarga = Thread {
            try {
                for (i in 1..10) {
                    if (Thread.currentThread().isInterrupted) break
                    Thread.sleep(1000)
                    Log.d("ServicioDescarga", "Archivo descargado: $i de 10")
                }
                Log.d("ServicioDescarga", "Descarga completada")
            } catch (e: InterruptedException) {
                Log.d("ServicioDescarga", "Descarga cancelada")
            }
        }.apply {
            start()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d("ServicioDescarga", "ServicioDescarga destruido")
        hiloDescarga?.interrupt()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}