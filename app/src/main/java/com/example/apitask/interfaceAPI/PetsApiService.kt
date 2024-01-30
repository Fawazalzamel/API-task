package com.example.apitask.interfaceAPI

import com.example.apitask.petsDatamodel.Pets
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PetsApiService {

    @GET("pets")
    suspend fun getAllPets():List<Pets>

    @POST("pets")
    suspend fun addPets(@Body pets: Pets): Response<Pets>

    @DELETE("pets/{petID}")
    suspend fun deletePets(@Path("petID")petID:Int):Response<Unit>
}