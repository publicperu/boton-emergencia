package com.example.sos

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private val NUMERO = "105"
    private val RETRASO_MS = 1500L

    private fun dp(valor: Int): Int =
        (valor * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val titulo = TextView(this).apply {
            text = "EMERGENCIA\nHORTIFRUT"
            textSize = 30f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val subtitulo = TextView(this).apply {
            text = "Llamando..."
            textSize = 18f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(0, dp(12), 0, 0)
        }

        val zonaTextos = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
            )
            addView(titulo)
            addView(subtitulo)
        }

        val icono = ImageView(this).apply {
            setImageResource(R.drawable.icono_aviso)
            // Convierte las líneas negras del icono en blancas
            setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
            scaleType = ImageView.ScaleType.FIT_CENTER
            layoutParams = LinearLayout.LayoutParams(dp(140), dp(140)).apply {
                bottomMargin = dp(48)
            }
        }

        val raiz = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setBackgroundColor(Color.parseColor("#B31E2D"))
            setPadding(dp(24), dp(24), dp(24), dp(24))
            addView(zonaTextos)
            addView(icono)
        }
        setContentView(raiz)

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
