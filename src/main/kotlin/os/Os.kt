package os

interface Os {
    val platform: Platform

    suspend fun launch(program: Program)
    suspend fun shutdown()

    enum class Platform {
        MacOS,
        Windows
    }
}
