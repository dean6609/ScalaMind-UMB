package com.example.scalemind_umb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

class ConfirmationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("NAME") ?: "No especificado"
        val email = intent.getStringExtra("EMAIL") ?: "No especificado"

        setContent {
            ConfirmationScreen(name, email) {
                finishAffinity() // Cierra la actividad al presionar "Finalizar"
            }
        }
    }
}

@Composable
fun ConfirmationScreen(name: String, email: String, onFinish: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título principal
        Text(
            text = "¡Inscripción Exitosa!",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Subtítulo o mensaje de agradecimiento
        Text(
            text = "Gracias por registrarte en ScalaMind. Nos pondremos en contacto contigo muy pronto.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Tarjeta con los datos del usuario
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Nombre:", style = MaterialTheme.typography.bodyMedium)
                Text(text = name, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Correo:", style = MaterialTheme.typography.bodyMedium)
                Text(text = email, style = MaterialTheme.typography.bodyLarge)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Botón "Finalizar"
        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Finalizar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConfirmationScreen() {
    ConfirmationScreen("Michael Pinzon", "michael@pinzon.com") {}
}
