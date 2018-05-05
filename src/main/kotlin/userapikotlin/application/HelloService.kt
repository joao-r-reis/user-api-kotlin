package userapikotlin.application

import userapikotlin.domain.Jedi

interface HelloService {
    suspend fun generateAndStore(): Jedi
}