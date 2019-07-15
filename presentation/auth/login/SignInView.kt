package com.example.itface.argo.presentation.auth.login

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SignInView : MvpView {
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onShowInputEmailError(message : String?)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onShowInputPasswordError(message : String?)
    
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgress(show : Boolean)
}