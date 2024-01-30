package com.example.apitask

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.apitask.petsDatamodel.Pets
import com.example.apitask.ui.theme.APITaskTheme
import com.example.apitask.viewModel.PetViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APITaskTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun PetsListScreen(viewModel: PetViewModel){
    val pets=viewModel.pets
    LazyColumn{
        items(pets){
            pet->
            PetItem(pet)
        }
    }
}



@Composable
fun PetItem(pet: Pets, petViewModel: PetViewModel = viewModel()) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
        ,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),

        ) {
        Row (
            Modifier
                .padding(10.dp)
                .height(150.dp),
            verticalAlignment = Alignment.CenterVertically)
        {
            AsyncImage(model = pet.image,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )
            Column {
                Text(text = pet.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
                Text(text = pet.gender,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis)
                Text(text = "${pet.age}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(top = 10.dp))



                }
            }
            Button(onClick = { petViewModel.deletePet(pet.id) }) {
                Text("Delete")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(title = { Text("My Pet List") }) }
        ,
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addPet")}) {
                Text("+")
            }
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = "petList") {
            composable("petList") {
                // Ensure BookListScreen is correctly implemented and contains visible content
                PetListScreen(modifier = Modifier.padding(padding))
            }
            composable("addPet") {
                AddPetScreen()

            }
        }
    }
}



@Composable
fun PetListScreen(modifier: Modifier = Modifier,
                  viewModel: PetViewModel = viewModel()) {
    val pets = viewModel.pets

    LazyColumn(modifier = modifier) {
        items(pets) { pet ->
            PetItem(pet)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPetScreen(modifier: Modifier = Modifier,
                 viewModel: PetViewModel = viewModel()) {

    // State variables for input fields
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add a New Pet",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            InputField(
                value = name,
                onValueChange = { name = it },
                label = "Name"
            )
            InputField(
                value = gender,
                onValueChange = { gender = it },
                label = "Gender"
            )
            InputField(
                value = age,
                onValueChange = { age = it },
                label = "Age",
                keyboardType = KeyboardType.Number
            )


            InputField(
                value = image,
                onValueChange = { image = it },
                label = "Image URL"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newPets = Pets(
                        id = 0,  // Assuming ID is generated by the server
                        name = name,
                        gender = gender,
                        age = age.toInt(),
                        image = image
                    )
                    viewModel.addPets(newPets)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Pet")
            }
        }
    }
}

@Composable
fun InputField(value: String, onValueChange: (String) -> Unit, label: String, keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}