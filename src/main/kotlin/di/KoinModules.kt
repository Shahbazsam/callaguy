package com.di

import com.repository.*
import com.services.AuthService
import com.services.AuthServiceImpl
import com.services.ServiceService
import com.services.ServiceServiceImpl
import com.utils.JwtConfig
import com.utils.PasswordUtils
import org.koin.dsl.module

val appModule  = module {

    //Utils
    single { PasswordUtils() }
    single { JwtConfig() }

    //Repo
    single<UserRepository> { UserRepositoryImpl() }
    single<ServiceRepository> { ServiceRepositoryImpl() }
    single<SubServiceRepository> { SubServiceRepositoryImpl() }

    //Service
    single<AuthService> { AuthServiceImpl(get() , get() , get()) }
    single<ServiceService> { ServiceServiceImpl(get() , get())  }

}