package com.mace.kmptemplate.di

import com.mace.mace_template.di.applicationModule
import org.koin.core.context.startKoin

fun initKoin() {
    // start Koin
    val koinApp = startKoin {
        modules(applicationModule)
    }.koin

}