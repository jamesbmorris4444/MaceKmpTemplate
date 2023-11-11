package com.mace.mace_template.di

import Repository
import RepositoryImpl
import org.koin.dsl.module

val applicationModule = module {
    single<Repository> { RepositoryImpl(get(), get()) }
}