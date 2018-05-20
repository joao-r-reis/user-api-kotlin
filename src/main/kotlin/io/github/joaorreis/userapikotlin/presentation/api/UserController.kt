package io.github.joaorreis.userapikotlin.presentation.api

import io.github.joaorreis.userapikotlin.application.dto.UserCreationRequest
import io.github.joaorreis.userapikotlin.application.services.UserService
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.pipeline.PipelineContext
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post

class UserController(
    context: PipelineContext<Unit, ApplicationCall>,
    private val userService: UserService
)
    : BaseController(context) {

    companion object {

        fun setupRoutes(route: Route) {
            route.get("/users") {
                createController<UserController>(this).get()
            }
            route.post("/users") {
                createController<UserController>(this).post()
            }
        }
    }

    suspend fun get() {
        val users = userService.getAllUsers()
        context.call.respond(users)
    }

    suspend fun post() {
        val params = context.call.receive<UserCreationRequest>()
        var newUser = userService.createUser(params)
        context.call.respond(newUser)
    }
}