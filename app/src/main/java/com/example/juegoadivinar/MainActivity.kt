package com.example.juegoadivinar

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var numInt = 5   // intentos por defecto
    private var numMax = 10  // número máximo por defecto
    private var numOculto = 0
    private var intentos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnConfigurar = findViewById<Button>(R.id.btnConfigurar)
        val btnJugar = findViewById<Button>(R.id.btnJugar)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        val tvJuego = findViewById<TextView>(R.id.tvJuego)
        val etNumero = findViewById<EditText>(R.id.etNumero)
        val btnProbar = findViewById<Button>(R.id.btnProbar)

        // OPCIÓN 1: Configurar
        btnConfigurar.setOnClickListener {
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL

            val inputInt = EditText(this)
            inputInt.hint = "Número de intentos"
            inputInt.inputType = android.text.InputType.TYPE_CLASS_NUMBER

            val inputMax = EditText(this)
            inputMax.hint = "Número máximo"
            inputMax.inputType = android.text.InputType.TYPE_CLASS_NUMBER

            layout.addView(inputInt)
            layout.addView(inputMax)

            AlertDialog.Builder(this)
                .setTitle("Configurar juego")
                .setView(layout)
                .setPositiveButton("Guardar") { _, _ ->
                    numInt = inputInt.text.toString().toIntOrNull() ?: numInt
                    numMax = inputMax.text.toString().toIntOrNull() ?: numMax
                    Toast.makeText(this, "Configurado: $numInt intentos, máximo $numMax", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        // OPCIÓN 2: Jugar
        btnJugar.setOnClickListener {
            numOculto = Random.nextInt(0, numMax + 1)
            intentos = 0

            tvJuego.text = "Adivina el número (0 - $numMax). Intentos: $numInt"
            tvJuego.visibility = TextView.VISIBLE
            etNumero.visibility = EditText.VISIBLE
            btnProbar.visibility = Button.VISIBLE
        }

        btnProbar.setOnClickListener {
            val numUsuario = etNumero.text.toString().toIntOrNull()
            if (numUsuario == null) {
                Toast.makeText(this, "Introduce un número válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            intentos++

            if (numUsuario == numOculto) {
                tvJuego.text = "🎉 ¡Has ganado! Necesitaste $intentos intentos."
                etNumero.visibility = EditText.GONE
                btnProbar.visibility = Button.GONE
            } else if (intentos >= numInt) {
                tvJuego.text = "❌ Perdiste, intentos consumidos. El número era $numOculto"
                etNumero.visibility = EditText.GONE
                btnProbar.visibility = Button.GONE
            } else {
                if (numUsuario < numOculto) {
                    tvJuego.text = "El número oculto es MAYOR. Intentos restantes: ${numInt - intentos}"
                } else {
                    tvJuego.text = "El número oculto es MENOR. Intentos restantes: ${numInt - intentos}"
                }
            }

            etNumero.text.clear()
        }

        // OPCIÓN 3: Salir
        btnSalir.setOnClickListener {
            finish()
        }
    }
}
