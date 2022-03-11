package com.rajesh.mvvmarchitecture.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.rajesh.mvvmarchitecture.R
import com.rajesh.mvvmarchitecture.data.remote.dto.SplashViewModel
import com.rajesh.mvvmarchitecture.databinding.ActivityContainerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    private val splashViewModel : SplashViewModel by viewModels()

    @Inject
    lateinit var fragmentFactory: ContainerFragmentFactory

    private lateinit var containerBinding: ActivityContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Show Splash screen with splash api
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                splashViewModel.isLoading.value
            }
        }

        supportFragmentManager.fragmentFactory = fragmentFactory

        containerBinding = ActivityContainerBinding.inflate(layoutInflater)
        setContentView(containerBinding.root)


    }
}