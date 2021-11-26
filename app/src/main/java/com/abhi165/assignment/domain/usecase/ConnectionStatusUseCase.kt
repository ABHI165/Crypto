package com.abhi165.assignment.domain.usecase

import com.abhi165.assignment.data.repository.BitCoinRepoImpl
import javax.inject.Inject

class ConnectionStatusUseCase @Inject constructor(private val repo: BitCoinRepoImpl) {

    operator fun invoke() = repo.observeWebSocketEvent()





}
