package com.abhi165.assignment.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi165.assignment.data.response.Transaction
import com.abhi165.assignment.domain.model.SubscribeUnconfirmedTransaction
import com.abhi165.assignment.domain.usecase.ConnectionStatusUseCase
import com.abhi165.assignment.domain.usecase.GetTransactionUseCase
import com.abhi165.assignment.domain.usecase.SubscribeToTransactionUseCase
import com.abhi165.assignment.utill.Constant
import com.abhi165.assignment.utill.Resource
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val connectionStatusUseCase: ConnectionStatusUseCase,private val getTransactionUseCase: GetTransactionUseCase,private val subscribeToTransactionUseCase: SubscribeToTransactionUseCase):ViewModel() {

    private  val _connectionStatusLiveData = mutableStateOf("")
    val  connectionStatusLiveData = _connectionStatusLiveData


    private  var _bitCoinLLiveData = mutableStateListOf<Transaction>()
    val  bitCoinLLiveData = _bitCoinLLiveData


    private  var _bitCoinLOldLiveData = mutableStateListOf<Transaction>()
    val  bitCoinLOldLiveData = _bitCoinLOldLiveData


    private val _expandedPostIds = mutableStateOf(listOf<Int>())
    val expandedPostIds: State<List<Int>> get() = _expandedPostIds

    private val _expandedPostOldIds = mutableStateOf(listOf<Int>())
    val expandedPostOldIds: State<List<Int>> get() = _expandedPostOldIds

    fun  init() {
        connectionStatusUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { event ->
                when(event){
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        _connectionStatusLiveData.value = Constant.CONNECTED
                        subscribeToTransactionUseCase(SubscribeUnconfirmedTransaction())
                    }
                    is WebSocket.Event.OnConnectionClosed -> {
                        _connectionStatusLiveData.value = Constant.CLOSED
                    }
                    is WebSocket.Event.OnConnectionFailed -> {
                        _connectionStatusLiveData.value = Constant.FAILED
                    }
                }

            }.launchIn(viewModelScope)


        getTransactionUseCase()
            .flowOn(Dispatchers.IO)
            .onEach {
                if(_bitCoinLLiveData.size <5) _bitCoinLLiveData.add(it)
                else {
                    val lastData = _bitCoinLLiveData[4]
                   val new = mutableListOf<Transaction>(it).also { it.addAll(_bitCoinLLiveData.dropLast(4)) }
                    _bitCoinLLiveData.apply {
                        clear()
                        addAll(new)
                    }
                    if(_bitCoinLOldLiveData.size>=5) _bitCoinLOldLiveData.drop(4)
                    else _bitCoinLOldLiveData.add(lastData)
                }


            }.launchIn(viewModelScope)

    }





    fun clearOldTransaction(){
        _bitCoinLOldLiveData.clear()
    }

    fun onPostClicked(id:Int){
        _expandedPostIds.value = _expandedPostIds.value.toMutableList().also {
            if(it.contains(id)) it.remove(id) else it.add(id)
        }
    }

    fun onPostOldClicked(id:Int){
        _expandedPostOldIds.value = _expandedPostOldIds.value.toMutableList().also {
            if(it.contains(id)) it.remove(id) else it.add(id)
        }
    }










}


