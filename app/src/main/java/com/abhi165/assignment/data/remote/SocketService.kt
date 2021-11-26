package com.abhi165.assignment.data.remote

import com.abhi165.assignment.data.response.Transaction
import com.abhi165.assignment.domain.model.SubscribeUnconfirmedTransaction
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow


interface SocketService {
    @Send
    fun subscribe(action: SubscribeUnconfirmedTransaction)

    @Receive
    fun observeTransaction(): Flow<Transaction>

    @Receive
    fun observeWebSocketEvent(): Flow<WebSocket.Event>
}