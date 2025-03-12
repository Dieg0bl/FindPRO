package dbl.findpro

import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import dbl.findpro.presentation.ui.navigation.AppNavigation
import dbl.findpro.presentation.ui.theme.FindProTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
        super.onCreate(savedInstanceState)
        Timber.d("✅ MainActivity creada")

        setContent {
            FindProTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
