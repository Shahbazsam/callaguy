package com.di

import com.repository.UserRepository
import com.repository.UserRepositoryImpl
import com.services.AuthService
import com.services.AuthServiceImpl
import com.utils.JwtConfig
import com.utils.PasswordUtils
import org.koin.dsl.module

val appModule  = module {

    //Utils
    single { PasswordUtils() }
    single { JwtConfig() }

    //Repo
    single<UserRepository> { UserRepositoryImpl() }

    //Service
    single<AuthService> { AuthServiceImpl(get() , get() , get()) }


}