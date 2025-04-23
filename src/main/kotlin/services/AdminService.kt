package com.services

import com.dtos.requests.auth.AdminRegisterRequestDto
import com.dtos.requests.auth.LoginRequest
import com.exceptions.AppException
import com.repository.AdminRepository
import com.utils.JwtConfig
import com.utils.PasswordUtils

interface AdminService {
    suspend fun registerAdmin(request : AdminRegisterRequestDto) : Boolean
    suspend fun Authenticate(credentials : LoginRequest) : String
}

class AdminServiceImpl(
    private val repository : AdminRepository,
    private val passwordUtil : PasswordUtils,
    private val jwtConfig: JwtConfig
) : AdminService {
    override suspend fun registerAdmin(request: AdminRegisterRequestDto): Boolean {
        if (repository.findByEmail(request.email) != null) throw AppException.ConflictException("email already exist")

        return repository.createAdmin(
            userName = request.userName,
            email = request.email,
            password = passwordUtil.hash(password = request.password)
        )
    }

    override suspend fun Authenticate(credentials: LoginRequest): String {
        val admin = repository.findByEmail(credentials.email)
            ?: throw AppException.UnauthorizedException("Invalid Credentials")

        if (!passwordUtil.verify(password = credentials.password , hash = admin.passwordHash)) {
            throw AppException.UnauthorizedException("Invalid Credentials")
        }
         return jwtConfig.generateToken(userId = admin.id.value , role = admin.type)
    }
}