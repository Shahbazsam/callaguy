package com.services

import com.dtos.requests.auth.LoginRequest
import com.dtos.requests.auth.RegisterRequest
import com.exceptions.AppException
import com.repository.CustomerRepository
import com.utils.JwtConfig
import com.utils.PasswordUtils

interface CustomerAuthService {
    suspend fun registerCustomer(request: RegisterRequest) : Boolean
    suspend fun authenticate(credentials : LoginRequest) : String
}

class  CustomerAuthServiceImpl(
    private val customerRepository: CustomerRepository,
    private val passwordUtil : PasswordUtils,
    private val jwtConfig: JwtConfig
) : CustomerAuthService {

    override suspend fun registerCustomer(request: RegisterRequest) : Boolean {
        if (customerRepository.findByEmail(request.email) != null) {
            throw AppException.ConflictException("Email already registered")
        }
        return customerRepository.createUser(
            userName = request.userName,
            email = request.email,
            password = passwordUtil.hash(password = request.password),
            phone = request.phone,
            address = request.address
        )
    }

    override suspend fun authenticate(credentials : LoginRequest) : String {
        val user = customerRepository.findByEmail(credentials.email)
            ?: throw AppException.UnauthorizedException("Invalid Credentials")
        if(!passwordUtil.verify(password = credentials.password , hash = user.passwordHash)) {
            throw AppException.UnauthorizedException("Invalid Credentials")
        }
        return jwtConfig.generateToken(user.id.value , user.type)
    }
}