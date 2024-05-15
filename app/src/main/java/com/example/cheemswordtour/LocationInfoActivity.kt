package com.example.cheemswordtour

import android.os.Bundle
import android.util.Log
import android.widget.Button
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

class LocationInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_location_info)

        val txtLugar = findViewById<TextView>(R.id.lugar_text)
        val txtResponsable = findViewById<TextView>(R.id.responsable_text)
        val txtMotivo = findViewById<TextView>(R.id.motivo_text)
        val txtLongitud = findViewById<TextView>(R.id.longitud)
        val txtLatitud = findViewById<TextView>(R.id.latitud)
        val btnClose = findViewById<Button>(R.id.cerrar)

        btnClose.setOnClickListener {
            finish()
        }

        var registroId = intent.getIntExtra("registro_id", 1)
        val call : Call<Registro> = RetrofitUtil.getApi()!!.getLocation(registroId)
        call.enqueue(object : Callback<Registro> {
            override fun onResponse(call: Call<Registro>, response: Response<Registro>) {
                val registro = response.body()
                txtLugar.text = registro!!.lugar
                txtResponsable.text = registro.responsable
                txtMotivo.text = registro.motivo
                txtLatitud.text = registro.latitud.toString()
                txtLongitud.text = registro.longitud.toString()
            }
            override fun onFailure(call: Call<Registro>, t: Throwable) {
                Log.e("Error llamando a un registro", t.message.toString())
            }
        })
    }
}