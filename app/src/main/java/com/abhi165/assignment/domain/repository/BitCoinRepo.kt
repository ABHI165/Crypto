package com.abhi165.assignment.domain.repository

import com.abhi165.assignment.data.response.Transaction
import com.abhi165.assignment.domain.model.SubscribeUnconfirmedTransaction
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

interface  BitCoinRepo {


     fun subscribe(action: SubscribeUnconfirmedTransaction)


    fun observeTransaction(): Flow<Transaction>


    fun observeWebSocketEvent(): Flow<WebSocket.Event>
}