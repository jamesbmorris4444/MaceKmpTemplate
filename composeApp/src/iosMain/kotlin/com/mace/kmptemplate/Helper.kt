package com.mace.kmptemplate

import org.koin.core.context.startKoin

fun initKoin() {
    // start Koin
    val koinApp = startKoin {
        modules(appModule())
    }.koin

}