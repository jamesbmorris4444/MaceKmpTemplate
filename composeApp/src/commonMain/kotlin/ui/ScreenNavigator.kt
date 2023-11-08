package ui
import BloodViewModel
import Repository
import Strings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import co.touchlab.kermit.Logger
import com.github.ajalt.colormath.extensions.android.composecolor.toComposeColor
import com.github.ajalt.colormath.model.RGB
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.transition.NavTransition

data class AppBarState(
    val title: String = Strings.get("rocket_launch_pending_title"),
    val actions: (@Composable RowScope.() -> Unit)? = null,
    val navigationIcon: (@Composable () -> Unit)? = null
)

// Called one time at app startup
@Composable
fun ScreenNavigator(
    navigator: Navigator,
    initialRoute: String,
    viewModel: BloodViewModel,
    repository: Repository,
    openDrawer: () -> Unit = { }
) {
    var appBarState by remember { mutableStateOf(AppBarState()) }
    Scaffold(
        topBar = {
            StartScreenAppBar(appBarState = appBarState)
        }
    ) { internalPadding ->
        Box(modifier = Modifier.padding(internalPadding)) {
            NavHost(
                navigator = navigator,
                navTransition = NavTransition(),
                initialRoute = initialRoute,
            ) {
                scene(
                    route = ScreenNames.RocketLaunch.name,
                    navTransition = NavTransition(),
                ) {
                    Logger.d("JIMX ScreenNavigator: launch screen=${ScreenNames.RocketLaunch.name}")
                    RocketLaunchScreen(
                        repository = repository,
                        configAppBar = {
                            appBarState = it
                        },
                        viewModel = viewModel,
                        title = Strings.get("rocket_launch_complete_title")
                    )
                }
                scene(
                    route = ScreenNames.DonateProductsSearch.name,
                    navTransition = NavTransition(),
                ) {
                    Logger.d("JIMX ScreenNavigator: launch screen=${ScreenNames.DonateProductsSearch.name}")
                    DonateProductsScreen(
                        repository = repository,
                        configAppBar = {
                            appBarState = it
                        },
                        canNavigateBack = navigator.canGoBack.collectAsState(true).value,
                        navigateUp = { navigator.goBack() },
                        openDrawer = openDrawer,
                        onItemButtonClicked = {
//                            donor = it
//                            transitionToCreateProductsScreen = true
//                            navController.navigate(manageDonorAfterSearchStringName)
                        },
                        viewModel = viewModel,
                        title = Strings.get("donate_products_search_screen_name")
                    )
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
        title = { Text(
            modifier = Modifier.testTag("item"),
            text = appBarState.title,
            color = RGB("#ffffff").toComposeColor(),
            style = MaterialTheme.typography.subtitle1
        ) },
        actions = { appBarState.actions?.invoke(this) },
        navigationIcon = { appBarState.navigationIcon?.invoke() }
    )
}

enum class ScreenNames(val inDrawer: Boolean, val string: String) {
    RocketLaunch(false, Strings.get("rocket_launch_screen_name")),
    DonateProductsSearch(false, Strings.get("donate_products_search_screen_name")),
//    CreateProducts(false, R.string.create_blood_product_title),
//    ManageDonorAfterSearch(false, R.string.manage_donor_after_search_title),
//    ManageDonorFromDrawer(true, R.string.manage_donor_from_drawer_title),
//    ReassociateDonation(true, R.string.reassociate_donation_title),
//    ViewDonorList(true, R.string.view_donor_list_title)
}