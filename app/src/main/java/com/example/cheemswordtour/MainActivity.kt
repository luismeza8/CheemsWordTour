package com.example.cheemswordtour

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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

        val btn_create = findViewById<Button>(R.id.add_button)
        btn_create.setOnClickListener {
            map?.addMarker(MarkerOptions().position(LatLng(27.0, -110.0)).draggable(true))
            map?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(27.0, -110.0)))
            map?.animateCamera(CameraUpdateFactory.zoomTo(12f))

            map?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
                override fun onMarkerDrag(p0: Marker) { }

                override fun onMarkerDragEnd(marker: Marker) {
                    val position = marker.position
                    val intent = Intent(context, LocationFormActivity::class.java)
                    intent.putExtra("latitud", position.latitude)
                    intent.putExtra("longitud", position.longitude)
                    startActivityForResult(intent, 1)
                }
                override fun onMarkerDragStart(p0: Marker) { }
            })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                recreate()
            }
        }
    }
}