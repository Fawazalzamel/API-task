package com.example.apitask.viewModel

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apitask.interfaceAPI.PetsApiService
import com.example.apitask.petsDatamodel.Pets
import com.example.apitask.repo.PetsRepo
import com.example.apitask.singlton.RetrofitHelper
import kotlinx.coroutines.launch
import java.lang.Exception

class PetViewModel: ViewModel() {

    private val petApiService = RetrofitHelper.getInstance().create(PetsApiService::class.java)
    private val repository = PetsRepo(petApiService)

    var pets by mutableStateOf(listOf<Pets>())

    init {
        fetchPets()
    }

    fun fetchPets(){
        viewModelScope.launch {
           try {
               pets=repository.getAllPets()
           } catch (e:Exception){ }

        }

    }

    fun addPets(pets: Pets){
        viewModelScope.launch {
            try {
                val response = petApiService.addPets(pets)
                if(response.isSuccessful && response.body() !=null ){
                    //handle success
            } else{
               //handle failure
            }

        }

            catch (e:Exception){
                //network errors
            }

        }
    }

    fun deletePet(petID:Int){
        viewModelScope.launch {
            try {
                val response = petApiService.deletePets(petID)
                if (response.isSuccessful) {
                } else {

                }
            }catch (e:Exception){

            }
        }
    }
}