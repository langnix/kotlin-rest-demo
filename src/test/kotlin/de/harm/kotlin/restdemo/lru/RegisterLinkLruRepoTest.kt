package de.harm.kotlin.restdemo.lru

import de.harm.kotlin.restdemo.RegisterLinkEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class RegisterLinkLruRepoTest {

    val lruMap: RegisterLinkLruMap = RegisterLinkLruMap(3)
    val cut: RegisterLinkLruRepo = RegisterLinkLruRepo(lruMap)
    var keycloakId: UUID = UUID.randomUUID()

    @BeforeEach
    internal fun setUp() {
        lruMap.clear()
    }

    @Test
    fun `put and than find will return the same`() {
        val entity = RegisterLinkEntity(keycloakId, "some-link")
        cut.add(entity)
        assertThat(cut.findById(keycloakId)).isEqualTo(entity)
    }

    @Test
    internal fun `not more than maxSize inside`() {

        cut.add(RegisterLinkEntity(UUID.randomUUID(), "1"))
        cut.add(RegisterLinkEntity(keycloakId, "2"))
        assertThat(cut.findById(keycloakId)!!.keycloakId).isEqualTo(keycloakId)

        cut.add(RegisterLinkEntity(UUID.randomUUID(), "3"))
        assertThat(cut.findById(keycloakId)!!.keycloakId).isEqualTo(keycloakId)
        assertThat(lruMap.size).isEqualTo(3)
        cut.add(RegisterLinkEntity(UUID.randomUUID(), "4"))
        assertThat(lruMap.size).isEqualTo(3)
        assertThat(cut.findById(keycloakId)!!.keycloakId).isEqualTo(keycloakId)
        cut.add(RegisterLinkEntity(UUID.randomUUID(), "5"))
        assertThat(cut.findById(keycloakId)!!.keycloakId).isEqualTo(keycloakId)
        cut.add(RegisterLinkEntity(UUID.randomUUID(), "6"))
        assertThat(cut.findById(keycloakId)!!.keycloakId).isEqualTo(keycloakId)
    }
}