package com.example.androidserviciosynotificaciones

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

// Servicio que simula la descarga de 10 archivos en segundo plano para el Ejercicio 3
class ServicioDescarga : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        // No requiere enlace por lo que retorna null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ServicioDescarga", "Iniciando simulacion de descarga")

        // Hilo secundario para realizar la simulacion de descarga
        Thread {
            for (i in 1..10) {
                Log.d("ServicioDescarga", "Archivo descargado: $i de 10")
                try {
                    // Espera de 1 segundo entre cada archivo
                    Thread.sleep(1000)
                } catch (_: InterruptedException) {
                    break
                }
            }
            Log.d("ServicioDescarga", "Descarga completada")
            stopSelf()
        }.start()

        return START_NOT_STICKY
    }
}
