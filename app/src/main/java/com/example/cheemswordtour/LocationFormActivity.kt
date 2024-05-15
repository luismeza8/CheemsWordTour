package com.example.cheemswordtour

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cheemswordtour.entities.Registro
import com.example.cheemswordtour.utilerias.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_location_form)

        val txtResposible = findViewById<EditText>(R.id.responsable_text)
        val txtMotive = findViewById<EditText>(R.id.motivo_text)
        val btnSave = findViewById<Button>(R.id.button_guardar)
        val btnCancel = findViewById<Button>(R.id.button_cancelar)
        val latitude = intent.getDoubleExtra("latitud", 0.0)
        val longitude = intent.getDoubleExtra("longitud", 0.0)

        btnCancel.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            val registro = Registro()
            registro.responsable = txtResposible.text.toString()
            registro.motivo = txtMotive.text.toString()
            registro.latitud = latitude
            registro.longitud = longitude

            val call : Call<Registro> = RetrofitUtil.getApi()!!.addLocation(registro)
            call.enqueue(object : Callback<Registro> {
                override fun onResponse(call: Call<Registro>, response: Response<Registro>) {
                    Log.e("yeap", "yeap x2")
                    val resultIntent = Intent()
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
                override fun onFailure(call: Call<Registro>, t: Throwable) {
                    Log.e("error creado registro", t.message.toString())
                }
            })
        }
    }
}