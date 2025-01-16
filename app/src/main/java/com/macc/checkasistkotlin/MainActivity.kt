package com.macc.checkasistkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var txtEmployee: TextView
    private lateinit var tbxEmployee: EditText
    private lateinit var btnCheck: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()

        btnCheck.setOnClickListener {
            checkAsist()
        }
    }

    private fun checkAsist() {
        val idEmployee = getIntFromInput(tbxEmployee)

        if (idEmployee == 0) {
            Toast.makeText(this, "Ingrese un ID valido", Toast.LENGTH_LONG).show()
            return
        }
        fetchDataFromApi(idEmployee)
    }

    private fun getIntFromInput(input: EditText): Int {
        val text = input.text.toString()
        return text.toIntOrNull() ?: 0
    }

    private fun initComponents() {
        txtEmployee = findViewById(R.id.txtEmployee)
        tbxEmployee = findViewById(R.id.tbxEmployee)
        btnCheck = findViewById(R.id.btnCheck)
    }

    private fun fetchDataFromApi(idEmployee: Int) {
        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.postAsistencia( idEmployee = idEmployee )

                if (!response.success) {
                    val jsonResponse = response.data.toString()
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("INFO")
                        .setMessage(jsonResponse)
                        .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                        .show()
                    Toast.makeText(this@MainActivity, "Asistencia Registrada: ${response.message}", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.message}", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Su asistencia ya ha sido registrada! ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
