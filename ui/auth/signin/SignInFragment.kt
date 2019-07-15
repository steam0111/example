package com.example.itface.argo.ui.auth.signin

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.itface.argo.R
import com.example.itface.argo.exetension.argument
import com.example.itface.argo.presentation.auth.login.SignInPresenter
import com.example.itface.argo.presentation.auth.login.SignInView
import com.example.itface.argo.toothpick.DI
import com.example.itface.argo.ui.global.BaseFragment
import com.example.itface.argo.utils.onTextChange
import kotlinx.android.synthetic.main.fragment_sign_in.*
import toothpick.Toothpick

class SignInFragment : BaseFragment(), SignInView {

    override val layoutRes: Int = R.layout.fragment_sign_in

    @InjectPresenter
    lateinit var presenter : SignInPresenter

    private val scopeName: String? by argument(ARG_SCOPE_NAME)

    companion object {
        private const val ARG_SCOPE_NAME = "arg_scope_name"

        fun create(scope: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SCOPE_NAME, scope)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(scopeName, DI.SIGN_IN_SCOPE))
        super.onCreate(savedInstanceState)
    }

    @ProvidePresenter
    fun providePresenter() =
        Toothpick.openScope(scopeName)
            .getInstance(SignInPresenter::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onTextChange(etEmail) { presenter.onEmailTextChange(it)}
        onTextChange(etPassword) { presenter.onPasswordTextChange(it)}

        btnAuth.setOnClickListener {
            presenter.btnAuthClick(scopeName ?: "")
        }
    }

    override fun onShowInputEmailError(message: String?) {
        tilEmail.error = message
    }

    override fun onShowInputPasswordError(message: String?) {
        tilPassword.error = message
    }
    
    override fun onShowProgress(show : Boolean) {
        progressBar.visible = show
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}