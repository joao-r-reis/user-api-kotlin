package io.github.joaorreis.userapikotlin.application.services

import io.github.joaorreis.userapikotlin.domain.model.Jedi

interface HelloService {
    suspend fun generateAndStore(): Jedi
}