package de.harm.kotlin.restdemo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.Instant
import java.util.*

// see :https://www.baeldung.com/spring-rest-template-list
class ListOfPwResetParam : ParameterizedTypeReference<List<PwResetLink>>()

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class UserControllerIT(@Autowired val restTemplate: TestRestTemplate, @Autowired val lruRepo: RegisterLinkLinkRepo) {
    val keycloakId = UUID.randomUUID()
    @BeforeEach
    internal fun setUp() {
        lruRepo.add(RegisterLinkEntity(keycloakId, "junit-run"))
    }


    @Test
    fun `register returns same userId`() {
        val res = restTemplate.getForEntity<RegisterLink>("/api/users/${keycloakId}/register")
        assertThat(res.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(res.body?.keycloakId).isEqualTo(keycloakId)
    }

    @Test
    internal fun `register returns 404 on unknown keycloakId`() {
        val unknownId = UUID.randomUUID()
        val res = restTemplate.getForEntity("/api/users/${unknownId}/register", String::class.java)
        assertThat(res.statusCode).isEqualTo(HttpStatus.NOT_FOUND)


    }

    @Test
    internal fun `pwReset returns all links of user`() {
        val res = restTemplate.exchange(
                "/api/users/${keycloakId}/pwreset",
                HttpMethod.GET,
                null, ListOfPwResetParam()
        )
        assertThat(res.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(res.body?.size).isEqualTo(2) // all of them
        val lofPw = res.body!!
        lofPw.forEach { assertThat(it.keycloakId).isEqualTo(keycloakId) }
    }

    @Test
    internal fun `pwReset last returns a not expired link`() {
        val res = restTemplate.getForEntity<PwResetLink>("/api/users/${keycloakId}/pwreset/last")
        assertThat(res.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(res.body?.expires).isAfter(Instant.now())
    }

}