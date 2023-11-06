
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import co.touchlab.kermit.Logger
import com.jetbrains.handson.kmm.shared.SpaceXSDK
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

data class AppBarState(
    val title: String = Strings.get("rocket_launch_pending_title"),
    val actions: (@Composable RowScope.() -> Unit)? = null,
    val navigationIcon: (@Composable () -> Unit)? = null
)

// Called one time at app startup
@Composable
fun ScreenNavigator(
    viewModel: BloodViewModel,
    repository: Repository,
    sdk: SpaceXSDK,
    openDrawer: () -> Unit = { }
) {
    PreComposeApp {
        var appBarState by remember { mutableStateOf(AppBarState()) }
        val navigator = rememberNavigator()
        Logger.d("JIMX 1   ${ScreenNames.RocketLaunch.name}")
        Scaffold(
            topBar = {
                StartScreenAppBar(appBarState = appBarState)
            }
        ) { internalPadding ->
            Box(modifier = Modifier.padding(internalPadding)) {
                NavHost(
                    navigator = navigator,
                    navTransition = NavTransition(),
                    initialRoute = ScreenNames.RocketLaunch.name,
                ) {
                    scene(
                        route =  ScreenNames.RocketLaunch.name,
                        navTransition = NavTransition(),
                    ) {
                        Logger.d("JIMX ScreenNavigator: launch screen=${ScreenNames.RocketLaunch.name}")
                        RocketLaunchScreen(
                            repository = repository,
                            sdk = sdk,
                            configAppBar = {
                                appBarState = it
                            },
                            canNavigateBack = true,
                            navigateUp = { navigator.goBack() },
                            openDrawer = openDrawer,
                            onItemButtonClicked = {
                                //donor = it
                                //transitionToCreateProductsScreen = true
                                //navController.navigate(manageDonorAfterSearchStringName)
                            },
                            viewModel = viewModel,
                            title = Strings.get("rocket_launch_complete_title")
                        )
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

enum class ScreenNames(val inDrawer: Boolean, val string: String) {
    RocketLaunch(false, Strings.get("Rocket Launch")),
//    CreateProducts(false, R.string.create_blood_product_title),
//    ManageDonorAfterSearch(false, R.string.manage_donor_after_search_title),
//    ManageDonorFromDrawer(true, R.string.manage_donor_from_drawer_title),
//    ReassociateDonation(true, R.string.reassociate_donation_title),
//    ViewDonorList(true, R.string.view_donor_list_title)
}