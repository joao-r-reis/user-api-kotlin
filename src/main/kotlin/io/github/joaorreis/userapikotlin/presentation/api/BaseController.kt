package io.github.joaorreis.userapikotlin.presentation.api

import io.ktor.application.ApplicationCall
import io.ktor.pipeline.PipelineContext

abstract class BaseController(protected val context: PipelineContext<Unit, ApplicationCall>) {
}