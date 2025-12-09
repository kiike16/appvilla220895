package com.example.invetarioproy220895

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.graphics.toColorInt
import androidx.core.text.HtmlCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_NAME = "user_data"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        private const val KEY_SUBNOMBRE = "subnombre"
        private const val KEY_CARGO = "cargo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias UI
        val email = findViewById<TextInputEditText>(R.id.inputEmail)
        val password = findViewById<TextInputEditText>(R.id.inputPassword)
        val subnombre = findViewById<TextInputEditText>(R.id.inputSubnombre)
        val spinnerCargo = findViewById<Spinner>(R.id.spCargo)
        val loginButton = findViewById<MaterialButton>(R.id.btnLogin)
        val txtRegister = findViewById<TextView>(R.id.txtRegister)

        // SharedPrefs
        val sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedEmail = sharedPref.getString(KEY_EMAIL, null)
        val savedPassword = sharedPref.getString(KEY_PASSWORD, null)
        val savedCargo = sharedPref.getString(KEY_CARGO, null)
        val savedSub = sharedPref.getString(KEY_SUBNOMBRE, null)

        // Prefill si hubiera datos
        if (!savedSub.isNullOrBlank()) subnombre.setText(savedSub)

        // Cargar cargos desde resources
        val cargosArray = resources.getStringArray(R.array.roles_array)
        val cargoAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.roles_array,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerCargo.adapter = cargoAdapter

        // Seleccionar cargo previamente guardado (si existe)
        if (!savedCargo.isNullOrBlank()) {
            val idx = cargosArray.indexOf(savedCargo)
            if (idx >= 0) spinnerCargo.setSelection(idx)
        }

        // Ir a registro
        txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Login
        loginButton.setOnClickListener {
            val user = email.text?.toString()?.trim().orEmpty()
            val pass = password.text?.toString()?.trim().orEmpty()
            val subname = subnombre.text?.toString()?.trim().orEmpty()
            val cargoSeleccionado = spinnerCargo.selectedItem?.toString().orEmpty()

            // Validaciones simples
            when {
                user.isEmpty() -> {
                    email.error = getString(R.string.required_field)
                    email.requestFocus()
                }
                pass.isEmpty() -> {
                    password.error = getString(R.string.required_field)
                    password.requestFocus()
                }
                subname.isEmpty() -> {
                    subnombre.error = getString(R.string.required_field)
                    subnombre.requestFocus()
                }
                else -> {
                    if (user == savedEmail && pass == savedPassword) {

                        val privacyText = """
                            <b>AVISO DE PRIVACIDAD DE STOCKNOVA</b><br><br>
                            En cumplimiento con la <i>Ley Federal de Protección de Datos Personales en Posesión de los Particulares</i>,
                            StockNova informa que los datos personales proporcionados por los usuarios serán tratados conforme a los
                            principios de licitud, consentimiento, información, calidad, finalidad, lealtad, proporcionalidad y responsabilidad.<br><br>

                            <b>1. Datos personales recabados:</b><br>
                            Nombre, correo electrónico, subnombre y contraseña, utilizados únicamente para la creación y acceso a la cuenta.<br><br>

                            <b>2. Finalidad:</b><br>
                            • Verificar la identidad del usuario.<br>
                            • Permitir el acceso seguro a la aplicación.<br>
                            • Notificar información relacionada con su cuenta.<br><br>

                            <b>3. Cargo:</b><br>
                            Cargo seleccionado: <b>$cargoSeleccionado</b>.<br><br>

                            <b>4. Protección y seguridad:</b><br>
                            StockNova implementa medidas técnicas y administrativas para proteger tus datos contra pérdida o acceso no autorizado.<br><br>

                            <i>Última actualización: Noviembre 2025</i>
                        """.trimIndent()

                        val textView = TextView(this).apply {
                            text = HtmlCompat.fromHtml(privacyText, HtmlCompat.FROM_HTML_MODE_LEGACY)
                            setPadding(40, 30, 40, 10)
                            textSize = 15f
                            setTextColor("#212121".toColorInt()) // KTX
                            movementMethod = ScrollingMovementMethod()
                        }

                        val scrollView = ScrollView(this).apply { addView(textView) }

                        AlertDialog.Builder(this)
                            .setTitle(getString(R.string.privacy_title))
                            .setView(scrollView)
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                                // Guardar subnombre, cargo y (opcional) email con KTX
                                sharedPref.edit {
                                    putString(KEY_SUBNOMBRE, subname)
                                    putString(KEY_CARGO, cargoSeleccionado)
                                    putString(KEY_EMAIL, user) // opcional
                                }

                                val intent = Intent(this, HomeActivity::class.java).apply {
                                    putExtra("nombre", subname)
                                    putExtra("cargo", cargoSeleccionado)
                                }
                                startActivity(intent)
                                finish()
                            }
                            .setNegativeButton(getString(R.string.reject)) { dialog, _ ->
                                dialog.dismiss()
                                Toast.makeText(
                                    this,
                                    getString(R.string.must_accept_privacy),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .show()

                    } else {
                        Toast.makeText(this, getString(R.string.bad_credentials), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
