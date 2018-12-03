package clients

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder
import kotlinx.coroutines.runBlocking


class ClientsController(private val clientDao: ClientDao, private val app: Javalin) {


    fun router() {
        app.routes {

            ApiBuilder.get("/clients") { ctx ->
                runBlocking {
                    ctx.json(clientDao.findAll().await())
                }
            }

            ApiBuilder.post("/clients") { ctx ->
                val client = ctx.body<Client>()
                clientDao.save(client)
                ctx.status(201)
            }
        }
    }
}