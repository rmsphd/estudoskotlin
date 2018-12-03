package clients

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


data class Client(val id: Int, val name: String)

class ClientDao {

    object Clients : Table("person") {
        val id = integer("id")
        val name = varchar("name", 100)
    }

    fun findAll() = GlobalScope.async {
        transaction {
            Clients.selectAll().map {
                Client(
                    it[Clients.id],
                    it[Clients.name]
                )
            }
        }
    }

    fun save(client: Client) = transaction {
        Clients.insert {
            it[name] = client.name
            it[id] = client.id
        }
    }
}





