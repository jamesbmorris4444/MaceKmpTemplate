
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition


data class AppBarState(
    val title: String = "Last Chance",
    val actions: (@Composable RowScope.() -> Unit)? = null,
    val navigationIcon: (@Composable () -> Unit)? = null
)

// Called one time at app startup
@Composable
fun ScreenNavigator(
    viewModel: BloodViewModel
) {
    Logger.d("JIMX 1")
    PreComposeApp {
        val appBarState by remember { mutableStateOf(AppBarState()) }
        val navigator = rememberNavigator()
        val completed = viewModel.refreshCompletedState.collectAsStateWithLifecycle()
        Logger.d("JIMX 1")
        Scaffold(
            topBar = {
                StartScreenAppBar(appBarState = appBarState)
            }
        ) { internalPadding ->
            Box(modifier = Modifier.padding(internalPadding)) {
                NavHost(
                    navigator = navigator,
                    // Navigation transition for the scenes in this NavHost, this is optional
                    navTransition = NavTransition(),
                    initialRoute = "home",
                ) {
                    // Define a scene to the navigation graph
                    scene(
                        route = "home",
                        // Navigation transition for this scene, this is optional
                        navTransition = NavTransition(),
                    ) {
                        Text(text = "Hello  Hello World!")
                    }
                }
            }
        }
    }
}

@Composable
fun StartScreenAppBar(
    appBarState: AppBarState
) {
    TopAppBar(
        title = { Text(appBarState.title) },
        actions = { appBarState.actions?.invoke(this) },
        navigationIcon = { appBarState.navigationIcon?.invoke() }
    )
}