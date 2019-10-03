package de.harm.kotlin.restdemo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

data class RegisterLink(val keycloakId: UUID, val link: String)
data class PwResetLink(val keycloakId: UUID, val link: String, val expires: Instant = Instant.now())

@RestController
@RequestMapping("/api/users/{keycloakUserId}")
class UserController {

    @GetMapping("/register")
    fun register(@PathVariable(value = "keycloakUserId") keycloakId: UUID) = RegisterLink(keycloakId, "somewhere")

    @GetMapping("/pwreset")
    fun pwReset(@PathVariable(value = "keycloakUserId") keycloakId: UUID) =
            listOf<PwResetLink>(
                    PwResetLink(keycloakId, "reset 1", Instant.now().plusSeconds(6000L)),
                    PwResetLink(keycloakId, "reset 2", Instant.now().minusSeconds(6000L)))

    @GetMapping("/pwreset/last")
    fun lastPwReset(@PathVariable(value = "keycloakUserId") keycloakId: UUID) =
            PwResetLink(keycloakId, "reset 1", Instant.now().plusSeconds(6000L))
}