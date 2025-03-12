package dbl.findpro.features.mapservice.presentation.ui.components

import android.util.DisplayMetrics
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getRefinedZoom(): Double {
    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val densityDpi = displayMetrics.densityDpi.toDouble()

    val referenceDpi = 625.0
    val referenceZoom = 5.0
    val dpiFactor = densityDpi / referenceDpi
    val adjustedZoom = referenceZoom * dpiFactor
    val adjustedZoomForResolution = adjustedZoom * 1.1

    return adjustedZoomForResolution
}
