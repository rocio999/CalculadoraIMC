package com.example.calculadoraimc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.calculadoraimc.ui.theme.CalculadoraIMCTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CalculadoraIMCTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   AppNavegacion()

                }
            }
        }
    }
}




//Pantalla1----UI(Interfaz)
@Composable
fun PantallaIngreso(navController: NavController){
    var nombre by remember { mutableStateOf( value = "")  }  //Logica de estado y validacion
    var peso by remember { mutableStateOf("") }
    var altura by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text("Calculadora IMC ", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(29.dp))

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Ingresar aqui tu nombre") }

        )
        Spacer(modifier = Modifier.height(10.dp))

        TextField(

            value = peso,
            onValueChange = { peso = it },
            label = { Text("Ingresa aqui tu peso en kilogramos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = altura,
            onValueChange = { altura = it },
            label = { Text("Ingresa aqui tu altura en metros") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )
        Spacer(modifier = Modifier.height(24.dp))

        //Validaciones

        Button(onClick = {
            val p = peso.toDoubleOrNull() ?: 0.0
            val a = altura.toDoubleOrNull() ?: 1.0

            val imc = p / (a * a) //Formula

            navController.navigate("resultado/$nombre/$imc")
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Green)
        ) {
            Text("Calcular tu IMC")
        }
    }}

@Composable
fun AppNavegacion(){
    val navController = rememberNavController()

NavHost(
    navController = navController,
    startDestination = "ingreso"

){
    composable("ingreso"){
        PantallaIngreso(navController)

    }
    composable("resultado/{nombre}/{imc}"){backStackEntry ->
        val nombre =
            backStackEntry.arguments?.getString("nombre")?:""
        val imcString =
            backStackEntry.arguments?.getString("imc") ?:""


        PantallaResultado(
            navController,
            nombre = nombre,
            imc = imcString.toDouble()
            )}}}
@Composable
fun PantallaResultado(
    navController: NavController,
    nombre: String,
    imc: Double


){

            val veredicto = when {

                imc < 18.5 -> "Bajo peso"
                imc < 25.0 -> "Peso normal"
                imc < 30.0 -> "Sobrepeso"
                else -> "Obesidad"

        }
            val colorVeredicto = when {

                imc < 18.5 -> Color.Yellow
                imc < 25.0 -> Color.Blue
                imc < 30.0 -> Color(0xFFFFA500)
                else -> Color.Red

        }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text("Resultado del IMC",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold)


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Nombre: $nombre",
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Tu IMC es: $imc",
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    veredicto,
                    fontSize = 28.sp,
                    color = colorVeredicto,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(onClick = {

                        navController.popBackStack()

                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Text("Calcular otra vez")

                }
            }
        }


@Preview(showBackground = true)
@Composable
fun PreviewPantallaIngreso() {
    val navController  = rememberNavController()
    PantallaIngreso(navController)


}