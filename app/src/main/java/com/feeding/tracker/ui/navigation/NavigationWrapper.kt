package com.feeding.tracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.feeding.tracker.ui.home.HomeScreen
import com.feeding.tracker.ui.login.LoginScreen
import com.feeding.tracker.ui.signup.SignUpScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SignUp) {
        composable<Login> {
            LoginScreen()
        }
        composable<SignUp> {
            SignUpScreen { navController.navigate(Home) }
        }
        composable<Home> {
            HomeScreen()
        }
    }
}
