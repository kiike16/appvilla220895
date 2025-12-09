package com.example.invetarioproy220895

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val email = findViewById<TextInputEditText>(R.id.regEmail)
        val password = findViewById<TextInputEditText>(R.id.regPassword)
        val btnRegister = findViewById<MaterialButton>(R.id.btnRegister)

        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)

        btnRegister.setOnClickListener {
            val userEmail = email.text.toString().trim()
            val userPass = password.text.toString().trim()

            if (userEmail.isEmpty() || userPass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Guardar datos localmente
                val editor = sharedPref.edit()
                editor.putString("email", userEmail)
                editor.putString("password", userPass)
                editor.apply()

                Toast.makeText(this, "Registro exitoso ✅", Toast.LENGTH_SHORT).show()

                // Regresar al login
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
