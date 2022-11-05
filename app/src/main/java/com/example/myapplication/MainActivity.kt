package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service: ApiService = getClient().create(ApiService::class.java)

        // memanggil api
        service.getUser().enqueue(createCallback(1))
        service.getUser().enqueue(createCallback(2))
        service.getUser().enqueue(createCallback(3))
        service.getUser().enqueue(createCallback(4))
        service.getUser().enqueue(createCallback(5))

    }

    private fun getClient(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://reqres.in")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun createCallback(callbackId: Int): Callback<RequestResponse> {
        return object : Callback<RequestResponse> {
            override fun onResponse(
                call: Call<RequestResponse>,
                response: Response<RequestResponse>,
            ) {
                println("callback id: $callbackId")
            }

            override fun onFailure(call: Call<RequestResponse>, t: Throwable) {
            }

        }
    }
}


// data class
data class RequestResponse(
    val data: User,
)

data class User(
    val id: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String,
)

// api service
interface ApiService {
    @GET("/api/users/2")
    fun getUser(): Call<RequestResponse>
}