package ru.androidlearning.simplemap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope
import ru.androidlearning.simplemap.navigation.MapFragmentScreen

class MainActivity : AppCompatActivity(R.layout.activity_main), AndroidScopeComponent {

    override val scope: Scope by activityScope()
    private val router: Router by inject()
    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator by lazy { AppNavigator(this, R.id.main_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState ?: router.newRootScreen(MapFragmentScreen)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}
