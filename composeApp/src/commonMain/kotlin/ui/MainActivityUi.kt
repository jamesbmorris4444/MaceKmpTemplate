package ui
import BloodViewModel
import Repository
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

// See https://www.geeksforgeeks.org/android-jetpack-compose-implement-navigation-drawer/ for Navigation Drawer

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DrawerAppComponent(
    bloodViewModel: BloodViewModel,
    repository: Repository
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val currentScreen = remember { mutableStateOf(ScreenNames.DonateProductsSearch) }
    val coroutineScope = rememberCoroutineScope()
    @Composable
    fun DrawerContentComponent(
        navigator: Navigator,
        closeDrawer: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .padding(top = 100.dp)
                .background(color = MaterialTheme.colors.background),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .align(CenterHorizontally)
            ) {
                Image(
                    painter = painterResource("drawable/fs_logo.png"),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(120.dp)
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                    text = "",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )
            }
            Spacer(Modifier.height(24.dp))
            for (screen in ScreenNames.values()) {
                if (screen.inDrawer) {
                    Column(
                        Modifier.clickable(onClick = {
                            closeDrawer()
                            currentScreen.value = screen
                            Logger.d("click=${screen.string}")
                            navigator.navigate(screen.string)
                        })
                    ) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = if (currentScreen.value == screen) {
                                MaterialTheme.colors.onSurface
                            } else {
                                MaterialTheme.colors.surface
                            }
                        ) {
                            Text(
                                text = screen.string,
                                modifier = Modifier.padding(16.dp),
                                color = if (currentScreen.value == screen) {
                                    MaterialTheme.colors.onBackground
                                } else {
                                    MaterialTheme.colors.background
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun BodyContentComponent(
        navigator: Navigator,
        openDrawer: () -> Unit,
        bloodViewModel: BloodViewModel
    ) {
        ScreenNavigator(
            viewModel = bloodViewModel,
            openDrawer = openDrawer,
            navigator = navigator,
//            initialRoute = ScreenNames.RocketLaunch.name,
            initialRoute = ScreenNames.DonateProductsSearch.name,
            repository = repository
        )
    }

    PreComposeApp {
        val navigator = rememberNavigator()
        ModalDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                DrawerContentComponent(
                    navigator = navigator,
                    closeDrawer = { coroutineScope.launch { drawerState.close() } }
                )
            },
            drawerBackgroundColor = MaterialTheme.colors.onBackground,
            content = {
                BodyContentComponent(
                    navigator = navigator,
                    openDrawer = { coroutineScope.launch { drawerState.open() } },
                    bloodViewModel = bloodViewModel
                )
            }
        )
    }
}