package dbl.findpro.authentication.utils

import timber.log.Timber


object ValidationUtils {

    fun isValidName(name: String): Boolean {
        return name.trim().length >= 3
    }

    fun isValidEmail(email: String): Boolean {
        val cleanedEmail = email.trim()
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        val isValid = cleanedEmail.matches(emailRegex) && !cleanedEmail.contains(" ")

        Timber.d("📩 Validando email: '$cleanedEmail' -> ¿Válido? $isValid")

        return isValid
    }


    fun isValidPassword(password: String): Boolean {
        return password.trim().length >= 6
    }

    fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return password.trim() == confirmPassword.trim()
    }

    // Validar dirección (campos básicos)
    fun isValidAddress(address: String): Boolean {
        return address.isNotBlank() && address.length >= 5
    }

    // Validar provincia
    fun isValidProvince(province: String?): Boolean {
        return !province.isNullOrBlank() && province.length >= 3 && province.all { it.isLetter() || it.isWhitespace() }
    }

    // Validar ciudad
    fun isValidCity(city: String): Boolean {
        return city.isNotBlank() && city.all { it.isLetter() || it.isWhitespace() }
    }

    // Validar código postal
    fun isValidPostalCode(postalCode: String): Boolean {
        val postalCodeRegex = "^\\d{4,10}$".toRegex()
        return postalCode.matches(postalCodeRegex)
    }

    // Validar número de teléfono
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneRegex = "^\\+?[0-9 ]{9,15}$".toRegex()
        return phoneNumber.matches(phoneRegex)
    }

    // Validar categorías
    fun isValidCategory(category: String?): Boolean {
        return !category.isNullOrBlank()
    }

    // Validar radio de cobertura
    fun isValidCoverageRadius(radius: Int?): Boolean {
        return radius != null && radius > 0
    }

    // Validar si un correo ya está en uso
    fun isEmailInUse(email: String, existingEmails: List<String>): Boolean {
        return existingEmails.contains(email)
    }
}
