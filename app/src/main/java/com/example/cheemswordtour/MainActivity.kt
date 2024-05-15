package com.example.cheemswordtour

import android.content.Context
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
        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val call : Call<Registro> = RetrofitUtil.getApi()!!.getLocation(1)
        call.enqueue(object : Callback<Registro>{
            override fun onResponse(
                call: Call<Registro>,
                response: Response<Registro>
            ) {
            try {
                Log.e("bien", "yeap")
                val registro : Registro? = response.body()
                Toast.makeText(context, "${registro?.responsable}", Toast.LENGTH_SHORT).show()
            }catch (e: Exception){
                Log.e("error", e.message.toString())
            }

        }

            override fun onFailure(call: Call<Registro>, t: Throwable) {
                Log.e("error llamando a la api", t.message.toString())
            }

        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            map = googleMap
            map?.mapType = GoogleMap.MAP_TYPE_HYBRID

            map?.clear()
            val latLong = LatLng(27.9681409, -110.9189332)
            map?.addMarker(MarkerOptions().position(latLong).draggable(true))
            map?.moveCamera(CameraUpdateFactory.newLatLng(latLong))
            map?.animateCamera(CameraUpdateFactory.zoomTo(12f))
            map?.setOnMarkerDragListener(object: GoogleMap.OnMarkerDragListener {
                override fun onMarkerDrag(marker: Marker) {}
                override fun onMarkerDragEnd(marker: Marker) {
                    val latLng = marker.position
                    Toast.makeText(context, "${latLng.latitude}", Toast.LENGTH_SHORT).show()
                }
                override fun onMarkerDragStart(marker: Marker) {}
            })
        }catch (ex: Exception){
            Log.e("Error loading map", ex.message.toString())
        }

    }
}