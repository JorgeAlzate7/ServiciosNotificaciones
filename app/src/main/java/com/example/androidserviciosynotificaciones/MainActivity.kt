package com.example.androidserviciosynotificaciones

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

// Actividad principal para ejecutar y coordinar las pruebas de los 8 ejercicios
class MainActivity : AppCompatActivity() {

    // Declaracion de constantes para el canal de notificaciones
    private val canalId = "canal_ejercicios_sena"
    private val canalNombre = "Canal Notificaciones SENA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        crearCanalNotificaciones()
        solicitarPermisoNotificaciones()

        // Referencias a los componentes de la interfaz de usuario
        val btnEjercicio1 = findViewById<Button>(R.id.btnEjercicio1)
        val btnEjercicio2 = findViewById<Button>(R.id.btnEjercicio2)
        val btnEjercicio3 = findViewById<Button>(R.id.btnEjercicio3)
        val etNumeroEjercicio4 = findViewById<EditText>(R.id.etNumeroEjercicio4)
        val btnEjercicio4 = findViewById<Button>(R.id.btnEjercicio4)
        val btnEjercicio5 = findViewById<Button>(R.id.btnEjercicio5)
        val btnEjercicio6 = findViewById<Button>(R.id.btnEjercicio6)

        // Ejercicio 1 inicio del ServicioContador
        btnEjercicio1.setOnClickListener {
            val intent = Intent(this, ServicioContador::class.java)
            startService(intent)
            Toast.makeText(this, "ServicioContador iniciado", Toast.LENGTH_SHORT).show()
        }

        // Ejercicio 2 envio del numero 5 al ServicioOperacion mediante Intent
        btnEjercicio2.setOnClickListener {
            val intent = Intent(this, ServicioOperacion::class.java)
            intent.putExtra("numero", 5)
            startService(intent)
            Toast.makeText(this, "Enviado numero 5 para factorial", Toast.LENGTH_SHORT).show()
        }

        // Ejercicio 3 inicio del ServicioDescarga
        btnEjercicio3.setOnClickListener {
            val intent = Intent(this, ServicioDescarga::class.java)
            startService(intent)
            Toast.makeText(this, "ServicioDescarga iniciado", Toast.LENGTH_SHORT).show()
        }

        // Ejercicio 4 envio del numero ingresado por el usuario para calcular el cuadrado
        btnEjercicio4.setOnClickListener {
            val texto = etNumeroEjercicio4.text.toString()
            if (texto.isNotEmpty()) {
                val valor = texto.toDouble()
                val intent = Intent(this, ServicioOperacion::class.java)
                intent.putExtra("numero", valor)
                intent.putExtra("calcular_cuadrado", true)
                startService(intent)
                Toast.makeText(this, "Enviado $valor para calcular cuadrado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor ingrese un numero", Toast.LENGTH_SHORT).show()
            }
        }

        // Ejercicio 5 creacion de notificacion con dos acciones ver detalles y cancelar
        btnEjercicio5.setOnClickListener {
            mostrarNotificacionConAcciones()
        }

        // Ejercicio 6 notificacion con imagen grande usando BigPictureStyle
        btnEjercicio6.setOnClickListener {
            mostrarNotificacionImagenGrande()
        }
    }

    // Creacion del canal de notificaciones requerido para Android 8 en adelante
    private fun crearCanalNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                canalId,
                canalNombre,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(canal)
        }
    }

    // Solicitar permiso de notificaciones para Android 13 o superior
    private fun solicitarPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 101)
            }
        }
    }

    // Ejercicio 5 crear notificacion con acciones Ver detalles y Cancelar
    private fun mostrarNotificacionConAcciones() {
        // Intent para abrir DetalleActivity al presionar Ver detalles
        val intentDetalle = Intent(this, DetalleActivity::class.java)
        val pendingIntentDetalle = PendingIntent.getActivity(
            this,
            0,
            intentDetalle,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent para cancelar la notificacion al presionar Cancelar
        val intentCancelar = Intent(this, ReceptorCancelarNotificacion::class.java)
        val pendingIntentCancelar = PendingIntent.getBroadcast(
            this,
            1,
            intentCancelar,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Construccion de la notificacion con ambas acciones
        val notificacion = NotificationCompat.Builder(this, canalId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Ejercicio 5: Notificacion")
            .setContentText("Seleccione una de las acciones disponibles")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(android.R.drawable.ic_menu_info_details, "Ver detalles", pendingIntentDetalle)
            .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Cancelar", pendingIntentCancelar)
            .build()

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notificacion)
    }

    // Ejercicio 6 notificacion con estilo de imagen grande BigPictureStyle
    private fun mostrarNotificacionImagenGrande() {
        // Conversion del icono de la app a Bitmap para la imagen grande
        val imagenBitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)

        val estiloImagenGrande = NotificationCompat.BigPictureStyle()
            .bigPicture(imagenBitmap)
            .setBigContentTitle("Ejercicio 6: Imagen Grande")
            .setSummaryText("Notificacion expandible con vista de imagen grande")

        val notificacion = NotificationCompat.Builder(this, canalId)
            .setSmallIcon(android.R.drawable.ic_menu_gallery)
            .setContentTitle("Ejercicio 6: Notificacion con Imagen")
            .setContentText("Despliega la notificacion para ver la imagen grande")
            .setStyle(estiloImagenGrande)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(2, notificacion)
    }
}
