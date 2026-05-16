
package com.example.mahilashakti

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mahilashakti.database.AppDatabase
import com.example.mahilashakti.database.MemberEntity
import com.example.mahilashakti.database.TransactionEntity

class RecordsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F3FF)
                ) {
                    RecordsScreen()
                }
            }
        }
    }
}

@Composable
fun RecordsScreen() {

    val context = LocalContext.current

    var transactions by remember {
        mutableStateOf<List<TransactionEntity>>(emptyList())
    }

    var members by remember {
        mutableStateOf<List<MemberEntity>>(emptyList())
    }

    var searchText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        val database =
            AppDatabase.getDatabase(context)

        transactions =
            database.transactionDao()
                .getAllTransactions()

        members =
            database.memberDao()
                .getAllMembers()
    }

    val totalSavings =
        transactions.filter {
            it.type == "Savings"
        }.sumOf {
            it.amount
        }

    val totalLoans =
        transactions.filter {
            it.type == "Loan"
        }.sumOf {
            it.amount
        }

    val totalExpenses =
        transactions.filter {
            it.type == "Expense"
        }.sumOf {
            it.amount
        }

    val totalBalance =
        totalSavings - totalLoans - totalExpenses

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Text(
            text = "Finance Dashboard",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5E35B1)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Overall Summary",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5E35B1)
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Total Savings: ₹$totalSavings",
                    fontSize = 20.sp,
                    color = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Total Loans: ₹$totalLoans",
                    fontSize = 20.sp,
                    color = Color(0xFFD32F2F)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Total Expenses: ₹$totalExpenses",
                    fontSize = 20.sp,
                    color = Color(0xFFE65100)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Net Balance: ₹$totalBalance",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1565C0)
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        OutlinedTextField(

            value = searchText,

            onValueChange = {
                searchText = it
            },

            label = {
                Text("Search Members")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Member Summaries",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5E35B1)
        )

        Spacer(modifier = Modifier.height(15.dp))

        members.filter {

            it.memberName.contains(
                searchText,
                ignoreCase = true
            )

        }.forEach { member ->

            val memberTransactions =
                transactions.filter {
                    it.memberId == member.id
                }

            val memberSavings =
                memberTransactions.filter {
                    it.type == "Savings"
                }.sumOf {
                    it.amount
                }

            val memberLoans =
                memberTransactions.filter {
                    it.type == "Loan"
                }.sumOf {
                    it.amount
                }

            val memberExpenses =
                memberTransactions.filter {
                    it.type == "Expense"
                }.sumOf {
                    it.amount
                }

            val memberBalance =
                memberSavings - memberLoans - memberExpenses

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
                    .clickable {

                        val intent =
                            Intent(
                                context,
                                MemberDetailsActivity::class.java
                            )

                        intent.putExtra(
                            "memberId",
                            member.id
                        )

                        intent.putExtra(
                            "memberName",
                            member.memberName
                        )

                        context.startActivity(intent)
                    }
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = member.memberName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5E35B1)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Savings: ₹$memberSavings",
                        fontSize = 18.sp,
                        color = Color(0xFF2E7D32)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Loans: ₹$memberLoans",
                        fontSize = 18.sp,
                        color = Color(0xFFD32F2F)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Expenses: ₹$memberExpenses",
                        fontSize = 18.sp,
                        color = Color(0xFFE65100)
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Balance: ₹$memberBalance",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Tap to View Full Details",
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Recent Transactions",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5E35B1)
        )

        Spacer(modifier = Modifier.height(15.dp))

        transactions.reversed().take(10).forEach { transaction ->

            val memberName =
                members.find {
                    it.id == transaction.memberId
                }?.memberName ?: "Unknown"

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {

                Column(
                    modifier = Modifier.padding(15.dp)
                ) {

                    Text(
                        text = memberName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = transaction.type,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "₹ ${transaction.amount}",
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = transaction.date
                    )
                }
            }
        }
    }
}
