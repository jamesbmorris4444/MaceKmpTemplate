
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.github.ajalt.colormath.extensions.android.composecolor.toComposeColor
import com.github.ajalt.colormath.model.RGB
import com.jetbrains.handson.kmm.shared.SpaceXSDK
import com.jetbrains.handson.kmm.shared.entity.Donor
import com.jetbrains.handson.kmm.shared.entity.RocketLaunch
import kotlinx.coroutines.launch
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle

@Composable
fun RocketLaunchScreen(
    repository: Repository,
    sdk: SpaceXSDK,
    forceReload: Boolean = true,
    configAppBar: (AppBarState) -> Unit,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    openDrawer: () -> Unit,
    onItemButtonClicked: (donor: Donor) -> Unit,
    viewModel: BloodViewModel,
    title: String
) {

    @Composable
    fun CustomCircularProgressBar() {
        CircularProgressIndicator(
            modifier = Modifier.size(120.dp),
            color = Color.Green,
            strokeWidth = 6.dp
        )
    }

    fun success(list: List<RocketLaunch>) {
        Logger.d("JIMX RocketLaunchScreen success=$list")
        viewModel.updateRefreshCompletedState(true)
        viewModel.updateDatabaseInvalidState(false)
        viewModel.updateRefreshFailureState("")
        viewModel.updateLaunchesAvailableState(list)
    }

    fun failure(message: String) {
        Logger.d("JIMX RocketLaunchScreen failure=$message")
        viewModel.updateRefreshCompletedState(true)
        viewModel.updateDatabaseInvalidState(false)
        viewModel.updateRefreshFailureState(message)
    }

    Logger.d("JIMX Compose: ${ScreenNames.RocketLaunch.name}")
//    viewModel.setAppDatabase()
//    val showStandardModalState = viewModel.showStandardModalState.observeAsState().value ?: StandardModalArgs()
    val composableScope = rememberCoroutineScope()
    val completed = viewModel.refreshCompletedState.collectAsStateWithLifecycle().value
    val isInvalid = viewModel.databaseInvalidState.collectAsStateWithLifecycle().value
    val failure = viewModel.refreshFailureState.collectAsStateWithLifecycle().value
    when {
        isInvalid -> {
            composableScope.launch {
                repository.refreshDatabase(sdk, ::success, ::failure)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CustomCircularProgressBar()
            }
        }

        failure.isNotEmpty() -> {
//            if (showStandardModalState.topIconResId >= 0) {
//                StandardModal(
//                    showStandardModalState.topIconResId,
//                    showStandardModalState.titleText,
//                    showStandardModalState.bodyText,
//                    showStandardModalState.positiveText,
//                    showStandardModalState.negativeText,
//                    showStandardModalState.neutralText,
//                    showStandardModalState.onDismiss
//                )
//            } else {
//                viewModel.changeShowStandardModalState(
//                    StandardModalArgs(
//                        topIconResId = R.drawable.notification,
//                        titleText = viewModel.getResources().getString(R.string.failure_db_entries_title_text),
//                        bodyText = viewModel.getResources().getString(R.string.failure_db_entries_body_text, failure),
//                        positiveText = viewModel.getResources().getString(R.string.positive_button_text_ok),
//                    ) {
//                        navigateUp()
//                        viewModel.changeShowStandardModalState(StandardModalArgs())
//                        viewModel.changeRefreshFailureState("")
//                    }
//                )
//            }
        }

        completed -> {
            RocketLaunchHandler(
                configAppBar = configAppBar,
                viewModel = viewModel,
                title = title)
        }

        else -> {
            viewModel.updateRefreshCompletedState(false)
            viewModel.updateDatabaseInvalidState(true)
            viewModel.updateRefreshFailureState("")
        }
    }
}

@Composable
fun RocketLaunchHandler(
    configAppBar: (AppBarState) -> Unit,
    viewModel: BloodViewModel,
    title: String,
) {
    val launches: List<RocketLaunch> = viewModel.launchesAvailableState.collectAsStateWithLifecycle().value

    @Composable
    fun LaunchesList(launches: List<RocketLaunch>) {
        LazyColumn {
            launches.forEachIndexed { index, item ->
                item {
                    LaunchElementText(
                        launches[index].flightNumber.toString(),
                        launches[index].missionName,
                        launches[index].details ?: "",
                        launches[index].launchDate,
                        launches[index].launchSuccess ?: true
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        configAppBar(
            AppBarState(
                title = title
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchesList(launches)
        }
    }
}

@Composable
fun LaunchElementText(
    flightNumber: String,
    missionName: String,
    details: String,
    launchDate: String,
    launchSuccess: Boolean
) {
    Text(
        modifier = Modifier.testTag("item"),
        text = "flight number: $flightNumber",
        color = RGB("#000000").toComposeColor(),
        style = MaterialTheme.typography.body1
    )
    Text(
        modifier = Modifier.testTag("item"),
        text = "mission name: $missionName",
        color = RGB("#000000").toComposeColor(),
        style = MaterialTheme.typography.body1
    )
    Text(
        modifier = Modifier.testTag("item"),
        text = "details: $details",
        color = RGB("#000000").toComposeColor(),
        style = MaterialTheme.typography.body1
    )
    Text(
        modifier = Modifier.testTag("item"),
        text = "launch date: $launchDate",
        color = RGB("#000000").toComposeColor(),
        style = MaterialTheme.typography.body1
    )
    Text(
        modifier = Modifier.testTag("item"),
        text = if (launchSuccess) "Successful" else "Failed",
        color = if (launchSuccess) RGB("#00ff00").toComposeColor() else RGB("#ff0000").toComposeColor(),
        style = MaterialTheme.typography.body1
    )
    Divider(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp), color = RGB("#000000").toComposeColor(), thickness = 2.dp)
}
