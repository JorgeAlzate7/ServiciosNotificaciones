package com.example.androidserviciosynotificaciones

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

// Receptor de eventos del sistema para detectar cambios en el modo avion para el Ejercicio 8
class ReceptorModoAvion : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val estaActivado = intent.getBooleanExtra("state", false)
            if (estaActivado) {
                // Mensaje Toast al activar modo avion
                Toast.makeText(context, "Modo avion activado", Toast.LENGTH_SHORT).show()
            } else {
                // Mensaje Toast al desactivar modo avion
                Toast.makeText(context, "Modo avion desactivado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
