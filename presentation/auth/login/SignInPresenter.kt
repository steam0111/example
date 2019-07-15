package com.example.itface.argo.presentation.auth.login

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.example.itface.argo.Constants
import com.example.itface.argo.R
import com.example.itface.argo.Screens
import com.example.itface.argo.entity.auth.SignInRequest
import com.example.itface.argo.model.data.server.ErrorHandler
import com.example.itface.argo.model.interactor.auth.login.AuthInteractor
import com.example.itface.argo.presentation.global.BasePresenter
import com.example.itface.argo.system.flow.FlowRouter
import com.example.itface.argo.system.message.SystemMessage
import com.example.itface.argo.system.message.SystemMessageNotifier
import javax.inject.Inject

@InjectViewState
class SignInPresenter @Inject constructor(
    private val interactor: AuthInteractor,
    private val systemMessageNotifier: SystemMessageNotifier,
    private val router: FlowRouter,
    private val errorHandler: ErrorHandler,
    private val context : Context
): BasePresenter<SignInView>() {

    private val signInRequest = SignInRequest()

    fun onEmailTextChange(email : String) {
        viewState.onShowInputEmailError(null)
        signInRequest.email = email
    }

    fun onPasswordTextChange(password : String) {
        viewState.onShowInputPasswordError(null)
        signInRequest.password = password
    }

    fun onBackPressed() {
        router.exit()
    }

    fun btnAuthClick(scopeName : String) {

        if (!validationSignInRequest(signInRequest)) {
            return
        }

        interactor
            .signIn(signInRequest)
            .doOnSubscribe { viewState.showProgress(true) }
            .doAfterTerminate { viewState.showProgress(false) }
            .subscribe({
                router.newRootScreen(Screens.MyModelsList(scopeName))
            }, { throwable ->
                errorHandler.proceed(throwable) { serverErrors ->
                    serverErrors.errors.firstOrNull()
                        ?.let {
                            systemMessageNotifier.send(SystemMessage(it.detail))
                        }

                }
            }).connect()
    }

    private fun validationSignInRequest(signInRequest : SignInRequest) : Boolean {
        var isDataValid = true

        if (signInRequest.email.isEmpty()) {
            viewState.onShowInputEmailError(
                context.getString(R.string.validation_empty)
            )

            isDataValid = false
        }

        if (signInRequest.password.length < Constants.ValidationConstant.MIN_PASSWORD_LENGTH) {
            viewState.onShowInputPasswordError(
                context.getString(
                    R.string.validation_min_length,
                    Constants.ValidationConstant.MIN_PASSWORD_LENGTH)
            )

            isDataValid = false
        }

        return isDataValid
    }
}