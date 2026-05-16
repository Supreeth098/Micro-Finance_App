package com.example.mahilashakti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class AIAdviceActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                AIAdviceScreen()
            }
        }
    }
}

@Composable
fun AIAdviceScreen() {

    var question by remember {
        mutableStateOf("")
    }

    var response by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3EEF9))
            .padding(20.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "AI Financial Advice",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6A1B9A)
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = question,

            onValueChange = {
                question = it
            },

            label = {
                Text("Ask your question")
            },

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(20.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(

            onClick = {

                scope.launch {

                    try {

                        response = "Loading..."

                        val apiKey = "AIzaSyCiCfTGUDkqF39z4cA2wh5oDnZw3O2ddus"

                        val generativeModel = GenerativeModel(
                            modelName = "gemini-2.0-flash",
                            apiKey = apiKey
                        )

                        val result =
                            generativeModel.generateContent(question)

                        response =
                            result.text ?: "No response"

                    } catch (e: Exception) {

                        response = "Error: ${e.message}"
                    }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),

            shape = RoundedCornerShape(22.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7E57C2)
            )

        ) {

            Text(
                text = "Get Advice",
                fontSize = 26.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFE7E1EC),
                    RoundedCornerShape(20.dp)
                )
                .padding(20.dp)
        ) {

            Text(
                text = response,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
    }
}