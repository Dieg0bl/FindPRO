package dbl.findpro.userprofiles.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dbl.findpro.core.domain.model.userGroupsAndProfiles.Category
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfessionalProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalProfileForm(
    profile: ProfessionalProfile,
    onProfileChange: (ProfessionalProfile) -> Unit,
    onSaveProfile: () -> Unit,
    categories: List<Category>, // ✅ Lista de categorías como parámetro
    validate: Boolean,
    onValidate: (Boolean) -> Unit
) {
    var selectedCategory by remember { mutableStateOf(profile.category) } // ✅ Estado para categoría

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

        OutlinedTextField(
            value = profile.coverageRadius.toString(),
            onValueChange = { newRadius ->
                val parsedRadius = newRadius.toIntOrNull() ?: 0
                onProfileChange(profile.copy(coverageRadius = parsedRadius))
            },
            label = { Text("Radio de cobertura (km)") },
            modifier = Modifier.fillMaxWidth()
        )

        // ✅ Selector de Categoría con Dropdown
        ExposedDropdownMenuBox(
            expanded = validate, // Se controla con `validate` para el caso inicial
            onExpandedChange = { onValidate(it) }
        ) {
            OutlinedTextField(
                value = selectedCategory.name,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = validate,
                onDismissRequest = { onValidate(false) }
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            selectedCategory = category
                            onProfileChange(profile.copy(category = category))
                            onValidate(false)
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = profile.profileProPicture ?: "",
            onValueChange = { onProfileChange(profile.copy(profileProPicture = it)) },
            label = { Text("URL de Foto de Perfil") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                onValidate(true)
                if (profile.contactId.isNotBlank() && profile.address != null && selectedCategory.name.isNotBlank()) {
                    onSaveProfile()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = profile.contactId.isNotBlank() && profile.address != null && selectedCategory.name.isNotBlank()
        ) {
            Text("Activar Perfil de Profesional")
        }
    }
}
