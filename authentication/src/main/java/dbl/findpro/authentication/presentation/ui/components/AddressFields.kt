package dbl.findpro.authentication.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dbl.findpro.authentication.utils.ValidationUtils
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Address
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Coordinates
import dbl.findpro.features.mapservice.presentation.ui.components.SearchRadiusSlider

@Composable
fun AddressFields(
    address: Address,
    onAddressChange: (Address) -> Unit,
    onGeocodeResult: (Coordinates?) -> Unit,
    onGeocodeRequest: (Address) -> Unit, // ✅ Usa una función en lugar de un UseCase
    showRadiusSlider: Boolean = false,
    radius: Float = 10f,
    onRadiusChange: (Float) -> Unit = {},
    validate: Boolean = false // Controla si las validaciones están activas
) {
    // Estados de error basados en la validación
    val provinceError = remember(address.province, validate) {
        validate && address.province?.let { !ValidationUtils.isValidProvince(it) } == true
    }
    val cityError = remember(address.city, validate) {
        validate && address.city?.let { !ValidationUtils.isValidCity(it) } == true
    }
    val streetError = remember(address.street, validate) {
        validate && address.street?.let { !ValidationUtils.isValidAddress(it) } == true
    }
    val postalCodeError = remember(address.postalCode, validate) {
        validate && address.postalCode?.let { !ValidationUtils.isValidPostalCode(it) } == true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        // Campo de dirección: Provincia
        OutlinedTextField(
            value = address.province ?: "",
            onValueChange = { province ->
                val updatedAddress = address.copy(province = province)
                onAddressChange(updatedAddress)
            },
            label = { Text("Provincia") },
            isError = provinceError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (provinceError) {
            Text(
                text = "Introduce una provincia válida.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Campo de dirección: Ciudad
        OutlinedTextField(
            value = address.city ?: "",
            onValueChange = { city ->
                val updatedAddress = address.copy(city = city)
                onAddressChange(updatedAddress)
            },
            label = { Text("Ciudad") },
            isError = cityError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (cityError) {
            Text(
                text = "Introduce una ciudad válida.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Campo de dirección: Calle y número
        OutlinedTextField(
            value = address.street ?: "",
            onValueChange = { street ->
                val updatedAddress = address.copy(street = street)
                onAddressChange(updatedAddress)
            },
            label = { Text("Calle y número") },
            isError = streetError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (streetError) {
            Text(
                text = "Introduce una dirección válida (mínimo 5 caracteres).",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Campo de dirección: Código Postal
        OutlinedTextField(
            value = address.postalCode ?: "",
            onValueChange = { postalCode ->
                val updatedAddress = address.copy(postalCode = postalCode)
                onAddressChange(updatedAddress)
            },
            label = { Text("Código Postal") },
            isError = postalCodeError,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (postalCodeError) {
            Text(
                text = "Introduce un código postal válido.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Botón para obtener coordenadas
        Button(
            onClick = { onGeocodeRequest(address) }, // ✅ Usa la función de geocodificación
            modifier = Modifier.fillMaxWidth(),
            enabled = !provinceError && !cityError && !streetError && !postalCodeError
        ) {
            Text("Obtener Coordenadas")
        }

        // Slider de radio (opcional)
        if (showRadiusSlider) {
            Spacer(modifier = Modifier.height(16.dp))
            SearchRadiusSlider(
                radius = radius,
                onRadiusChange = onRadiusChange,
                modifier = Modifier.fillMaxWidth(),
                enabled = true
            )
        }
    }
}
