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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mahilashakti.database.AppDatabase
import com.example.mahilashakti.database.MemberEntity

class MembersActivity :
    ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContent {

            MaterialTheme {

                Surface {

                    MembersScreen()
                }
            }
        }
    }
}


@Composable
fun MembersScreen() {


    var members by remember {

        mutableStateOf<
                List<MemberEntity>
                >(emptyList())
    }

    val context =
        LocalContext.current

    LaunchedEffect(Unit) {

        val db =
            AppDatabase.getDatabase(context)

        members =
            db.memberDao()
                .getAllMembers()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Members",
            fontSize = 30.sp
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        LazyColumn {

            items(members) { member ->

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
                        modifier =
                            Modifier.padding(20.dp)
                    ) {

                        Text(
                            text =
                                member.memberName,

                            fontSize = 22.sp
                        )
                    }
                }
            }
        }
    }
}