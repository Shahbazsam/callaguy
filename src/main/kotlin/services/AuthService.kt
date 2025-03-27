package com.services

import com.dtos.requests.auth.LoginRequest
import com.dtos.requests.auth.RegisterRequest
import com.exceptions.AppException
import com.repository.UserRepository
import com.utils.JwtConfig
import com.utils.PasswordUtils

interface AuthService {
    suspend fun registerUser(request: RegisterRequest) : Boolean
    suspend fun authenticate(credentials : LoginRequest) : String
}

class  AuthServiceImpl(
    private val userRepository: UserRepository
) : AuthService {
    private val passwordUtil = PasswordUtils
    private val jwtConfig = JwtConfig
    override suspend fun registerUser(request: RegisterRequest) : Boolean {
        if (userRepository.findByEmail(request.email) != null) {
            throw AppException.ConflictException("Email already registered")
        }
        return userRepository.createUser(
            userName = request.userName,
            email = request.email,
            password = passwordUtil.hash(password = request.password),
            type = request.userType,
            phone = request.phone,
            address = request.address
        )
    }

    override suspend fun authenticate(credentials : LoginRequest) : String {
        val user = userRepository.findByEmail(credentials.email)
            ?: throw AppException.UnauthorizedException("Invalid Credentials")
        if(!passwordUtil.verify(password = credentials.password , hash = user.passwordHash)) {
            throw AppException.UnauthorizedException("Invalid Credentials")
        }
        return jwtConfig.generateToken(user.id.value , user.type)
    }
}