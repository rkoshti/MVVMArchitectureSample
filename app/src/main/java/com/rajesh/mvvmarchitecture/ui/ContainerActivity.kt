package com.rajesh.mvvmarchitecture.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rajesh.mvvmarchitecture.data.remote.dto.SplashViewModel
import com.rajesh.mvvmarchitecture.databinding.ActivityContainerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

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