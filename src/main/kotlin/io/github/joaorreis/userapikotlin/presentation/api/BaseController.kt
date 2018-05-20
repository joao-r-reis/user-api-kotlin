package io.github.joaorreis.userapikotlin.presentation.api

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.pipeline.PipelineContext
import org.koin.ktor.ext.inject

abstract class BaseController(protected val context: PipelineContext<Unit, ApplicationCall>) {
    companion object {
        inline fun <reified T : BaseController> createController(context: PipelineContext<Unit, ApplicationCall>): T {

            val controller: T by context.call.application.inject { mapOf(CONTEXT_PARAM to context) }
            return controller
        }
    }
}