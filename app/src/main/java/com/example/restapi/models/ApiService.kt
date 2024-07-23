package com.example.restapi.models

import com.example.restapi.utils.MyNotes
import com.example.restapi.utils.PostRequestTodo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("plan")
    fun getNotes(): Call<List<MyNotes>>

    @POST("plan/")
    fun addTodo(@Body postRequestTodo: PostRequestTodo) : Call<MyNotes>

    @DELETE("plan/{id}/")
    fun deleteTodo(@Path("id") id:Int) : Call<Any>

    @PUT("plan/{id}/")
    fun update(@Path("id") id: Int, @Body postRequestTodo: PostRequestTodo ): Call<MyNotes>


}