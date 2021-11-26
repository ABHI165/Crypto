package com.abhi165.assignment.domain.usecase

import com.abhi165.assignment.data.repository.BitCoinRepoImpl
import com.abhi165.assignment.domain.model.SubscribeUnconfirmedTransaction
import javax.inject.Inject

class SubscribeToTransactionUseCase @Inject constructor(private val repo: BitCoinRepoImpl) {

    operator fun invoke(action: SubscribeUnconfirmedTransaction) =
        repo.subscribe(action)


}
