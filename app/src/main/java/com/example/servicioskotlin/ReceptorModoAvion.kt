package com.example.servicioskotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReceptorModoAvion : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val estaActivado = intent.getBooleanExtra("state", false)
            val mensaje = if (estaActivado) {
                "Modo avión activado"
            } else {
                "Modo avión desactivado"
            }
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}