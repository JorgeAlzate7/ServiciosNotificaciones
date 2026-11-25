package com.example.androidserviciosynotificaciones

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// Actividad secundaria que se abre desde la accion Ver detalles de la notificacion en el Ejercicio 5
class DetalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
    }
}
