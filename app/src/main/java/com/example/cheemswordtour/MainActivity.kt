package com.example.cheemswordtour

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cheemswordtour.entities.Registro
import com.example.cheemswordtour.utilerias.RetrofitUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    var context : Context = this
    var map : GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            map = googleMap
            map?.mapType = GoogleMap.MAP_TYPE_HYBRID

            val call : Call<List<Registro>> = RetrofitUtil.getApi()!!.getLocations()
            call.enqueue(object  : Callback<List<Registro>> {
                override fun onResponse(
                    call: Call<List<Registro>>,
                    response: Response<List<Registro>>
                ) {
                 val registros = response.body()
                    for (registro in registros!!) {
                        val latLng = LatLng(registro.latitud!!, registro.longitud!!)
                        val marker = map?.addMarker(MarkerOptions().position(latLng).draggable(false))
                        marker?.tag = registro.id
                    }
                    map?.setOnMarkerClickListener { marker ->
                        val registroId = marker.tag as? Int
                        val intent = Intent(context, LocationInfoActivity::class.java)
                        intent.putExtra("registro_id", registroId)
                        startActivity(intent)
                        false
                    }
                }
                override fun onFailure(call: Call<List<Registro>>, t: Throwable) {
                    Log.e("Error llamada api", t.message.toString())
                }
            })

            map?.clear()
        }catch (ex: Exception){
            Log.e("Error loading map", ex.message.toString())
        }

    }
}