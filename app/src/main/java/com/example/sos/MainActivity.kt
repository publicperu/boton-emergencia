package com.example.sos

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {

    private val NUMERO = "989957003"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val boton = Button(this).apply {
            text = "LLAMADA DE\nEMERGENCIA\n$NUMERO"
            textSize = 30f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.RED)
            setOnClickListener { confirmar() }
        }
        setContentView(boton)
    }

    private fun confirmar() {
        AlertDialog.Builder(this)
            .setTitle("¿Llamar al $NUMERO?")
            .setPositiveButton("Llamar") { _, _ -> llamar() }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun llamar() {
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$NUMERO")))
        } else {
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 1)
        }
    }

    override fun onRequestPermissionsResult(code: Int, perms: Array<out String>, results: IntArray) {
        super.onRequestPermissionsResult(code, perms, results)
        if (code == 1 && results.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            llamar()
        } else {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$NUMERO")))
        }
    }
}
