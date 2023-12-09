package com.mohammedie.bostaalbum.core

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flowOf

@ExperimentalCoroutinesApi
inline fun <T1, T2> Flow<T1>.retryIf(flow: Flow<T2>, crossinline predicate: (T1, T2) -> Boolean) =
    combine(flow) { t1, t2 ->
        if (predicate(t1, t2))
            this
        else flowOf(t1)
    }.flattenConcat()

