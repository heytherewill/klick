package shortcuts

interface KeyboardHandler {
    suspend fun handleLeftKeyPress(shift: Boolean)
    suspend fun handleMiddleKeyPress(shift: Boolean)
    suspend fun handleRightKeyPress(shift: Boolean)
}
