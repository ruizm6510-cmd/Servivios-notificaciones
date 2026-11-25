package com.example.servicioskotlin

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var receptorModoAvion: ReceptorModoAvion
    private lateinit var receptorCargador: ReceptorCargador

    private val solicitarPermisoNotificacionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { esConcedido ->
            if (esConcedido) {
                Toast.makeText(this, "Permiso de notificaciones concedido", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permiso de notificaciones denegado", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Crear canales de notificación
        NotificacionesHelper.crearCanalNotificaciones(this)

        // Solicitar permisos de notificación en Android 13+
        verificarYSolicitarPermisoNotificaciones()

        // Configurar los 8 ejercicios
        configurarEjercicio1()
        configurarEjercicio2()
        configurarEjercicio3()
        configurarEjercicio4()
        configurarEjercicio5()
        configurarEjercicio6()
        configurarEjercicio7y8()
    }

    private fun verificarYSolicitarPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                solicitarPermisoNotificacionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    // Ejercicio 1: ServicioContador
    private fun configurarEjercicio1() {
        val btnIniciar: Button = findViewById(R.id.btn_ej1_iniciar)
        val btnDetener: Button = findViewById(R.id.btn_ej1_detener)

        btnIniciar.setOnClickListener {
            startService(Intent(this, ServicioContador::class.java))
            Toast.makeText(this, "ServicioContador iniciado", Toast.LENGTH_SHORT).show()
        }

        btnDetener.setOnClickListener {
            stopService(Intent(this, ServicioContador::class.java))
            Toast.makeText(this, "ServicioContador detenido", Toast.LENGTH_SHORT).show()
        }
    }

    // Ejercicio 2: ServicioOperacion (Factorial)
    private fun configurarEjercicio2() {
        val etNumero: EditText = findViewById(R.id.et_ej2_numero)
        val btnCalcular: Button = findViewById(R.id.btn_ej2_calcular)

        btnCalcular.setOnClickListener {
            val numStr = etNumero.text.toString()
            val numero = numStr.toIntOrNull() ?: 5
            val intent = Intent(this, ServicioOperacion::class.java)
            intent.putExtra("numero", numero)
            startService(intent)
            Toast.makeText(this, "Calculando factorial de $numero en ServicioOperacion", Toast.LENGTH_SHORT).show()
        }
    }

    // Ejercicio 3: ServicioDescarga
    private fun configurarEjercicio3() {
        val btnIniciar: Button = findViewById(R.id.btn_ej3_iniciar)
        val btnDetener: Button = findViewById(R.id.btn_ej3_detener)

        btnIniciar.setOnClickListener {
            startService(Intent(this, ServicioDescarga::class.java))
            Toast.makeText(this, "ServicioDescarga iniciado", Toast.LENGTH_SHORT).show()
        }

        btnDetener.setOnClickListener {
            stopService(Intent(this, ServicioDescarga::class.java))
            Toast.makeText(this, "ServicioDescarga detenido", Toast.LENGTH_SHORT).show()
        }
    }

    // Ejercicio 4: ServicioOperacionCuadrado
    private fun configurarEjercicio4() {
        val etNumero: EditText = findViewById(R.id.et_ej4_numero)
        val btnCalcular: Button = findViewById(R.id.btn_ej4_calcular)

        btnCalcular.setOnClickListener {
            val numStr = etNumero.text.toString()
            val valor = numStr.toDoubleOrNull() ?: 0.0
            val intent = Intent(this, ServicioOperacionCuadrado::class.java)
            intent.putExtra("numero", valor)
            startService(intent)
            Toast.makeText(this, "Calculando cuadrado de $valor en ServicioOperacionCuadrado", Toast.LENGTH_SHORT).show()
        }
    }

    // Ejercicio 5: Notificación con Acciones
    private fun configurarEjercicio5() {
        val btnNotificacion: Button = findViewById(R.id.btn_ej5_notificacion)
        btnNotificacion.setOnClickListener {
            NotificacionesHelper.mostrarNotificacionConBotones(this)
        }
    }

    // Ejercicio 6: Notificación BigPictureStyle
    private fun configurarEjercicio6() {
        val btnNotificacion: Button = findViewById(R.id.btn_ej6_notificacion)
        btnNotificacion.setOnClickListener {
            NotificacionesHelper.mostrarNotificacionBigPicture(this)
        }
    }

    // Ejercicio 7 y Ejercicio 8: Receptores dinámicos
    private fun configurarEjercicio7y8() {
        receptorCargador = ReceptorCargador()
        val filtroCargador = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(receptorCargador, filtroCargador)

        receptorModoAvion = ReceptorModoAvion()
        val filtroModoAvion = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(receptorModoAvion, filtroModoAvion)
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            unregisterReceiver(receptorCargador)
            unregisterReceiver(receptorModoAvion)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}