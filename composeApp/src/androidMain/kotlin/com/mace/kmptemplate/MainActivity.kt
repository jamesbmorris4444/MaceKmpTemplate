package com.mace.kmptemplate

import BloodViewModel
import RepositoryImpl
import ScreenNavigator
import Strings
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.jetbrains.handson.kmm.shared.SpaceXSDK
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory
import com.mace.mace_template.ui.theme.MaceTemplateTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel


class MainActivity : ComponentActivity() {
    private val sdk = SpaceXSDK(DatabaseDriverFactory(this))
    private val repository = RepositoryImpl()
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: BloodViewModel by viewModels()
        Strings.context = this
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            MaceTemplateTheme {
                ScreenNavigator(viewModel, repository, sdk)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
        //repository.saveStagingDatabase(Constants.MODIFIED_DATABASE_NAME, getDatabasePath(Constants.MODIFIED_DATABASE_NAME))
    }
}