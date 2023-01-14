package com.advanced.advanceddragonball.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.advanced.advanceddragonball.R
import com.advanced.advanceddragonball.data.local.datastore.PrefsDataStore
import com.advanced.advanceddragonball.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(


) {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var prefsDataStore: PrefsDataStore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)
//        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InByaXZhdGUifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiaWRlbnRpZnkiOiIyNjBENjk3My00Njc0LTQyRDQtQjUxRi00MjYwRTBBMUJCOUYiLCJlbWFpbCI6InJyb2pvLnZhQGdtYWlsLmNvbSJ9.lQOqPIfkP0_GJs8lik1PmfacpoQcyDxy3NGJGeflOEc"


//        val token = ""
//        if (token.isNotEmpty()){
//            navController = findNavController(R.id.HeroListFragment)
//            appBarConfiguration = AppBarConfiguration(navController.graph)
//            setupActionBarWithNavController(navController, appBarConfiguration)
//
//        } else
//
//            navController = findNavController(R.id.nav_host_fragment_content_main)
//            appBarConfiguration = AppBarConfiguration(navController.graph)
//            setupActionBarWithNavController(navController, appBarConfiguration)

//        navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)


        lifecycleScope.launch {


            Log.d("TOKEN ACTIVITY", "${isTokenEmpty()}")

            if (isTokenEmpty()) {
                Log.d("TOKEN ACTIVITY", "token empty ${isTokenEmpty()}")

                navGraph.setStartDestination(R.id.LoginFragment)
            } else {

                navGraph.setStartDestination(R.id.HeroListFragment)
                Log.d("TOKEN ACTIVITY", "token not empty ${isTokenEmpty()}")
            }
        }

//        private suspend fun isTokenEmpty(): Boolean {
//        val startDestination = CoroutineScope(Dispatchers.Main).async asyncnavGraph@{
//            if (isTokenEmpty()) {
//                Log.d("TOKEN ACTIVITY", "token empty ${isTokenEmpty()}")
//
//                return@asyncnavGraph.setStartDestination(R.id.LoginFragment)
//            } else {
//
//                navGraph.setStartDestination(R.id.HeroListFragment)
//                Log.d("TOKEN ACTIVITY", "token not empty ${isTokenEmpty()}")
//            }
//        }
//        return isNotTokenEmpty.await()
//        }




        navController.graph = navGraph

        binding.toolbar.setupWithNavController(navController)



//        binding.bottomNavView.setupWithNavController(navController)


//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

    }


    private suspend fun isTokenEmpty(): Boolean {
        val isNotTokenEmpty = CoroutineScope(Dispatchers.IO).async {

            val token = prefsDataStore.getToken("TOKEN")
            return@async token?.isEmpty() == true
        }
        return isNotTokenEmpty.await()
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}