package io.github.joaorreis.userapikotlin.application.services

import io.github.joaorreis.userapikotlin.data.repository.HelloRepository
import io.github.joaorreis.userapikotlin.domain.model.Jedi
import java.util.*

class HelloServiceImpl(private val repo: HelloRepository) : HelloService {

    private val random = Random()

    override suspend fun generateAndStore(): Jedi {
        val id = random.nextInt()
        val jedi = Jedi("name$id", id)
        repo.saveHello(jedi)
        return jedi
    }
}