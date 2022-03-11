package com.rajesh.mvvmarchitecture.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rajesh.mvvmarchitecture.adapter.CoinAdapter
import javax.inject.Inject

class ContainerFragmentFactory @Inject constructor(
    private val coinAdapter: CoinAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

       return when(className) {
           ListFragment::class.java.name -> ListFragment( coinAdapter )
           DetailFragment::class.java.name -> DetailFragment()
           else -> super.instantiate(classLoader, className)
       }
    }
}