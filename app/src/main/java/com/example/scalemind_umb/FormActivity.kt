package com.example.scalemind_umb

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

class FormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FormScreen { name, email ->
                val intent = Intent(this, ConfirmationActivity::class.java).apply {
                    putExtra("NAME", name)
                    putExtra("EMAIL", email)
                }
                startActivity(intent)
            }
        }
    }
}

@Composable
fun FormScreen(onSubmit: (String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // Variables para saber si el usuario ya interactuó con el campo
    var nameTouched by remember { mutableStateOf(false) }
    var emailTouched by remember { mutableStateOf(false) }

    // Variables para detectar si el campo llegó a tener foco al menos una vez
    var nameWasFocused by remember { mutableStateOf(false) }
    var emailWasFocused by remember { mutableStateOf(false) }

    val isFormValid = validateForm(name, email)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título similar a tu pantalla de presentación
        Text(
            text = "Formulario de Registro",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Subtítulo descriptivo
        Text(
            text = "Completa el siguiente formulario para que nuestro equipo de ScalaMind podamos contactarte",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre completo") },
            isError = nameTouched && name.isBlank(),
            supportingText = {
                if (nameTouched && name.isBlank()) {
                    Text("El nombre es requerido", color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        nameWasFocused = true
                    } else if (!focusState.isFocused && nameWasFocused) {
                        nameTouched = true
                    }
                }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailTouched && !isValidEmail(email),
            supportingText = {
                if (emailTouched && !isValidEmail(email)) {
                    Text("Correo electrónico no válido", color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        emailWasFocused = true
                    } else if (!focusState.isFocused && emailWasFocused) {
                        emailTouched = true
                    }
                }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { if (isFormValid) onSubmit(name, email) },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}

// Función para validar el formulario
fun validateForm(name: String, email: String): Boolean {
    return name.isNotBlank() && isValidEmail(email)
}

// Función para validar el correo electrónico
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun PreviewFormScreen() {
    FormScreen { _, _ -> }
}
