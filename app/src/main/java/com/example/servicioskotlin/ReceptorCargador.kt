package com.example.servicioskotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ReceptorCargador : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                Toast.makeText(context, "Cargador CONECTADO", Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                Toast.makeText(context, "Cargador DESCONECTADO", Toast.LENGTH_SHORT).show()
            }
        }
    }
}