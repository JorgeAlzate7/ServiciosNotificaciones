package com.example.androidserviciosynotificaciones

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

// Receptor para cancelar la notificacion cuando el usuario presiona la accion Cancelar en el Ejercicio 5
class ReceptorCancelarNotificacion : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            // Obtenemos el servicio de notificaciones para cancelar la notificacion con ID 1
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(1)
            Toast.makeText(context, "Notificacion cancelada", Toast.LENGTH_SHORT).show()
        }
    }
}
