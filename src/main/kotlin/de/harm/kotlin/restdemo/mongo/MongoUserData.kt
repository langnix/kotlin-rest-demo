package de.harm.kotlin.restdemo.mongo

import com.mongodb.client.MongoDatabase
import de.harm.kotlin.restdemo.RegisterLinkEntity
import de.harm.kotlin.restdemo.RegisterLinkLinkRepo
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollection
import java.util.*


//@Component
class RegisterLinkMongoRepo(val database: MongoDatabase) : RegisterLinkLinkRepo {

    private val registerCol = database.getCollection<RegisterLinkEntity>("register")

    override fun findById(keycloakId: UUID) = registerCol.findOneById(keycloakId)

    override fun add(registerLink: RegisterLinkEntity) = registerCol.insertOne(registerLink)
}

