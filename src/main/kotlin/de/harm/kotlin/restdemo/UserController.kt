package de.harm.kotlin.restdemo


import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.util.*

data class RegisterLink(val keycloakId: UUID, val link: String)
data class PwResetLink(val keycloakId: UUID, val link: String, val expires: Instant = Instant.now())


@RestController
@RequestMapping("/api/users/{keycloakUserId}")
class UserController(val registerLinkLinkRepo: RegisterLinkLinkRepo) {

    @GetMapping("/register")
    @Throws(ResponseStatusException::class)
    fun register(@PathVariable(value = "keycloakUserId") keycloakId: UUID): RegisterLink {
        println("looking for ${keycloakId}")
        val entity = registerLinkLinkRepo.findById(keycloakId)
        if (entity == null) throw  ResponseStatusException(HttpStatus.NOT_FOUND)
        return RegisterLink(keycloakId, entity.link)
    }

    @GetMapping("/pwreset")
    fun pwReset(@PathVariable(value = "keycloakUserId") keycloakId: UUID) =
            listOf<PwResetLink>(
                    PwResetLink(keycloakId, "reset 1", Instant.now().plusSeconds(6000L)),
                    PwResetLink(keycloakId, "reset 2", Instant.now().minusSeconds(6000L)))

    @GetMapping("/pwreset/last")
    fun lastPwReset(@PathVariable(value = "keycloakUserId") keycloakId: UUID) =
            PwResetLink(keycloakId, "reset 1", Instant.now().plusSeconds(6000L))
}


