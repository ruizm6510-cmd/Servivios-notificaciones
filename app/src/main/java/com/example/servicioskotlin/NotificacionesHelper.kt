package com.example.servicioskotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificacionesHelper {

    const val CANAL_ID = "canal_ejercicios"
    const val CANAL_NOMBRE = "Canal de Notificaciones de Ejercicios"
    const val NOTIFICACION_BOTONES_ID = 101
    const val NOTIFICACION_BIG_PICTURE_ID = 102

    fun crearCanalNotificaciones(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CANAL_ID,
                CANAL_NOMBRE,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Canal para notificaciones de ejercicios de servicios"
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Ejercicio 5: Notificación con Botones de Acción
    fun mostrarNotificacionConBotones(context: Context) {
        crearCanalNotificaciones(context)

        // Intent para "Ver detalles" -> Abre DetalleActivity
        val intentDetalles = Intent(context, DetalleActivity::class.java)
        val pendingIntentDetalles = PendingIntent.getActivity(
            context,
            0,
            intentDetalles,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent para "Cancelar" -> Ejecuta AccionCancelarReceiver
        val intentCancelar = Intent(context, AccionCancelarReceiver::class.java).apply {
            putExtra("notificacion_id", NOTIFICACION_BOTONES_ID)
        }
        val pendingIntentCancelar = PendingIntent.getBroadcast(
            context,
            1,
            intentCancelar,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CANAL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Ejercicio 5: Notificación con Acciones")
            .setContentText("Selecciona una acción a continuación:")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .addAction(
                android.R.drawable.ic_menu_info_details,
                "Ver detalles",
                pendingIntentDetalles
            )
            .addAction(
                android.R.drawable.ic_menu_close_clear_cancel,
                "Cancelar",
                pendingIntentCancelar
            )

        val notificationManager = NotificationManagerCompat.from(context)
        try {
            notificationManager.notify(NOTIFICACION_BOTONES_ID, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    // Ejercicio 6: Notificación con BigPictureStyle
    fun mostrarNotificacionBigPicture(context: Context) {
        crearCanalNotificaciones(context)

        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sample_big_picture)

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(bitmap)
            .setBigContentTitle("Ejercicio 6: Imagen Grande")
            .setSummaryText("Demostración del estilo BigPictureStyle en Android")

        val builder = NotificationCompat.Builder(context, CANAL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Ejercicio 6: Imagen Grande")
            .setContentText("Despliega la notificación para ver la imagen completa.")
            .setStyle(bigPictureStyle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        try {
            notificationManager.notify(NOTIFICACION_BIG_PICTURE_ID, builder.build())
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }
}