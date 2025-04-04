package com.di

import com.repository.*
import com.services.*
import com.utils.JwtConfig
import com.utils.PasswordUtils
import org.koin.dsl.module

val appModule  = module {

    //Utils
    single { PasswordUtils() }
    single { JwtConfig() }

    //Repo
    single<CustomerRepository> { CustomerRepositoryImpl() }
    single<ServiceRepository> { ServiceRepositoryImpl() }
    single<SubServiceRepository> { SubServiceRepositoryImpl() }

    //Service
    single<CustomerAuthService> { CustomerAuthServiceImpl(get() , get() , get()) }
    single<ServiceService> { ServiceServiceImpl(get() , get())  }

}