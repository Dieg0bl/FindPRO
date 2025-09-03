# UserProfiles Module - FindPRO

## 📋 Propósito del Módulo

El módulo **userprofiles** gestiona toda la información relacionada con perfiles de usuarios en FindPRO. Maneja tanto perfiles de particulares (clientes) como de profesionales (proveedores de servicios), incluyendo la creación, edición, visualización y gestión de datos personales, preferencias, portfolios y configuraciones de cuenta.

## 🚀 Tecnologías Utilizadas

### UI y Experiencia de Usuario
- **Jetpack Compose** - Framework de UI declarativa moderno
- **Material 3** - Sistema de diseño de Google
- **Navigation Compose** - Navegación fluida entre pantallas
- **Coil Compose** - Carga y manejo eficiente de imágenes
- **Compose Animation** - Animaciones fluidas y atractivas

### Backend y Persistencia
- **Firebase Firestore** - Base de datos NoSQL para perfiles
- **Firebase Storage** - Almacenamiento de imágenes y documentos
- **Room Database** - Cache local de datos de usuario
- **DataStore Preferences** - Configuraciones y preferencias

### Arquitectura y Patrones
- **Hilt** - Inyección de dependencias
- **MVVM Pattern** - Arquitectura Model-View-ViewModel
- **Repository Pattern** - Abstracción de fuentes de datos
- **Clean Architecture** - Separación de responsabilidades

### Funcionalidades Específicas
- **Camera/Gallery Integration** - Captura y selección de imágenes
- **Input Validation** - Validación robusta de formularios
- **Kotlin Coroutines** - Operaciones asíncronas
- **Timber** - Sistema de logging estructurado

## 📁 Estructura del Módulo

```
userprofiles/
├── src/
│   ├── main/
│   │   └── java/dbl/findpro/features/userprofiles/
│   │       ├── data/
│   │       │   ├── repository/        # Implementación de repositorios
│   │       │   ├── datasource/        # Firebase y fuentes locales
│   │       │   ├── model/             # DTOs y modelos de datos
│   │       │   └── mapper/            # Conversión entre capas
│   │       ├── domain/
│   │       │   ├── model/             # Entidades de dominio
│   │       │   ├── repository/        # Interfaces de repositorio
│   │       │   └── usecase/           # Casos de uso de perfiles
│   │       ├── presentation/
│   │       │   ├── ui/
│   │       │   │   ├── profile/       # Pantalla principal de perfil
│   │       │   │   ├── edit/          # Edición de perfil
│   │       │   │   ├── settings/      # Configuraciones
│   │       │   │   ├── portfolio/     # Portfolio de profesionales
│   │       │   │   ├── reviews/       # Sistema de reseñas
│   │       │   │   └── components/    # Componentes reutilizables
│   │       │   ├── viewmodel/         # ViewModels
│   │       │   └── navigation/        # Navegación del módulo
│   │       ├── di/                    # Módulos de Hilt
│   │       └── utils/                 # Utilidades del módulo
│   ├── test/                          # Tests unitarios
│   └── androidTest/                   # Tests de UI
├── build.gradle.kts                   # Configuración de build
└── proguard-rules.pro                 # Reglas de ofuscación
```

## 🛠️ Instalación y Configuración

### Prerrequisitos
- **Firebase Storage** configurado para imágenes
- **Firebase Firestore** con reglas de seguridad
- **Permisos de cámara y almacenamiento** en AndroidManifest.xml
- **Módulos core y authentication** configurados

### Configuración de Firebase Storage

```javascript
// Firestore Security Rules
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Perfiles de usuario
    match /user_profiles/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Perfiles de profesionales (lectura pública)
    match /professional_profiles/{professionalId} {
      allow read: if true;
      allow write: if request.auth != null && request.auth.uid == professionalId;
    }
    
    // Reseñas
    match /reviews/{reviewId} {
      allow read: if true;
      allow create: if request.auth != null;
      allow update, delete: if request.auth != null && 
        request.auth.uid == resource.data.authorId;
    }
  }
}
```

### Dependencias del Módulo

```kotlin
dependencies {
    implementation(project(":core"))
    implementation(project(":authentication"))
    implementation(project(":mapservice"))
    
    // UI Framework
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.navigation:navigation-compose:2.8.7")
    
    // Image Handling
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // Firebase
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.2")
    implementation("com.google.firebase:firebase-storage-ktx:21.0.1")
    
    // Architecture
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    
    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.55")
    ksp("com.google.dagger:hilt-compiler:2.55")
}
```

### Permisos Necesarios

```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
```

## 🏃‍♂️ Cómo Usar el Módulo

### Integración en Navegación

```kotlin
// En el módulo app
import dbl.findpro.features.userprofiles.presentation.navigation.profileNavGraph

NavHost(
    navController = navController,
    startDestination = "profile"
) {
    profileNavGraph(
        navController = navController,
        onNavigateToAuth = { 
            // Navegar a autenticación si no hay sesión
        }
    )
}
```

### Mostrar Perfil de Usuario

```kotlin
@Composable
fun UserProfileScreen(
    userId: String,
    isOwnProfile: Boolean = false
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val profileState by viewModel.profileState.collectAsState()
    
    LaunchedEffect(userId) {
        viewModel.loadProfile(userId)
    }
    
    when (val state = profileState) {
        is ProfileState.Loading -> {
            LoadingScreen()
        }
        is ProfileState.Success -> {
            ProfileContent(
                profile = state.profile,
                isEditable = isOwnProfile,
                onEditClick = { 
                    // Navegar a edición de perfil
                }
            )
        }
        is ProfileState.Error -> {
            ErrorScreen(
                message = state.message,
                onRetry = { viewModel.loadProfile(userId) }
            )
        }
    }
}
```

### Editar Perfil

```kotlin
@Composable
fun EditProfileScreen(
    onProfileUpdated: () -> Unit
) {
    val viewModel: EditProfileViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()
    
    var showImagePicker by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Avatar y selección de imagen
        ProfileImagePicker(
            currentImageUrl = uiState.profileImageUrl,
            onImageSelected = viewModel::updateProfileImage,
            onCameraClick = { showImagePicker = true }
        )
        
        // Formulario de datos personales
        PersonalInfoForm(
            personalInfo = uiState.personalInfo,
            onPersonalInfoChange = viewModel::updatePersonalInfo,
            errors = uiState.validationErrors
        )
        
        // Formulario específico para profesionales
        if (uiState.userType == UserType.PROFESSIONAL) {
            ProfessionalInfoForm(
                professionalInfo = uiState.professionalInfo,
                onProfessionalInfoChange = viewModel::updateProfessionalInfo
            )
        }
        
        // Botón de guardar
        SaveButton(
            isLoading = uiState.isLoading,
            isEnabled = uiState.isValid,
            onClick = { 
                viewModel.saveProfile {
                    onProfileUpdated()
                }
            }
        )
    }
    
    // Selector de imagen
    if (showImagePicker) {
        ImagePickerDialog(
            onImageSelected = { uri ->
                viewModel.uploadProfileImage(uri)
                showImagePicker = false
            },
            onDismiss = { showImagePicker = false }
        )
    }
}
```

## 📱 Características Principales

### Gestión de Perfiles Generales
- ✅ **Perfiles personalizados** para usuarios y profesionales
- ✅ **Carga de imágenes** de perfil y galería
- ✅ **Información personal** completa y editable
- ✅ **Preferencias de usuario** y configuraciones
- ✅ **Historial de actividad** y estadísticas
- ✅ **Privacidad y visibilidad** configurables

### Funcionalidades para Profesionales
- ✅ **Portfolio visual** con imágenes de trabajos
- ✅ **Servicios ofrecidos** con descripciones
- ✅ **Precios y tarifas** configurables
- ✅ **Horarios de disponibilidad**
- ✅ **Ubicación de servicio** y radio de cobertura
- ✅ **Certificaciones y documentos** verificables

### Sistema de Reseñas y Calificaciones
- ✅ **Sistema de rating** de 5 estrellas
- ✅ **Reseñas detalladas** con texto y fotos
- ✅ **Verificación de reseñas** anti-spam
- ✅ **Respuestas del profesional** a reseñas
- ✅ **Estadísticas de calificación** promedio
- ✅ **Filtros y ordenamiento** de reseñas

### Configuraciones y Privacidad
- ✅ **Configuración de notificaciones**
- ✅ **Privacidad de datos** personales
- ✅ **Visibilidad del perfil** pública/privada
- ✅ **Gestión de contactos** bloqueados
- ✅ **Exportación de datos** personales
- ✅ **Eliminación de cuenta** segura

## 🔧 Configuración de Desarrollo

### ViewModel Implementation

```kotlin
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _profileState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val profileState = _profileState.asStateFlow()
    
    fun loadProfile(userId: String) {
        viewModelScope.launch {
            try {
                _profileState.value = ProfileState.Loading
                
                val profile = if (userId == authRepository.getCurrentUserId()) {
                    // Cargar perfil propio con datos completos
                    profileRepository.getOwnProfile()
                } else {
                    // Cargar perfil público de otro usuario
                    profileRepository.getPublicProfile(userId)
                }
                
                _profileState.value = ProfileState.Success(profile)
                
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error(
                    e.message ?: "Error al cargar perfil"
                )
            }
        }
    }
    
    fun updateProfile(profileUpdate: ProfileUpdate) {
        viewModelScope.launch {
            try {
                _profileState.value = ProfileState.Loading
                
                val updatedProfile = profileRepository.updateProfile(profileUpdate)
                _profileState.value = ProfileState.Success(updatedProfile)
                
            } catch (e: Exception) {
                _profileState.value = ProfileState.Error(
                    e.message ?: "Error al actualizar perfil"
                )
            }
        }
    }
}
```

### Repository Pattern

```kotlin
@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val profileDao: ProfileDao
) : ProfileRepository {
    
    override suspend fun getProfile(userId: String): Profile {
        return try {
            // Intentar obtener de cache local primero
            profileDao.getProfile(userId)?.let { cachedProfile ->
                // Verificar si el cache es reciente
                if (cachedProfile.lastUpdated > System.currentTimeMillis() - CACHE_TIMEOUT) {
                    return cachedProfile.toDomainModel()
                }
            }
            
            // Obtener de Firestore
            val document = firestore.collection("profiles")
                .document(userId)
                .get()
                .await()
                
            val profile = document.toObject<ProfileDto>()?.toDomainModel()
                ?: throw Exception("Perfil no encontrado")
            
            // Actualizar cache local
            profileDao.insertProfile(profile.toEntity())
            
            profile
            
        } catch (e: Exception) {
            throw Exception("Error al obtener perfil: ${e.message}")
        }
    }
    
    override suspend fun uploadProfileImage(uri: Uri): String {
        return suspendCoroutine { continuation ->
            val fileName = "profile_images/${UUID.randomUUID()}.jpg"
            val imageRef = storage.reference.child(fileName)
            
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    imageRef.downloadUrl
                        .addOnSuccessListener { downloadUrl ->
                            continuation.resume(downloadUrl.toString())
                        }
                        .addOnFailureListener { exception ->
                            continuation.resumeWithException(exception)
                        }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}
```

### Validación de Formularios

```kotlin
class ProfileFormValidator {
    
    fun validatePersonalInfo(personalInfo: PersonalInfo): ValidationResult {
        val errors = mutableListOf<ValidationError>()
        
        // Validar nombre
        if (personalInfo.firstName.isBlank()) {
            errors.add(ValidationError.FIRST_NAME_REQUIRED)
        } else if (personalInfo.firstName.length < 2) {
            errors.add(ValidationError.FIRST_NAME_TOO_SHORT)
        }
        
        // Validar apellido
        if (personalInfo.lastName.isBlank()) {
            errors.add(ValidationError.LAST_NAME_REQUIRED)
        }
        
        // Validar teléfono
        if (personalInfo.phone.isNotBlank() && !isValidPhoneNumber(personalInfo.phone)) {
            errors.add(ValidationError.INVALID_PHONE_NUMBER)
        }
        
        // Validar fecha de nacimiento
        if (personalInfo.birthDate != null && isUnderAge(personalInfo.birthDate)) {
            errors.add(ValidationError.UNDER_AGE)
        }
        
        return ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors
        )
    }
    
    fun validateProfessionalInfo(professionalInfo: ProfessionalInfo): ValidationResult {
        val errors = mutableListOf<ValidationError>()
        
        // Validar servicios
        if (professionalInfo.services.isEmpty()) {
            errors.add(ValidationError.SERVICES_REQUIRED)
        }
        
        // Validar descripción
        if (professionalInfo.description.length < 50) {
            errors.add(ValidationError.DESCRIPTION_TOO_SHORT)
        }
        
        // Validar precios
        professionalInfo.services.forEach { service ->
            if (service.price <= 0) {
                errors.add(ValidationError.INVALID_PRICE)
            }
        }
        
        return ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors
        )
    }
}
```

## 🎨 Componentes UI Avanzados

### Portfolio Gallery

```kotlin
@Composable
fun PortfolioGallery(
    images: List<PortfolioImage>,
    onImageClick: (PortfolioImage) -> Unit,
    onAddImage: () -> Unit,
    isEditable: Boolean = false
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Botón para agregar imagen (solo si es editable)
        if (isEditable) {
            item {
                AddImageCard(
                    onClick = onAddImage,
                    modifier = Modifier.aspectRatio(1f)
                )
            }
        }
        
        // Galería de imágenes
        items(images) { image ->
            PortfolioImageCard(
                image = image,
                onClick = { onImageClick(image) },
                isEditable = isEditable,
                modifier = Modifier.aspectRatio(1f)
            )
        }
    }
}

@Composable
fun PortfolioImageCard(
    image: PortfolioImage,
    onClick: () -> Unit,
    isEditable: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            AsyncImage(
                model = image.url,
                contentDescription = image.description,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Overlay con información
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            )
                        )
                    )
            )
            
            // Descripción en la parte inferior
            Text(
                text = image.description,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Botón de editar (solo si es editable)
            if (isEditable) {
                IconButton(
                    onClick = { /* abrir editor */ },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
```

### Rating and Reviews Component

```kotlin
@Composable
fun ReviewsSection(
    reviews: List<Review>,
    averageRating: Float,
    totalReviews: Int,
    onWriteReview: () -> Unit,
    canWriteReview: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Header con estadísticas
        ReviewsHeader(
            averageRating = averageRating,
            totalReviews = totalReviews,
            onWriteReview = if (canWriteReview) onWriteReview else null
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Lista de reseñas
        LazyColumn {
            items(reviews) { review ->
                ReviewCard(
                    review = review,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ReviewCard(
    review: Review,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header con avatar, nombre y rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = review.authorAvatar,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = review.authorName,
                        style = MaterialTheme.typography.titleSmall
                    )
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RatingStars(
                            rating = review.rating,
                            size = 16.dp
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = formatDate(review.date),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Contenido de la reseña
            Text(
                text = review.comment,
                style = MaterialTheme.typography.bodyMedium
            )
            
            // Imágenes de la reseña (si las hay)
            if (review.images.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(review.images) { imageUrl ->
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}
```

## 🧪 Testing

### Tests Unitarios

```kotlin
@Test
fun `updateProfile should save profile successfully`() = runTest {
    // Given
    val profileUpdate = ProfileUpdate(
        firstName = "Juan",
        lastName = "Pérez",
        phone = "+1234567890"
    )
    
    // When
    val result = profileRepository.updateProfile(profileUpdate)
    
    // Then
    assertEquals("Juan", result.firstName)
    assertEquals("Pérez", result.lastName)
    assertEquals("+1234567890", result.phone)
}

@Test
fun `validatePersonalInfo should return errors for invalid data`() {
    // Given
    val personalInfo = PersonalInfo(
        firstName = "", // Inválido: vacío
        lastName = "Pérez",
        phone = "invalid-phone" // Inválido: formato
    )
    
    // When
    val result = ProfileFormValidator().validatePersonalInfo(personalInfo)
    
    // Then
    assertFalse(result.isValid)
    assertTrue(result.errors.contains(ValidationError.FIRST_NAME_REQUIRED))
    assertTrue(result.errors.contains(ValidationError.INVALID_PHONE_NUMBER))
}
```

### Tests de UI

```kotlin
@Test
fun testProfileScreenDisplaysUserInfo() {
    val mockProfile = createMockProfile()
    
    composeTestRule.setContent {
        UserProfileScreen(
            userId = "test-user-id",
            isOwnProfile = true
        )
    }
    
    composeTestRule
        .onNodeWithText(mockProfile.firstName)
        .assertIsDisplayed()
        
    composeTestRule
        .onNodeWithText("Editar Perfil")
        .assertIsDisplayed()
}
```

## 🔄 Sincronización y Cache

### Estrategia de Datos
```kotlin
class ProfileSyncManager @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val profileDao: ProfileDao
) {
    
    suspend fun syncProfile(userId: String) {
        try {
            // Obtener datos remotos
            val remoteProfile = getRemoteProfile(userId)
            
            // Actualizar cache local
            profileDao.insertProfile(remoteProfile.toEntity())
            
            // Notificar cambios
            _profileUpdates.emit(remoteProfile)
            
        } catch (e: Exception) {
            Timber.e(e, "Error syncing profile for user $userId")
        }
    }
    
    fun observeProfileChanges(userId: String): Flow<Profile> {
        return firestore.collection("profiles")
            .document(userId)
            .snapshots()
            .map { snapshot ->
                snapshot.toObject<ProfileDto>()?.toDomainModel()
                    ?: throw Exception("Profile not found")
            }
            .catch { error ->
                Timber.e(error, "Error observing profile changes")
                // Fallback a datos locales
                emit(profileDao.getProfile(userId)?.toDomainModel()
                    ?: throw Exception("No local profile available"))
            }
    }
}
```

## 🤝 Integración con Otros Módulos

### Dependencias y Comunicación
- **core**: Utilidades base y componentes UI
- **authentication**: Estado de autenticación y datos de usuario
- **mapservice**: Ubicación de profesionales y servicios

### APIs Expuestas
- **ProfileRepository**: Gestión de perfiles
- **ReviewRepository**: Sistema de reseñas
- **PortfolioRepository**: Gestión de portfolios

## 📊 Analytics y Métricas

### Eventos Tracked
- Completitud de perfil
- Interacciones con portfolio
- Ratings y reseñas recibidos
- Tiempo en pantallas de perfil
- Conversiones de visualización a contacto

## 🔐 Privacidad y Seguridad

### Medidas Implementadas
- **Validación de imágenes** antes de subida
- **Filtros de contenido** inapropiado
- **Control de visibilidad** granular
- **Encriptación** de datos sensibles
- **Anonimización** de datos eliminados