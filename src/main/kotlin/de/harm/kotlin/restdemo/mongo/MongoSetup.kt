package de.harm.kotlin.restdemo.mongo

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI


//@Component
//@ConfigurationProperties(prefix = "app.mongo")
class MongoSetup {
    var uri: String = "mongodb://localhost:27017"
    var database: String = "restdemo"

    //    @Bean
    fun mongoDatabase() = MongoClient(MongoClientURI(uri)).getDatabase(database)
}