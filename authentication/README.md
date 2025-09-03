# Authentication Module - FindPRO

## 📋 Propósito del Módulo

El módulo **authentication** maneja todo el sistema de autenticación y autorización de la aplicación FindPRO. Proporciona una experiencia completa de registro, inicio de sesión, gestión de perfiles y seguridad de usuarios, tanto para particulares como para profesionales.

## 🚀 Tecnologías Utilizadas

### Autenticación y Seguridad
- **Firebase Auth** - Sistema de autenticación robusto y seguro
- **Firebase Firestore** - Almacenamiento de datos de usuario
- **Google Play Services** - Integración con servicios de Google

### UI y Experiencia de Usuario
- **Jetpack Compose** - Framework de UI declarativa
- **Material 3** - Sistema de diseño moderno
- **Material Icons Extended** - Iconografía extendida
- **Compose Navigation** - Navegación entre pantallas
- **Coil Compose** - Carga y manejo de imágenes

### Arquitectura y Patrones
- **Hilt** - Inyección de dependencias
- **MVVM Pattern** - Arquitectura Model-View-ViewModel
- **Android Architecture Components** - Lifecycle, ViewModel, LiveData
- **Kotlin Coroutines** - Programación asíncrona

### Herramientas de Desarrollo
- **Timber** - Sistema de logging estructurado
- **KSP** - Kotlin Symbol Processing para anotaciones

## 📁 Estructura del Módulo

```
authentication/
├── src/
│   ├── main/
│   │   └── java/dbl/findpro/features/authentication/
│   │       ├── data/
│   │       │   ├── repository/        # Implementación de repositorios
│   │       │   ├── datasource/        # Fuentes de datos (Firebase)
│   │       │   └── model/             # Modelos de datos
│   │       ├── domain/
│   │       │   ├── model/             # Entidades de dominio
│   │       │   ├── repository/        # Interfaces de repositorio
│   │       │   └── usecase/           # Casos de uso de autenticación
│   │       ├── presentation/
│   │       │   ├── ui/
│   │       │   │   ├── login/         # Pantalla de inicio de sesión
│   │       │   │   ├── register/      # Pantalla de registro
│   │       │   │   ├── forgot/        # Recuperación de contraseña
│   │       │   │   └── profile/       # Gestión de perfil
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
- Configuración de Firebase Auth habilitada
- Módulo core instalado y configurado
- Configuración de Google Play Services

### Dependencias del Módulo

```kotlin
dependencies {
    implementation(project(":core"))
    implementation(project(":mapservice"))
    
    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth-ktx:23.2.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.2")
    
    // UI Components
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    
    // Architecture Components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    
    // Image Loading
    implementation("io.coil-kt:coil-compose:2.5.0")
}
```

### Configuración de Firebase

1. **Habilitar Firebase Auth**
   ```
   - Ir a Firebase Console
   - Seleccionar proyecto FindPRO
   - Habilitar Authentication
   - Configurar métodos de sign-in (Email/Password, Google, etc.)
   ```

2. **Configurar Firestore Rules**
   ```javascript
   rules_version = '2';
   service cloud.firestore {
     match /databases/{database}/documents {
       match /users/{userId} {
         allow read, write: if request.auth != null && request.auth.uid == userId;
       }
     }
   }
   ```

## 🏃‍♂️ Cómo Usar el Módulo

### Integración en Navegación

```kotlin
// En el módulo app
import dbl.findpro.features.authentication.presentation.navigation.authNavGraph

NavHost(
    navController = navController,
    startDestination = "auth"
) {
    authNavGraph(
        navController = navController,
        onAuthSuccess = { /* navegar a home */ }
    )
}
```

### Verificación de Autenticación

```kotlin
@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    val isUserAuthenticated = authRepository.isUserAuthenticated()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )
}
```

### Casos de Uso Principales

#### 1. Iniciar Sesión
```kotlin
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel()
    
    LaunchedEffect(viewModel.loginState) {
        if (viewModel.loginState.value is LoginState.Success) {
            onLoginSuccess()
        }
    }
    
    // UI Implementation
}
```

#### 2. Registro de Usuario
```kotlin
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    userType: UserType // PARTICULAR o PROFESIONAL
) {
    val viewModel: RegisterViewModel = hiltViewModel()
    
    // Formulario de registro adaptado al tipo de usuario
}
```

#### 3. Recuperación de Contraseña
```kotlin
@Composable
fun ForgotPasswordScreen(
    onPasswordResetSent: () -> Unit
) {
    val viewModel: ForgotPasswordViewModel = hiltViewModel()
    
    // UI para envío de email de recuperación
}
```

## 📱 Características Principales

### Funcionalidades de Autenticación
- ✅ **Registro de usuarios** (particulares y profesionales)
- ✅ **Inicio de sesión** con email/contraseña
- ✅ **Recuperación de contraseña** por email
- ✅ **Autenticación persistente** (remember me)
- ✅ **Validación de formularios** en tiempo real
- ✅ **Manejo de errores** user-friendly

### Gestión de Perfiles
- ✅ **Creación de perfiles** diferenciados
- ✅ **Actualización de datos** personales
- ✅ **Carga de imágenes** de perfil
- ✅ **Verificación de profesionales** (badge system)
- ✅ **Configuración de privacidad**

### Seguridad
- ✅ **Validación robusta** de inputs
- ✅ **Encriptación de datos** sensibles
- ✅ **Sesiones seguras** con Firebase Auth
- ✅ **Protección contra ataques** comunes
- ✅ **Logout automático** por inactividad

### UI/UX
- ✅ **Diseño responsive** para tablets y móviles
- ✅ **Tema Material 3** consistente
- ✅ **Animaciones fluidas** entre pantallas
- ✅ **Feedback visual** para acciones del usuario
- ✅ **Accesibilidad** mejorada

## 🔧 Configuración de Desarrollo

### ViewModels Pattern

```kotlin
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val result = authRepository.login(email, password)
                _loginState.value = LoginState.Success(result)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}
```

### Repository Implementation

```kotlin
@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {
    
    override suspend fun login(email: String, password: String): User {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    // Obtener datos de usuario de Firestore
                    getUserData(result.user?.uid ?: "") { user ->
                        continuation.resume(user)
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}
```

## 🔄 Estados y Navegación

### Estados de Autenticación
```kotlin
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}
```

### Flujo de Navegación
```
Splash → Login/Register → Profile Setup → Home
    ↑         ↓
    ←─── Forgot Password
```

## 🧪 Testing

### Tests Unitarios
```kotlin
@Test
fun `login with valid credentials should return success`() = runTest {
    // Given
    val email = "test@example.com"
    val password = "password123"
    
    // When
    val result = authRepository.login(email, password)
    
    // Then
    assert(result is AuthResult.Success)
}
```

### Tests de UI
```kotlin
@Test
fun testLoginScreenDisplaysCorrectly() {
    composeTestRule.setContent {
        LoginScreen(
            onLoginSuccess = {},
            onNavigateToRegister = {}
        )
    }
    
    composeTestRule
        .onNodeWithText("Iniciar Sesión")
        .assertIsDisplayed()
}
```

## 🤝 Integración con Otros Módulos

### Dependencias
- **core**: Utilidades comunes y componentes base
- **mapservice**: Para profesionales que necesitan ubicación

### Datos Compartidos
- **User Model**: Compartido con userprofiles
- **Authentication State**: Utilizado por app y otros módulos

## 📊 Métricas y Analytics

El módulo incluye tracking de eventos importantes:
- Registros exitosos
- Inicios de sesión
- Errores de autenticación
- Tiempo en pantallas de auth

## 🔐 Consideraciones de Seguridad

- **Validación del lado cliente y servidor**
- **Encriptación de datos en tránsito**
- **Tokens de sesión seguros**
- **Protección contra ataques de fuerza bruta**
- **Validación de emails** antes del registro completo

## 📞 Soporte y Troubleshooting

### Errores Comunes
1. **Firebase not configured**: Verificar google-services.json
2. **Network errors**: Implementar retry logic
3. **Weak passwords**: Mostrar requirements claros
4. **Email already exists**: Manejar duplicados graciosamente