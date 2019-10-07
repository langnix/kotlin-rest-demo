package de.harm.kotlin.restdemo


import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.web.server.ResponseStatusException
import java.util.*

internal class UserControllerTest {
    val registerLinkLinkRepo: RegisterLinkLinkRepo = mockk()

    val cut: UserController = UserController(registerLinkLinkRepo)
    @Test
    internal fun `no register link exists`() {
        val keycloakId = UUID.randomUUID()
        every { registerLinkLinkRepo.findById(keycloakId) }.returns(null)
        assertThatThrownBy { cut.register(keycloakId) }.isInstanceOf(ResponseStatusException::class.java)
    }
}