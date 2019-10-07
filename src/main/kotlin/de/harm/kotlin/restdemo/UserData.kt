package de.harm.kotlin.restdemo

import java.time.Instant
import java.util.*

data class RegisterLinkEntity(val keycloakId: UUID, val link: String)

interface RegisterLinkLinkRepo {

    fun findById(keycloakId: UUID): RegisterLinkEntity?
    fun add(registerLink: RegisterLinkEntity)
}


data class PwResetLinkEntity(val link: String, val expires: Instant)
data class PwResetLinkOfUserEntity(val keycloakId: UUID, val pwResets: Array<PwResetLinkEntity>)