package com.example.itface.argo.model.repository.auth.login

import com.example.itface.argo.entity.auth.SignInRequest
import com.example.itface.argo.model.data.auth.AuthHolder
import com.example.itface.argo.model.data.server.ArgoApi
import com.example.itface.argo.system.SchedulersProvider
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authData: AuthHolder,
    private val api: SomeApi,
    private val schedulers: SchedulersProvider
) {

    val isSignedIn get() = !authData.token.isNullOrEmpty()

    fun signIn(signInRequest: SignInRequest)
            = api
        .signIn(email = signInRequest.email,
                password = signInRequest.password)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())

    fun saveAuthData(token: String) {
        authData.token = token
    }

    fun clearAuthData() {
        authData.token = null
    }
}