package com.example.androidserviciosynotificaciones

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

// Servicio para realizar operaciones matematicas en segundo plano para el Ejercicio 2 y Ejercicio 4
class ServicioOperacion : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        // No se requiere enlace para este servicio por lo que retorna null
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ServicioOperacion", "Servicio de operaciones iniciado")

        // Verificamos si la solicitud es para el Ejercicio 4 calcular cuadrado
        if (intent != null && intent.hasExtra("calcular_cuadrado")) {
            val valor = intent.getDoubleExtra("numero", 0.0)

            // Hilo en segundo plano para calcular el cuadrado
            Thread {
                val cuadrado = valor * valor
                Log.d("ServicioOperacion", "El cuadrado de $valor es $cuadrado")
                stopSelf()
            }.start()

        } else if (intent != null && intent.hasExtra("numero")) {
            // Ejercicio 2 calcular factorial de un entero
            val numero = intent.getIntExtra("numero", 0)

            // Hilo en segundo plano para calcular el factorial
            Thread {
                var factorial: Long = 1
                for (i in 1..numero) {
                    factorial *= i
                }
                Log.d("ServicioOperacion", "Factorial de $numero es $factorial")
                stopSelf()
            }.start()
        }

        return START_NOT_STICKY
    }
}
