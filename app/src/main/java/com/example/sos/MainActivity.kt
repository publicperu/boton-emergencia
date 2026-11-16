package com.example.sos

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {

    private val NUMERO = "989957003"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            llamar()
        } else {
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 1)
        }
    }

  private fun llamar() {
    Toast.makeText(this, "Llamando a Emergencia Hortifrut…", Toast.LENGTH_SHORT).show()
    startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$NUMERO")))
    finish()
}

    override fun onRequestPermissionsResult(code: Int, perms: Array<out String>, results: IntArray) {
        super.onRequestPermissionsResult(code, perms, results)
        if (code == 1 && results.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            llamar()
        } else {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$NUMERO")))
            finish()
        }
    }
}
