import clients.ClientDao
import clients.ClientsController
import io.javalin.Javalin
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {

    Database.connect("jdbc:postgresql://localhost:5432/c6test",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "concrete")

    val app = Javalin.create().apply {
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start(7000)

    app.get("/") { ctx -> ctx.result("Hello World") }


    val clientDao = ClientDao()

    val clients = ClientsController(clientDao,app)
    clients.router()

}