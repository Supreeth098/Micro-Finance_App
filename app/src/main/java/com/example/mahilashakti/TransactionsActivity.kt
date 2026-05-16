package com.example.mahilashakti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mahilashakti.database.AppDatabase
import com.example.mahilashakti.database.TransactionEntity

class TransactionsActivity :
    ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContent {

            MaterialTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F3FF)
                ) {

                    TransactionsScreen()
                }
            }
        }
    }
}

@Composable
fun TransactionsScreen() {

    var transactions by remember {

        mutableStateOf<
                List<TransactionEntity>
                >(emptyList())
    }

    val context =
        androidx.compose.ui.platform
            .LocalContext.current

    LaunchedEffect(Unit) {

        val database =
            AppDatabase.getDatabase(context)

        transactions =
            database.transactionDao()
                .getAllTransactions()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "All Transactions",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5E35B1)
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        LazyColumn {

            itemsIndexed(
                transactions
            ) { index, transaction ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                    ) {

                        Text(
                            text =
                                "${index + 1}. ${transaction.memberId}",

                            fontSize = 22.sp,

                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )

                        Text(
                            text =
                                "Type: ${transaction.type}",

                            fontSize = 18.sp
                        )

                        Spacer(
                            modifier =
                                Modifier.height(5.dp)
                        )

                        Text(
                            text =
                                "Amount: ₹ ${transaction.amount}",

                            fontSize = 18.sp
                        )

                        Spacer(
                            modifier =
                                Modifier.height(5.dp)
                        )

                        Text(
                            text =
                                "Date: ${transaction.date}",

                            fontSize = 16.sp,

                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}