
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.mahilashakti.database.AppDatabase
import com.example.mahilashakti.database.TransactionEntity
import kotlinx.coroutines.launch

class MemberDetailsActivity :
    ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        val memberId =
            intent.getIntExtra(
                "memberId",
                0
            )

        val memberName =
            intent.getStringExtra(
                "memberName"
            ) ?: ""

        setContent {

            MaterialTheme {

                Surface(
                    color = Color(0xFFF5F3FF)
                ) {

                    MemberDetailsScreen(
                        memberId,
                        memberName
                    )
                }
            }
        }
    }
}

@Composable
fun MemberDetailsScreen(
    memberId: Int,
    memberName: String
) {

    val context =
        LocalContext.current

    val activity =
        context as MemberDetailsActivity

    var transactions by remember {

        mutableStateOf<
                List<TransactionEntity>
                >(emptyList())
    }

    var showEditDialog by remember {
        mutableStateOf(false)
    }

    var editAmount by remember {
        mutableStateOf("")
    }

    var editType by remember {
        mutableStateOf("")
    }

    var selectedTransaction by remember {
        mutableStateOf<TransactionEntity?>(null)
    }

    var searchText by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {

        val database =
            AppDatabase.getDatabase(context)

        transactions =
            database.transactionDao()
                .getTransactionsByMember(
                    memberId
                )
    }

    val savings =
        transactions.filter {
            it.type == "Savings"
        }.sumOf {
            it.amount
        }

    val loans =
        transactions.filter {
            it.type == "Loan"
        }.sumOf {
            it.amount
        }

    val expenses =
        transactions.filter {
            it.type == "Expense"
        }.sumOf {
            it.amount
        }

    val balance =
        savings - loans - expenses

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = memberName,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5E35B1)
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier =
                    Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Savings: ₹$savings",
                    fontSize = 20.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )

                Text(
                    text = "Loans: ₹$loans",
                    fontSize = 20.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )

                Text(
                    text = "Expenses: ₹$expenses",
                    fontSize = 20.sp
                )

                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )

                Text(
                    text = "Balance: ₹$balance",
                    fontSize = 24.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(

            value = searchText,

            onValueChange = {
                searchText = it
            },

            label = {
                Text("Search Transactions")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        if (showEditDialog &&
            selectedTransaction != null
        ) {

            AlertDialog(

                onDismissRequest = {
                    showEditDialog = false
                },

                title = {
                    Text("Edit Transaction")
                },

                text = {

                    Column {

                        OutlinedTextField(

                            value = editAmount,

                            onValueChange = {
                                editAmount = it
                            },

                            label = {
                                Text("Amount")
                            },

                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )

                        Spacer(
                            modifier =
                                Modifier.height(10.dp)
                        )

                        Button(

                            onClick = {
                                expanded = true
                            },

                            modifier = Modifier.fillMaxWidth()

                        ) {

                            Text(editType)
                        }

                        DropdownMenu(

                            expanded = expanded,

                            onDismissRequest = {
                                expanded = false
                            }

                        ) {

                            DropdownMenuItem(

                                text = {
                                    Text("Savings")
                                },

                                onClick = {

                                    editType = "Savings"
                                    expanded = false
                                }
                            )

                            DropdownMenuItem(

                                text = {
                                    Text("Loan")
                                },

                                onClick = {

                                    editType = "Loan"
                                    expanded = false
                                }
                            )

                            DropdownMenuItem(

                                text = {
                                    Text("Expense")
                                },

                                onClick = {

                                    editType = "Expense"
                                    expanded = false
                                }
                            )
                        }
                    }
                },

                confirmButton = {

                    Button(

                        onClick = {

                            activity.lifecycleScope.launch {

                                val database =
                                    AppDatabase.getDatabase(
                                        context
                                    )

                                val updatedTransaction =

                                    selectedTransaction!!.copy(

                                        amount =
                                            editAmount.toIntOrNull()
                                                ?: 0,

                                        type = editType
                                    )

                                database.transactionDao()
                                    .updateTransaction(
                                        updatedTransaction
                                    )

                                transactions =
                                    database.transactionDao()
                                        .getTransactionsByMember(
                                            memberId
                                        )

                                showEditDialog = false
                            }
                        }

                    ) {

                        Text("Save")
                    }
                },

                dismissButton = {

                    Button(

                        onClick = {
                            showEditDialog = false
                        }

                    ) {

                        Text("Cancel")
                    }
                }
            )
        }

        LazyColumn {

            items(

                transactions.filter {

                    it.type.contains(
                        searchText,
                        ignoreCase = true
                    )

                            ||

                            it.amount.toString().contains(
                                searchText
                            )

                            ||

                            it.date.contains(
                                searchText,
                                ignoreCase = true
                            )
                }

            ) { transaction ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {

                    Column(
                        modifier =
                            Modifier.padding(15.dp)
                    ) {

                        Text(
                            text =
                                transaction.type,

                            fontWeight =
                                FontWeight.Bold,

                            fontSize = 20.sp
                        )

                        Spacer(
                            modifier =
                                Modifier.height(5.dp)
                        )

                        Text(
                            text =
                                "₹ ${transaction.amount}"
                        )

                        Spacer(
                            modifier =
                                Modifier.height(5.dp)
                        )

                        Text(
                            text =
                                transaction.date
                        )

                        Spacer(
                            modifier =
                                Modifier.height(10.dp)
                        )

                        Button(

                            onClick = {

                                activity.lifecycleScope.launch {

                                    val database =
                                        AppDatabase.getDatabase(
                                            context
                                        )

                                    database.transactionDao()
                                        .deleteTransaction(
                                            transaction
                                        )

                                    transactions =
                                        database.transactionDao()
                                            .getTransactionsByMember(
                                                memberId
                                            )
                                }
                            },

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )

                        ) {

                            Text(
                                text = "Delete"
                            )
                        }

                        Spacer(
                            modifier =
                                Modifier.height(10.dp)
                        )

                        Button(

                            onClick = {

                                selectedTransaction =
                                    transaction

                                editAmount =
                                    transaction.amount.toString()

                                editType =
                                    transaction.type

                                showEditDialog = true
                            },

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1565C0)
                            )

                        ) {

                            Text(
                                text = "Edit"
                            )
                        }

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Button(

                                onClick = {

                                    activity.lifecycleScope.launch {

                                        val database =
                                            AppDatabase.getDatabase(
                                                context
                                            )

                                        database.transactionDao()
                                            .deleteTransactionsByMember(
                                                memberId
                                            )

                                        database.transactionDao()
                                            .deleteTransactionsByMember(
                                                memberId
                                            )

                                        val member =

                                            database.memberDao()
                                                .getMemberByName(
                                                    memberName
                                                )

                                        if (member != null) {

                                            database.memberDao()
                                                .deleteMember(member)
                                        }

                                        activity.finish()
                                    }
                                },

                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black
                                )

                            ) {

                                Text(
                                    text = "Delete Member"
                                )
                            }

                    }
                }
            }
        }
    }
}