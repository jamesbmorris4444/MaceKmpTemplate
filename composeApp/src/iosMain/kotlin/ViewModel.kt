import kotlinx.coroutines.flow.MutableStateFlow

actual abstract class ViewModel actual constructor() {
    actual fun onCleared() {}
    actual val privateRefreshCompletedState: MutableStateFlow<Boolean> = MutableStateFlow(true)
    actual val refreshCompletedState: MutableStateFlow<Boolean>
        get() = privateRefreshCompletedState
}