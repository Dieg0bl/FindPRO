package dbl.findpro.userprofiles.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ParticularProfile

@Composable
fun ParticularProfileForm(
    profile: ParticularProfile,
    onProfileChange: (ParticularProfile) -> Unit,
    onSaveProfile: () -> Unit,
    validate: Boolean,
    onValidate: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = profile.address?.street ?: "",
            onValueChange = { onProfileChange(profile.copy(address = profile.address?.copy(street = it))) },
            label = { Text("Calle y número") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = profile.address?.city ?: "",
            onValueChange = { onProfileChange(profile.copy(address = profile.address?.copy(city = it))) },
            label = { Text("Ciudad") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = profile.address?.postalCode ?: "",
            onValueChange = { onProfileChange(profile.copy(address = profile.address?.copy(postalCode = it))) },
            label = { Text("Código Postal") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = profile.contactId,
            onValueChange = { onProfileChange(profile.copy(contactId = it)) },
            label = { Text("ID de Contacto") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                onValidate(true)
                if (profile.contactId.isNotBlank() && profile.address != null) {
                    onSaveProfile()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = profile.contactId.isNotBlank() && profile.address != null
        ) {
            Text("Activar Perfil de Particular")
        }
    }
}
