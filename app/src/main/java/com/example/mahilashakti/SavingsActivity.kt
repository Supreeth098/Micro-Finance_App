package com.example.mahilashakti

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.mahilashakti.database.AppDatabase
import com.example.mahilashakti.database.TransactionEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SavingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F3FF)
                ) {
                    SavingsScreen()
                }
            }
        }
    }

    @Composable
    fun SavingsScreen() {

        val context = LocalContext.current

        var members by remember {
            mutableStateOf(listOf<String>())
        }

        var expanded by remember {
            mutableStateOf(false)
        }

        var selectedMember by remember {
            mutableStateOf("")
        }

        var amount by remember {
            mutableStateOf("")
        }

        LaunchedEffect(Unit) {

            val database =
                AppDatabase.getDatabase(context)

            val memberList =
                database.memberDao()
                    .getAllMembers()

            members =
                memberList.map {
                    it.memberName
                }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {

            Text(
                text = "Add Savings",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF5E35B1)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {
                    expanded = true
                },

                modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7E57C2)
                )
            ) {

                Text(
                    text =
                        if (selectedMember.isEmpty())
                            "Select Member"
                        else
                            selectedMember
                )
            }

            DropdownMenu(
                expanded = expanded,

                onDismissRequest = {
                    expanded = false
                }
            ) {

                members.forEach { member ->

                    DropdownMenuItem(

                        text = {
                            Text(member)
                        },

                        onClick = {
                            selectedMember = member
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = amount,

                onValueChange = {
                    amount = it
                },

                label = {
                    Text("Enter Savings Amount")
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {

                    if (
                        selectedMember.isBlank() ||
                        amount.toIntOrNull() == null
                    ) {

                        Toast.makeText(
                            context,
                            "Please fill valid details",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        val currentDate =
                            SimpleDateFormat(
                                "dd MMM yyyy hh:mm a",
                                Locale.getDefault()
                            ).format(Date())

                        val database =
                            AppDatabase.getDatabase(context)

                        lifecycleScope.launch {

                            val member =
                                database.memberDao()
                                    .getMemberByName(
                                        selectedMember
                                    )

                            if (member != null) {

                                database.transactionDao()
                                    .insertTransaction(

                                        TransactionEntity(

                                            memberId = member.id,

                                            type = "Savings",

                                            amount =
                                                amount.toIntOrNull() ?: 0,

                                            date = currentDate
                                        )
                                    )

                                Toast.makeText(
                                    context,
                                    "Savings Added Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                amount = ""
                            }
                        }
                    }
                },

                modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                )
            ) {

                Text(
                    text = "Add Savings"
                )
            }
        }
    }
}