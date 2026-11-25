package com.example.androidserviciosynotificaciones

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

// Servicio que ejecuta un conteo en segundo plano sin bloquear la interfaz
class ServicioContador : Service() {

    // Variable para controlar si el hilo debe continuar ejecutandose
    private var ejecutando = false
    private var hiloContador: Thread? = null

    override fun onBind(intent: Intent?): IBinder? {
        // Un servicio iniciado no requiere enlace por lo que se retorna null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ServicioContador", "Servicio iniciado")

        ejecutando = true

        // Se crea e inicia un hilo para no bloquear la interfaz de usuario
        hiloContador = Thread {
            for (i in 1..10) {
                if (!ejecutando) break

                Log.d("ServicioContador", "Valor: $i")

                try {
                    // Pausa de 1 segundo entre cada numero
                    Thread.sleep(1000)
                } catch (_: InterruptedException) {
                    Log.d("ServicioContador", "Hilo interrumpido")
                    break
                }
            }
            // Se detiene el servicio al terminar la tarea
            stopSelf()
        }

        hiloContador?.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Se detiene el hilo correctamente cuando el servicio es destruido
        ejecutando = false
        hiloContador?.interrupt()
        Log.d("ServicioContador", "Servicio destruido y hilo detenido")
    }
}
