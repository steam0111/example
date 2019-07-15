package com.example.itface.argo.model.interactor.auth.login

import com.example.itface.argo.entity.auth.SignInRequest
import com.example.itface.argo.model.repository.auth.login.AuthRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private var authRepository : AuthRepository
) {

    fun isSignedIn() = authRepository.isSignedIn

    fun signIn(signInRequest: SignInRequest) =
        Completable.defer {
            authRepository
                .signIn(signInRequest)
                .doOnSuccess {
                    authRepository.saveAuthData(it.token)
                }
                .doOnError {
                    Single.error<Throwable>(it)
                }
                .ignoreElement()
        }
}