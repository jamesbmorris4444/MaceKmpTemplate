import com.jetbrains.handson.kmm.shared.entity.RocketLaunch
import kotlinx.coroutines.flow.MutableStateFlow

actual abstract class ViewModel : androidx.lifecycle.ViewModel() {

    actual override fun onCleared() {
        super.onCleared()
    }

    internal actual val privateRefreshCompletedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    actual val refreshCompletedState: MutableStateFlow<Boolean>
        get() = privateRefreshCompletedState

    internal actual val privateDatabaseInvalidState: MutableStateFlow<Boolean> = MutableStateFlow(true)
    actual val databaseInvalidState: MutableStateFlow<Boolean>
        get() = privateDatabaseInvalidState

    internal actual val privateRefreshFailureState: MutableStateFlow<String> = MutableStateFlow("")
    actual val refreshFailureState: MutableStateFlow<String>
        get() = privateRefreshFailureState

    internal actual val privateLaunchesAvailableState: MutableStateFlow<List<RocketLaunch>> = MutableStateFlow(listOf())
    actual val launchesAvailableState: MutableStateFlow<List<RocketLaunch>>
        get() = privateLaunchesAvailableState

    actual fun updateRefreshCompletedState(value: Boolean) {
        privateRefreshCompletedState.value = value
    }

    actual fun updateDatabaseInvalidState(value: Boolean) {
        privateDatabaseInvalidState.value = value
    }

    actual fun updateRefreshFailureState(value: String) {
        privateRefreshFailureState.value = value
    }

    actual fun updateLaunchesAvailableState(value: List<RocketLaunch>) {
        privateLaunchesAvailableState.value = value
    }
}