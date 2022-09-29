package os

interface Os {
    suspend fun launch(program: Program)

    suspend fun shutdown()
}
