package com.example.servicioskotlin

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AccionCancelarReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("notificacion_id", NotificacionesHelper.NOTIFICACION_BOTONES_ID)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)

        Toast.makeText(context, "Notificación cancelada por el usuario", Toast.LENGTH_SHORT).show()
    }
}