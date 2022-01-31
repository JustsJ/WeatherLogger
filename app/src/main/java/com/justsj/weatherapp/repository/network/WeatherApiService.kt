package com.justsj.weatherapp.repository.network


import com.justsj.WeatherLogger.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = BuildConfig.WEATHER_API_KEY

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val okhttpClient = OkHttpClient.Builder()
    .addInterceptor(Interceptor { chain ->
        //Interceptor for modifying the request to add the WEATHER_API_KEY
        val original = chain.request().url

        val url = original.newBuilder()
            .addQueryParameter("appId", API_KEY)
            .build()

        val requestBuilder: Request.Builder = chain.request()
            .newBuilder()
            .url(url)

        val request = requestBuilder.build()
        chain.proceed(request)
    })
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okhttpClient)
    .build()

interface WeatherApiService{
    @GET("weather")
    suspend fun getCurrentWeather(@Query("q") city: String): CurrentWeatherDTO
}

object WeatherApi{
    fun create():WeatherApiService{
        return retrofit.create(WeatherApiService::class.java)
    }
}