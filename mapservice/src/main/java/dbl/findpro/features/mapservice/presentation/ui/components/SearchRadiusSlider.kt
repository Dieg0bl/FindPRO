package dbl.findpro.features.mapservice.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun SearchRadiusSlider(
    radius: Float,
    onRadiusChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    if (enabled) {
        Column(modifier = modifier) {
            Text("Radio: ${radius.toInt()} km", style = MaterialTheme.typography.bodySmall)
            Slider(
                value = radius,
                onValueChange = { if (enabled) onRadiusChange(it) },
                valueRange = 1f..100f,
                enabled = enabled
            )
        }
    }
}
