package com.mace.kmptemplate

import BloodViewModel
import RepositoryImpl
import Strings
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.jetbrains.handson.kmm.shared.SpaceXSDK
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory
import com.mace.mace_template.ui.theme.MaceTemplateTheme
import ui.DrawerAppComponent


class MainActivity : ComponentActivity() {
    private val databaseDriverFactory = DatabaseDriverFactory(this)
    private val sdk = SpaceXSDK(databaseDriverFactory)
    private val repository = RepositoryImpl(sdk, databaseDriverFactory)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: BloodViewModel by viewModels()
        Strings.context = this
        repository.screenWidth = Resources.getSystem().displayMetrics.widthPixels
        repository.screenHeight = Resources.getSystem().displayMetrics.heightPixels
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            MaceTemplateTheme {
                DrawerAppComponent(viewModel, repository)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        //repository.saveStagingDatabase(Constants.MODIFIED_DATABASE_NAME, getDatabasePath(Constants.MODIFIED_DATABASE_NAME))
    }
}