package com.example.itface.argo.model.repository.auth.registration

import com.example.itface.argo.entity.auth.RegistrationRequest
import com.example.itface.argo.model.data.server.ArgoApi
import com.example.itface.argo.system.SchedulersProvider
import javax.inject.Inject

class RegistrationRepository @Inject constructor(
    private val api: SomeApi,
    private val schedulers: SchedulersProvider
) {
    fun registration(registrationRequest: RegistrationRequest)
            = api
                 .registration(email = registrationRequest.email,
                               name = registrationRequest.userName,
                               password = registrationRequest.password)
                 .subscribeOn(schedulers.io())
                 .observeOn(schedulers.ui())
}