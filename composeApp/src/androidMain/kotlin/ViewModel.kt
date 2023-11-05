import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow

actual abstract class ViewModel : androidx.lifecycle.ViewModel() {
    val scope = viewModelScope
    actual override fun onCleared() {
        super.onCleared()
    }
    actual val privateRefreshCompletedState: MutableStateFlow<Boolean> = MutableStateFlow(true)
    actual val refreshCompletedState: MutableStateFlow<Boolean>
        get() = privateRefreshCompletedState
}