package com.configuration

import com.exceptions.AppException

inline fun <T> safeDbCall(block: () -> T): T {
    return try {
        block()
    } catch (e: AppException) {
        throw e
    } catch (e: Exception) {
        throw AppException.InternalServerError()
    }
}