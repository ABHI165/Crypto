package com.abhi165.assignment.presentation.component

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhi165.assignment.domain.model.SubscribeUnconfirmedTransaction
import com.abhi165.assignment.presentation.viewmodel.MainViewModel
import com.abhi165.assignment.ui.theme.BGMaterial
import com.abhi165.assignment.ui.theme.assignmentTheme
import com.abhi165.assignment.utill.Constant
import com.abhi165.assignment.utill.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val mainViewModel:MainViewModel by viewModels()

    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.init()
        setContent {
            Scaffold(scaffoldState = rememberScaffoldState(),backgroundColor = BGMaterial){

                Column {

                    val isConnected:String =   mainViewModel.connectionStatusLiveData.value
                    val data = mainViewModel.bitCoinLLiveData
                    val olddata = mainViewModel.bitCoinLOldLiveData

                    val expendedids = mainViewModel.expandedPostIds.value
                    val oldExpendedids = mainViewModel.expandedPostOldIds.value
                    when
                    {
                       isConnected ==""-> {
                           Text(
                               text = "Connecting..",
                               textAlign = TextAlign.Center,
                               style = MaterialTheme.typography.h6,
                               color = Color.Yellow,
                               modifier = Modifier.fillMaxWidth(),
                           )
                       }
                       isConnected ==Constant.CONNECTED -> {
                           Text(
                               text = "Connected",
                               textAlign = TextAlign.Center,
                               style = MaterialTheme.typography.h6,
                               color = Color.Green,
                               modifier = Modifier.fillMaxWidth()
                           )
                       }
                       isConnected ==Constant.CLOSED -> {
                           Text(
                               text = "Closed",
                               textAlign = TextAlign.Center,
                               style = MaterialTheme.typography.h6,
                                       color = Color.Red,
                               modifier = Modifier.fillMaxWidth()
                           )
                       }

                        isConnected ==Constant.FAILED -> {
                            Text(
                                text = "Disconnected",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h6,
                                color = Color.Red,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                   LazyColumn{
                       stickyHeader {
                           Divider(modifier = Modifier.fillMaxWidth().padding(6.dp),color = Color.Black,thickness = 2.dp)
                           Text("Current Transactions",modifier = Modifier.wrapContentSize().padding(10.dp),textAlign = TextAlign.Center,style = MaterialTheme.typography.h6)
                       }
                       itemsIndexed(data) {id,post->
                           PostItem(postdata = post, onPostClicked = {mainViewModel.onPostClicked(id)}, isExpanded =expendedids.contains(id) )

                       }

                       stickyHeader {
                           Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceAround,verticalAlignment = Alignment.CenterVertically) {
                               Text("Past Transactions",modifier = Modifier.wrapContentSize(),textAlign = TextAlign.Center,style = MaterialTheme.typography.h6)
                               IconButton(onClick =  {mainViewModel.clearOldTransaction()},) {
                                   Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                               }
                           }

                       }
                       itemsIndexed(olddata){
                           id,post->
                               PostItem(postdata = post, onPostClicked = {mainViewModel.onPostOldClicked(id)}, isExpanded =oldExpendedids.contains(id) )


                       }
                   }



                }


            }
        }
    }
}

