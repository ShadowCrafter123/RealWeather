package com.example.realweather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realweather.api.Constant
import com.example.realweather.api.NetworkResponse
import com.example.realweather.api.RetrofitInstance
import com.example.realweather.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel(){
    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()  // Permite que o WeatherViewModel atualize os dados do clima.
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city : String){
        _weatherResult.value = NetworkResponse.Loading

        //Log.i("City name", city)
        viewModelScope.launch {

            try {
                val response = weatherApi.getWeather(Constant.apiKey, city)

                if(response.isSuccessful){
                    //Log.i("Response: ", response.body().toString())
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else{
                    //Log.i("Error: ", response.message())
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            }
            catch (e : Exception){
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }
    }
}