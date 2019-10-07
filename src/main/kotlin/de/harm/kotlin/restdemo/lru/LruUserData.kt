package de.harm.kotlin.restdemo.lru

import de.harm.kotlin.restdemo.RegisterLinkEntity
import de.harm.kotlin.restdemo.RegisterLinkLinkRepo
import org.springframework.stereotype.Component
import java.util.*

@Component
class RegisterLinkLruMap(val maxSize: Int = 1000, loadfactor: Float = 0.75f) :
        LinkedHashMap<UUID, RegisterLinkEntity>(16, loadfactor, true) {
    override fun removeEldestEntry(eldest: MutableMap.MutableEntry<UUID, RegisterLinkEntity>?): Boolean {
        val res = size > maxSize
        if (res) println("Removing: ${eldest}")
        return res
    }
}

@Component
class RegisterLinkLruRepo(val lruCache: RegisterLinkLruMap) : RegisterLinkLinkRepo {
    override fun findById(keycloakId: UUID) = lruCache[keycloakId]
    override fun add(registerLink: RegisterLinkEntity) {
        lruCache.put(registerLink.keycloakId, registerLink)
    }
}