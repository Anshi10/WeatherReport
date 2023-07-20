package com.example.weatherreport

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weatherreport.databinding.ActivityMainBinding
import org.json.JSONObject
import kotlin.math.ceil
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")

        window.statusBarColor = Color.parseColor("#1383C3")
        getJsonData(lat,long)
        
        
    }

    private fun getJsonData(lat: String?, long: String?) {
        //instatiate request queue
        val API_KEY ="f707195223d4c6c26313106316f2c512"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"

        //request a string response from the provided url
        val JsonRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                // Display the first 500 characters of the response string.
                setvalues(response)
            },
            { Toast.makeText(this,"That didn't work!",Toast.LENGTH_LONG).show() })

        //add the request to the request queue
        queue.add(JsonRequest)
    }

    private fun setvalues(response: JSONObject) {
        binding.city.text = response.getString("name")
        var lat = response.getJSONObject("coord").getString("lat")
        var long=response.getJSONObject("coord").getString("lon")
        binding.coordinates.text = "${lat} , ${long}"
        binding.weather.text = response.getJSONArray("weather").getJSONObject(0).getString("main")
        var temp = response.getJSONObject("main").getString("temp")
        temp = ((temp).toFloat() -273.15).toInt().toString()
        binding.temp.text = temp+"℃"
        var mintemp = response.getJSONObject("main").getString("temp_min")
        mintemp = ((mintemp).toFloat() -273.15).toInt().toString()
        var maxtemp = response.getJSONObject("main").getString("temp_max")
        maxtemp = ceil(((maxtemp).toFloat() -273.15)).toInt().toString()
        binding.minTemp.text = mintemp+"℃"
         binding.maxTemp.text = maxtemp+"℃"
        binding.pressure.text = response.getJSONObject("main").getString("pressure")
       binding.humidity.text = response.getJSONObject("main").getString("humidity") + "%"
        binding.wind.text = response.getJSONObject("wind").getString("speed")
        binding.degree.text  = "Degree : "+ response.getJSONObject("wind").getString("deg")


    }
}