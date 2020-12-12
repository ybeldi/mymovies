package com.cic.formation.mymovies.data.utils

import retrofit2.Response

/**
 * A generic extension function for handling Retrofit response
 * P : Retrofit response type
 * R : Return type
 */
fun <P, R> Response<P>.handleResponse(
    onSuccess: (data: P) -> Results<R>,
    onError: (errorMessage: String) -> Results.Error
): Results<R> {
    return if (isSuccessful) {
        if (body() != null) {
            onSuccess.invoke(body()!!)
        } else {
            onError.invoke("Body is empty")
        }
    } else {
        onError.invoke("Call not completed successfully")
    }
}