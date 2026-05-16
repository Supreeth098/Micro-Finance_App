package com.example.mahilashakti

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mahilashakti.database.AppDatabase
import com.example.mahilashakti.database.MemberEntity
import kotlinx.coroutines.launch

class AddMemberActivity :
    ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContent {

            AddMemberScreen()
        }
    }
}

@Composable
fun AddMemberScreen() {

    var memberName by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val scope =
        rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Add Member",
            fontSize = 28.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(

            value = memberName,

            onValueChange = {
                memberName = it
            },

            label = {
                Text("Member Name")
            },

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(

            onClick = {

                scope.launch {

                    val db =
                        AppDatabase
                            .getDatabase(context)

                    val existingMember =
                        db.memberDao()
                            .getMemberByName(
                                memberName
                            )

                    if (
                        memberName.any {
                            it.isDigit()
                        }
                    ) {

                        Toast.makeText(
                            context,
                            "Numbers not allowed in member name",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@launch
                    }

                    if (existingMember != null) {

                        Toast.makeText(
                            context,
                            "Member already exists",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else {

                        db.memberDao()
                            .insertMember(

                                MemberEntity(
                                    memberName =
                                        memberName
                                )
                            )

                        Toast.makeText(
                            context,
                            "Member Added",
                            Toast.LENGTH_SHORT
                        ).show()

                        memberName = ""
                    }
                }
            }

        ) {

            Text("Save Member")
        }
    }
}