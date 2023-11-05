import kotlinx.coroutines.flow.MutableStateFlow

expect abstract class ViewModel() {
    protected fun onCleared()
    val privateRefreshCompletedState: MutableStateFlow<Boolean>
    val refreshCompletedState: MutableStateFlow<Boolean>
}