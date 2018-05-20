package io.github.joaorreis.userapikotlin.presentation.api

import org.koin.dsl.context.Context

const val CONTEXT_PARAM: String = "context"

fun Context.addControllersToKoinModule() {
    factory { params -> UserController(params[CONTEXT_PARAM], get()) }
}