package com.services

import com.dtos.response.approval.NotApprovedProfessionals
import com.repository.ApprovalRepository

interface ApprovalService {
    suspend fun getNotApprovedProfessional(): List<NotApprovedProfessionals>
    suspend fun approveProfessionals(id : Int)
    suspend fun rejectProfessional(id: Int)
}


class ApprovalServiceImpl(
    private val approvalRepository: ApprovalRepository
) : ApprovalService {
    override suspend fun getNotApprovedProfessional(): List<NotApprovedProfessionals> {
        return approvalRepository.getNotApprovedProfessional()
    }

    override suspend fun approveProfessionals(id: Int) {
        return approvalRepository.approveProfessional(id)
    }

    override suspend fun rejectProfessional(id: Int) {
        return approvalRepository.rejectProfessional(id)
    }
}