package com.services

import com.dtos.requests.auth.LoginRequest
import com.dtos.requests.auth.ProfessionalRegisterRequest
import com.exceptions.AppException
import com.repository.ProfessionalRepository
import com.utils.JwtConfig
import com.utils.PasswordUtils

interface ProfessionalAuthService {
    suspend fun registerProfessional(request : ProfessionalRegisterRequest) : Boolean
    suspend fun authenticate(credentials : LoginRequest) : String
}


class ProfessionalAuthServiceImpl(
    private val professionalRepository: ProfessionalRepository,
    private val passwordUtils: PasswordUtils,
    private val jwtConfig: JwtConfig
) : ProfessionalAuthService {

    override suspend fun registerProfessional(request: ProfessionalRegisterRequest): Boolean {
        if (professionalRepository.findByEmail(request.email) != null ) {
            throw AppException.ConflictException("Email already registered ")
        }
        return professionalRepository.createProfessional(
            username = request.userName,
            email = request.email,
            passwordHash = passwordUtils.hash(password = request.password),
            experience = request.experience,
            documents = request.documents,
            serviceIds = request.serviceId,
        )
    }

    override suspend fun authenticate(credentials: LoginRequest): String {
        val professional = professionalRepository.findByEmail(credentials.email)
            ?: throw AppException.UnauthorizedException("Invalid Credentials")
        if(!passwordUtils.verify(password = credentials.password  , hash = professional.passwordHash)) {
            throw AppException.UnauthorizedException("Invalid Credentials")
        }
        return jwtConfig.generateToken(userId = professional.id.value , role = professional.type)
    }
}