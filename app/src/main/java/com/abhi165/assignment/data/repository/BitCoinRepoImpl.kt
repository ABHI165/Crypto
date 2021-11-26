package com.abhi165.assignment.data.repository

import com.abhi165.assignment.data.remote.SocketService
import com.abhi165.assignment.data.response.Transaction
import com.abhi165.assignment.domain.model.SubscribeUnconfirmedTransaction
import com.abhi165.assignment.domain.repository.BitCoinRepo
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BitCoinRepoImpl @Inject constructor(private val remote:SocketService):BitCoinRepo {

    override fun subscribe(action: SubscribeUnconfirmedTransaction) {
       remote.subscribe(action)
    }

    override fun observeTransaction(): Flow<Transaction> =
      remote.observeTransaction()


    override fun observeWebSocketEvent(): Flow<WebSocket.Event> = remote.observeWebSocketEvent()
}