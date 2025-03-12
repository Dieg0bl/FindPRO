package dbl.findpro.presentation.ui.navigation

sealed class Routes(val route: String) {
    object Start : Routes("start") // 🔹 Pantalla de bienvenida
    object SearchProfessionals : Routes("search_professionals") // 🔹 Pantalla inicial sin autenticación
    object Login : Routes("login")
    object Register : Routes("register")
    object ForgotPassword : Routes("forgot_password")
    object SelectProfile : Routes("select_profile") // 🔹 Nueva pantalla de selección de perfil
    object ActivateParticularProfile : Routes("activate_particular_profile")
    object ActivateProfessionalProfile : Routes("activate_professional_profile")
    object SearchOffers : Routes("search_offers") // 🔹 Solo accesible tras autenticación
}
