package dbl.findpro.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import timber.log.Timber
import dbl.findpro.features.mapservice.presentation.ui.screens.SearchProfessionalsScreen
import dbl.findpro.features.mapservice.presentation.ui.screens.SearchOffersScreen
import dbl.findpro.authentication.presentation.ui.screen.LoginScreen
import dbl.findpro.authentication.presentation.ui.screen.RegisterScreen
import dbl.findpro.authentication.presentation.ui.screen.ForgotPasswordScreen
import dbl.findpro.datosPruebaDesarrollo.StartScreen
import dbl.findpro.userprofiles.presentation.ui.screen.ActivateParticularProfileScreen
import dbl.findpro.userprofiles.presentation.ui.screen.ActivateProfessionalProfileScreen
import dbl.findpro.userprofiles.presentation.ui.screen.SelectProfileScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Start.route // 🔹 Pantalla de bienvenida al iniciar la app
    ) {
        // 📌 Pantalla de bienvenida
        composable(Routes.Start.route) {
            Timber.d("🔄 Navegando a StartScreen")
            StartScreen(navController)
        }

        // 📌 Pantalla principal sin autenticación: Búsqueda de Profesionales
        composable(Routes.SearchProfessionals.route) {
            Timber.d("🔄 Navegando a SearchProfessionalsScreen")
            SearchProfessionalsScreen(navController)
        }

        // 📌 Autenticación y Registro
        composable(Routes.Login.route) {
            Timber.d("🔄 Navegando a LoginScreen")
            LoginScreen(navController)
        }

        composable(Routes.Register.route) {
            Timber.d("🔄 Navegando a RegisterScreen")
            RegisterScreen(navController)
        }

        composable(Routes.ForgotPassword.route) {
            Timber.d("🔄 Navegando a ForgotPasswordScreen")
            ForgotPasswordScreen(navController)
        }

        // 📌 Selección de perfil tras autenticación
        composable(Routes.SelectProfile.route) {
            Timber.d("🔄 Navegando a SelectProfileScreen")
            SelectProfileScreen(navController)
        }

        // 📌 Activación de perfiles después del registro
        composable(Routes.ActivateParticularProfile.route) {
            Timber.d("🔄 Navegando a ActivateParticularProfileScreen")
            ActivateParticularProfileScreen(navController)
        }

        composable(Routes.ActivateProfessionalProfile.route) {
            Timber.d("🔄 Navegando a ActivateProfessionalProfileScreen")
            ActivateProfessionalProfileScreen(navController)
        }

        // 📌 Funcionalidades después de autenticación (ej. búsqueda de ofertas)
        composable(Routes.SearchOffers.route) {
            Timber.d("🔄 Navegando a SearchOffersScreen")
            SearchOffersScreen()
        }
    }
}
