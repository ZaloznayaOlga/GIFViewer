package zaloznaya.olga.app.gifviewer.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.compose.rememberNavController
import zaloznaya.olga.app.gifviewer.R
import zaloznaya.olga.app.gifviewer.presentation.main_screen.ImagesListViewModel
import zaloznaya.olga.app.gifviewer.presentation.ui.components.ImagesScreen
import zaloznaya.olga.app.gifviewer.presentation.ui.theme.*

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<ImagesListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GifViewerTheme {
                val systemUiController = rememberSystemUiController()
                var orientation by remember { mutableStateOf(Configuration.ORIENTATION_PORTRAIT) }
                val configuration = LocalConfiguration.current
                // If our configuration changes then this will launch a new coroutine scope for it
                LaunchedEffect(configuration) {
                    snapshotFlow { configuration.orientation }
                        .collect { orientation = it }
                }

                val navController = rememberNavController()

                // Set status bar color
                val primaryBackground = Color.Black
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = primaryBackground,
                        darkIcons = true
                    )
                }

                // Content
                val tabs = listOf("Home", "Favorites", "Downloads")
                var currentTab by remember { mutableStateOf(tabs.first()) }

                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(1f)) {
                        when (currentTab) {
                            "Home" -> ImagesScreen(
                                screenOrientation = orientation,
                                viewModel = viewModel,
                                navController = navController
                            )
                            else -> {
                                Text(modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center), text = currentTab)
                            }
                        }
                    }
                    BottomNavigation(
                        backgroundColor = BaseBackground
                    ) {
                        tabs.forEach { tab ->
                            BottomNavigationItem(selected = tab == currentTab, onClick = {
                                currentTab = tab
                            }, icon = {
                                val color = if (tab == currentTab) AccentLight else Color.White
                                when (tab) {
                                    "Home" -> Image(painter = painterResource(id = R.drawable.icon_home), contentDescription = "Home", colorFilter = ColorFilter.tint(color))
                                    "Favorites" -> Image(painter = painterResource(id = R.drawable.icon_favorites), contentDescription = "Favorites", colorFilter = ColorFilter.tint(color))
                                    "Downloads" -> Image(painter = painterResource(id = R.drawable.icon_downloads), contentDescription = "Downloads", colorFilter = ColorFilter.tint(color))
                                }
                            }, label = {
                                Text(text = tab, color = if (tab == currentTab) AccentLight else Color.White)
                            })
                        }
                    }
                }
            }
        }

//        setContentView(R.layout.activity_main)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        }
    }
}