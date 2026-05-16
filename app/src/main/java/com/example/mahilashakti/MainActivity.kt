package com.example.mahilashakti

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .verticalScroll(
                rememberScrollState()
            )
            .fillMaxSize()
            .background(Color(0xFFF3EEF9))
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.height(120.dp)
        )




        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Micro-Finance App",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                context.startActivity(
                    Intent(context, AddMemberActivity::class.java)
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),

            shape = RoundedCornerShape(20.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF7E57C2)
            )

        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Member"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Add Member",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {
                context.startActivity(
                    Intent(context, SavingsActivity::class.java)
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),

            shape = RoundedCornerShape(20.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5E35B1)
            )

        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Savings"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Add Savings",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                context.startActivity(
                    Intent(context, LoanActivity::class.java)
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),

            shape = RoundedCornerShape(20.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF8E24AA)
            )

        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Loan"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Add Loan",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                context.startActivity(
                    Intent(context, ExpenseActivity::class.java)
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),

            shape = RoundedCornerShape(20.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE65100)
            )

        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Expense"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Add Expense",
                    fontSize = 18.sp
                )
            }
        }



        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                context.startActivity(
                    Intent(context, RecordsActivity::class.java)
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),

            shape = RoundedCornerShape(20.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4527A0)
            )

        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Records"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "View Records",
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(

            onClick = {

                val intent =
                    Intent(
                        context,
                        MembersActivity::class.java
                    )

                context.startActivity(intent)
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp),

            shape = RoundedCornerShape(20.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4527A0)
            )

        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Members"
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "Members",
                    fontSize = 18.sp
                )
            }


        }
    }
}