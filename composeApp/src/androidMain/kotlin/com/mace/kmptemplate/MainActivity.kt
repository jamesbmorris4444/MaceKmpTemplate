package com.mace.kmptemplate

import BloodViewModel
import ScreenNavigator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.mace.mace_template.ui.theme.MaceTemplateTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bloodViewModel: BloodViewModel by viewModels()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            MaceTemplateTheme {
                ScreenNavigator(bloodViewModel)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        //repository.saveStagingDatabase(Constants.MODIFIED_DATABASE_NAME, getDatabasePath(Constants.MODIFIED_DATABASE_NAME))
    }
}