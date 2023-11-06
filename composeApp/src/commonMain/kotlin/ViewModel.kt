import com.jetbrains.handson.kmm.shared.entity.RocketLaunch
import kotlinx.coroutines.flow.MutableStateFlow

expect abstract class ViewModel() {
    protected fun onCleared()
    internal val privateRefreshCompletedState: MutableStateFlow<Boolean>
    val refreshCompletedState: MutableStateFlow<Boolean>
    internal val privateDatabaseInvalidState: MutableStateFlow<Boolean>
    val databaseInvalidState: MutableStateFlow<Boolean>
    internal val privateRefreshFailureState: MutableStateFlow<String>
    val refreshFailureState: MutableStateFlow<String>
    internal val privateLaunchesAvailableState: MutableStateFlow<List<RocketLaunch>>
    val launchesAvailableState: MutableStateFlow<List<RocketLaunch>>
    fun updateRefreshCompletedState(value: Boolean)
    fun updateDatabaseInvalidState(value: Boolean)
    fun updateRefreshFailureState(value: String)
    fun updateLaunchesAvailableState(value: List<RocketLaunch>)
}