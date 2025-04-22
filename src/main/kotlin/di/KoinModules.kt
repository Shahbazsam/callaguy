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
    single<ProfessionalRepository>{ ProfessionalRepositoryImpl() }
    single<ServiceRepository> { ServiceRepositoryImpl() }
    single<SubServiceRepository> { SubServiceRepositoryImpl() }
    single<ServiceRequestRepository> { ServiceRequestRepositoryImpl() }
    single<SupportMessageRepository>{ SupportMessageRepositoryImpl() }
    single<SupportTicketRepository> { SupportTicketRepositoryImpl() }
    single<PaymentRepository> { PaymentRepositoryImpl() }
    single<ProfilePictureRepository> { ProfilePictureRepositoryImpl() }

    //Service
    single<CustomerAuthService> { CustomerAuthServiceImpl(get() , get() , get()) }
    single<ProfessionalAuthService> {ProfessionalAuthServiceImpl(get(), get() , get())  }
    single<ServiceService> { ServiceServiceImpl(get() , get())  }
    single<ServiceRequestService> { ServiceRequestServiceImpl(get()) }
    single<SupportMessageServices> { SupportMessageServicesImpl(get()) }
    single<SupportTicketService> { SupportTicketServiceImpl(get()) }
    single<PaymentService> {PaymentServiceImpl(get())  }
    single<ProfilePictureService> { ProfilePictureServiceImpl(get()) }
}