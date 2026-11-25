package com.example.androidserviciosynotificaciones

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

// Receptor de eventos del sistema para detectar conexion y desconexion del cargador en el Ejercicio 7
class ReceptorCargador : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        when (intent.action) {
            Intent.ACTION_POWER_CONNECTED -> {
                // Muestra un mensaje Toast cuando el cargador es conectado
                Toast.makeText(context, "Cargador conectado", Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                // Muestra un mensaje Toast cuando el cargador es desconectado
                Toast.makeText(context, "Cargador desconectado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
