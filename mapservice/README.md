# MapService Module - FindPRO

## 📋 Propósito del Módulo

El módulo **mapservice** proporciona todos los servicios relacionados con mapas, geolocalización y búsqueda espacial en FindPRO. Permite a los usuarios encontrar profesionales cercanos, visualizar ubicaciones en mapas interactivos y gestionar servicios basados en ubicación geográfica.

## 🚀 Tecnologías Utilizadas

### Mapas y Geolocalización
- **Mapbox Maps SDK** - Mapas interactivos de alta calidad
- **Mapbox Compose Extension** - Integración nativa con Jetpack Compose
- **Mapbox SDK Turf** - Cálculos geoespaciales avanzados
- **Google Play Services Location** - Servicios de ubicación precisos

### UI y Framework
- **Jetpack Compose** - Framework de UI declarativa
- **Material 3** - Sistema de diseño moderno
- **Compose Navigation** - Navegación entre pantallas
- **Coil Compose** - Carga de imágenes y avatares

### Networking y APIs
- **Retrofit** - Cliente HTTP para APIs de geolocalización
- **OkHttp** - Cliente HTTP con interceptors
- **Gson** - Serialización/deserialización JSON
- **Logging Interceptor** - Logs de peticiones de red

### Backend y Persistencia
- **Firebase Firestore** - Base de datos geoespacial
- **GeoHash** - Indexación geográfica eficiente
- **Kotlin Coroutines** - Operaciones asíncronas

### Arquitectura
- **Hilt** - Inyección de dependencias
- **MVVM Pattern** - Arquitectura Model-View-ViewModel
- **Repository Pattern** - Abstracción de fuentes de datos
- **Timber** - Sistema de logging estructurado

## 📁 Estructura del Módulo

```
mapservice/
├── src/
│   ├── main/
│   │   └── java/dbl/findpro/features/mapservices/
│   │       ├── data/
│   │       │   ├── repository/        # Implementación de repositorios
│   │       │   ├── datasource/        # APIs y fuentes de datos
│   │       │   ├── model/             # DTOs y modelos de red
│   │       │   └── mapper/            # Mappers entre capas
│   │       ├── domain/
│   │       │   ├── model/             # Entidades de dominio
│   │       │   ├── repository/        # Interfaces de repositorio
│   │       │   └── usecase/           # Casos de uso geoespaciales
│   │       ├── presentation/
│   │       │   ├── ui/
│   │       │   │   ├── map/           # Pantalla principal del mapa
│   │       │   │   ├── search/        # Búsqueda con filtros
│   │       │   │   ├── details/       # Detalles de ubicación
│   │       │   │   └── components/    # Componentes UI del mapa
│   │       │   ├── viewmodel/         # ViewModels
│   │       │   └── navigation/        # Navegación del módulo
│   │       ├── di/                    # Módulos de Hilt
│   │       └── utils/                 # Utilidades geoespaciales
│   ├── test/                          # Tests unitarios
│   └── androidTest/                   # Tests de UI
├── build.gradle.kts                   # Configuración de build
└── proguard-rules.pro                 # Reglas de ofuscación
```

## 🛠️ Instalación y Configuración

### Prerrequisitos
- **Token de Mapbox** configurado y válido
- **Permisos de ubicación** en AndroidManifest.xml
- **Google Play Services** instalados en el dispositivo
- **Módulo core** configurado

### Configuración de Mapbox

1. **Obtener Token de Acceso**
   ```
   - Ir a https://account.mapbox.com/access-tokens/
   - Crear token con permisos de Downloads:Read y Vision:Read
   - El token ya está configurado en settings.gradle.kts
   ```

2. **Configurar Permisos en AndroidManifest.xml**
   ```xml
   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
   <uses-permission android:name="android.permission.INTERNET" />
   ```

### Dependencias del Módulo

```kotlin
dependencies {
    implementation(project(":core"))
    
    // Mapbox Maps
    implementation("com.mapbox.maps:android:11.9.2")
    implementation("com.mapbox.extension:maps-compose:11.9.0")
    implementation("com.mapbox.mapboxsdk:mapbox-sdk-turf:6.11.0")
    implementation("com.mapbox.base:common:0.11.0")
    
    // Location Services
    implementation("com.google.android.gms:play-services-location:21.3.0")
    
    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    
    // Firebase
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.2")
    
    // UI
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("io.coil-kt:coil-compose:2.5.0")
}
```

## 🏃‍♂️ Cómo Usar el Módulo

### Integración en Navegación

```kotlin
// En el módulo app
import dbl.findpro.features.mapservices.presentation.navigation.mapNavGraph

NavHost(
    navController = navController,
    startDestination = "map"
) {
    mapNavGraph(
        navController = navController,
        onProfessionalSelected = { professional ->
            // Navegar a detalles del profesional
        }
    )
}
```

### Mostrar Mapa Principal

```kotlin
@Composable
fun MapScreen(
    onProfessionalClick: (Professional) -> Unit
) {
    val viewModel: MapViewModel = hiltViewModel()
    val mapState by viewModel.mapState.collectAsState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        MapboxMap(
            modifier = Modifier.fillMaxSize(),
            mapInitOptionsFactory = { context ->
                MapInitOptions(
                    context = context,
                    styleUri = Style.MAPBOX_STREETS
                )
            }
        ) {
            // Marcadores de profesionales
            mapState.professionals.forEach { professional ->
                MarkerAnnotation(
                    point = Point.fromLngLat(
                        professional.longitude, 
                        professional.latitude
                    )
                ) {
                    ProfessionalMarker(
                        professional = professional,
                        onClick = { onProfessionalClick(professional) }
                    )
                }
            }
        }
        
        // Controles de UI sobre el mapa
        SearchBar(
            modifier = Modifier.align(Alignment.TopStart),
            onSearch = viewModel::searchProfessionals
        )
        
        LocationButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = viewModel::centerOnUserLocation
        )
    }
}
```

### Búsqueda Geoespacial

```kotlin
@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    
    fun searchProfessionals(
        category: String,
        radius: Double = 5.0 // km
    ) {
        viewModelScope.launch {
            val userLocation = locationRepository.getCurrentLocation()
            val professionals = mapRepository.searchProfessionalsNearby(
                latitude = userLocation.latitude,
                longitude = userLocation.longitude,
                radius = radius,
                category = category
            )
            _mapState.value = _mapState.value.copy(
                professionals = professionals
            )
        }
    }
}
```

## 📱 Características Principales

### Funcionalidades de Mapas
- ✅ **Mapa interactivo** con Mapbox de alta calidad
- ✅ **Marcadores personalizados** para profesionales
- ✅ **Clustering inteligente** para múltiples marcadores
- ✅ **Estilos de mapa** adaptativos (día/noche)
- ✅ **Zoom y navegación** fluidos
- ✅ **Overlays informativos** sobre el mapa

### Geolocalización
- ✅ **Ubicación actual** del usuario en tiempo real
- ✅ **Tracking de movimiento** para servicios móviles
- ✅ **Geocoding** y reverse geocoding
- ✅ **Cálculo de distancias** precisas
- ✅ **Estimación de rutas** entre puntos

### Búsqueda Espacial
- ✅ **Búsqueda por proximidad** (radio configurable)
- ✅ **Filtros por categoría** de servicio
- ✅ **Búsqueda por texto** con autocompletado
- ✅ **Ordenamiento por distancia** y rating
- ✅ **Resultados en tiempo real** mientras se mueve el mapa

### Optimizaciones
- ✅ **Cache de mapas** para uso offline
- ✅ **Lazy loading** de marcadores
- ✅ **Compresión de datos** geoespaciales
- ✅ **Batch queries** optimizadas
- ✅ **Memory management** eficiente

## 🔧 Configuración de Desarrollo

### Repository Implementation

```kotlin
@Singleton
class MapRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val mapboxService: MapboxService
) : MapRepository {
    
    override suspend fun searchProfessionalsNearby(
        latitude: Double,
        longitude: Double,
        radius: Double,
        category: String?
    ): List<Professional> {
        
        // Calcular bounds geográficos
        val bounds = calculateBounds(latitude, longitude, radius)
        
        // Query a Firestore con filtros geoespaciales
        val query = firestore.collection("professionals")
            .whereGreaterThan("geohash", bounds.southwest)
            .whereLessThan("geohash", bounds.northeast)
        
        if (category != null) {
            query.whereEqualTo("category", category)
        }
        
        val snapshot = query.get().await()
        return snapshot.documents.mapNotNull { doc ->
            doc.toObject<Professional>()?.let { professional ->
                // Filtrar por distancia exacta
                val distance = calculateDistance(
                    latitude, longitude,
                    professional.latitude, professional.longitude
                )
                if (distance <= radius) professional else null
            }
        }
    }
}
```

### Location Services

```kotlin
@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    @ApplicationContext private val context: Context
) : LocationRepository {
    
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LatLng {
        return suspendCoroutine { continuation ->
            if (hasLocationPermission()) {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        location?.let {
                            continuation.resume(LatLng(it.latitude, it.longitude))
                        } ?: run {
                            requestNewLocation { newLocation ->
                                continuation.resume(newLocation)
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            } else {
                continuation.resumeWithException(
                    SecurityException("Location permission not granted")
                )
            }
        }
    }
}
```

### Utilidades Geoespaciales

```kotlin
object GeoUtils {
    
    fun calculateDistance(
        lat1: Double, lon1: Double,
        lat2: Double, lon2: Double
    ): Double {
        return TurfMeasurement.distance(
            Point.fromLngLat(lon1, lat1),
            Point.fromLngLat(lon2, lat2),
            TurfConstants.UNIT_KILOMETERS
        )
    }
    
    fun generateGeohash(latitude: Double, longitude: Double): String {
        // Implementar algoritmo de geohash para indexación
        return GeoHash.withCharacterPrecision(latitude, longitude, 12).toBase32()
    }
    
    fun calculateBounds(
        centerLat: Double, 
        centerLon: Double, 
        radiusKm: Double
    ): GeoBounds {
        // Calcular bounding box para queries eficientes
        val radiusDegrees = radiusKm / 111.0 // Aproximación
        return GeoBounds(
            southwest = LatLng(centerLat - radiusDegrees, centerLon - radiusDegrees),
            northeast = LatLng(centerLat + radiusDegrees, centerLon + radiusDegrees)
        )
    }
}
```

## 🎨 Componentes UI Personalizados

### Marcador de Profesional

```kotlin
@Composable
fun ProfessionalMarker(
    professional: Professional,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(60.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = professional.profileImage,
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            
            Text(
                text = professional.rating.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
```

## 🧪 Testing

### Tests Unitarios

```kotlin
@Test
fun `searchProfessionalsNearby should return professionals within radius`() = runTest {
    // Given
    val centerLat = 40.7128
    val centerLon = -74.0060
    val radius = 5.0
    
    // When
    val result = mapRepository.searchProfessionalsNearby(
        centerLat, centerLon, radius, null
    )
    
    // Then
    result.forEach { professional ->
        val distance = GeoUtils.calculateDistance(
            centerLat, centerLon,
            professional.latitude, professional.longitude
        )
        assert(distance <= radius)
    }
}
```

### Tests de Integración

```kotlin
@Test
fun testMapDisplaysProfessionalsCorrectly() {
    composeTestRule.setContent {
        MapScreen(
            onProfessionalClick = {}
        )
    }
    
    // Verificar que el mapa se muestra
    composeTestRule.waitForIdle()
    
    // Verificar marcadores de profesionales
    composeTestRule
        .onAllNodesWithTag("professional_marker")
        .assertCountEquals(expectedCount)
}
```

## 🔄 Sincronización de Datos

### Estrategia de Cache
- **Cache Level 1**: Datos en memoria durante la sesión
- **Cache Level 2**: Almacenamiento local con Room
- **Cache Level 3**: Tiles de mapa offline

### Actualización en Tiempo Real
```kotlin
class ProfessionalLocationUpdater @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    
    fun startLocationUpdates(professionalId: String) {
        firestore.collection("professionals")
            .document(professionalId)
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let { doc ->
                    val professional = doc.toObject<Professional>()
                    professional?.let {
                        // Actualizar posición en el mapa
                        updateProfessionalMarker(it)
                    }
                }
            }
    }
}
```

## 🤝 Integración con Otros Módulos

### Dependencias
- **core**: Utilidades geoespaciales y networking
- **userprofiles**: Datos de profesionales para mostrar en mapa

### APIs Expuestas
- **MapRepository**: Para búsquedas geoespaciales
- **LocationRepository**: Para servicios de ubicación
- **NavigationUtils**: Para direcciones y rutas

## 📊 Métricas y Performance

### KPIs Monitoreados
- Tiempo de carga del mapa
- Precisión de geolocalización
- Número de búsquedas por sesión
- Distancia promedio de búsquedas
- Eficiencia de queries geoespaciales

### Optimizaciones Implementadas
- Clustering adaptivo de marcadores
- Lazy loading de tiles
- Debouncing de búsquedas
- Cache inteligente de resultados

## 🌐 Consideraciones Offline

- **Mapas offline**: Cache de tiles para áreas frecuentes
- **Datos locales**: Sincronización cuando hay conectividad
- **Fallbacks**: Usar última ubicación conocida
- **UX degradada**: Indicadores claros de estado offline