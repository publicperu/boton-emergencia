package com.example.sos

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.TextView

class MainActivity : Activity() {

    private val NUMERO = "989957003"
    private val NOMBRE = "Emergencia Hortifrut"
    private val RETRASO_MS = 1500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val aviso = TextView(this).apply {
            text = "Llamando a\n$NOMBRE"
            textSize = 40f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#E0505F"))
            gravity = Gravity.CENTER
            setPadding(48, 48, 48, 48)
        }
        setContentView(aviso)

        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Handler(Looper.getMainLooper()).postDelayed({ llamar() }, RETRASO_MS)
        } else {
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 1)
        }
    }

    private fun llamar() {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$NUMERO")))
        finish()
    }

    override fun onRequestPermissionsResult(code: Int, perms: Array<out String>, results: IntArray) {
        super.onRequestPermissionsResult(code, perms, results)
        if (code == 1 && results.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            Handler(Looper.getMainLooper()).postDelayed({ llamar() }, RETRASO_MS)
        } else {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$NUMERO")))
            finish()
        }
    }
}
