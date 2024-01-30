package com.example.apitask.repo

import com.example.apitask.interfaceAPI.PetsApiService

class PetsRepo (private val api:PetsApiService){
    suspend fun getAllPets() = api.getAllPets()
}