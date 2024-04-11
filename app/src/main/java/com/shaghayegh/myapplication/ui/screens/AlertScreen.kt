package com.shaghayegh.myapplication.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shaghayegh.myapplication.ui.viewmodel.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertScreen(mainViewModel: MainViewModel) {
    // Collect the alert state flow
    val alerts = mainViewModel.alertFeedStateFlow.collectAsState()
    val alertList = alerts.value?.entityList

    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Column(
//                        modifier = Modifier.fillMaxWidth(),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = "Alerts",
//                            fontSize = 30.sp,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                }
//            )
//        }
    ){ innerPadding ->
        // Display the list of alerts
        LazyColumn {
            items(alertList ?: emptyList()) { alert ->
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Bus: ${alert.alert.informedEntityList[0].routeId}",
                    )
                    Text(
                        "Cause: ${alert.alert.cause}",
                    )
                    Text(
                        "Effect: ${alert.alert.effect}",
                    )
                    Text(
                        "Description: ${alert.alert.headerText.translationList.firstOrNull()?.text}",
                    )
                }
            }
        }
    }
}