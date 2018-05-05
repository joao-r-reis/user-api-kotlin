package userapikotlin.application

import userapikotlin.data.HelloRepository
import userapikotlin.domain.Jedi
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