# Core Module - FindPRO

## 📋 Propósito del Módulo

El módulo **core** es la base fundamental de la aplicación FindPRO. Contiene todas las funcionalidades compartidas, utilidades comunes y componentes base que son utilizados por otros módulos de la aplicación. Este módulo promueve la reutilización de código y mantiene la consistencia a través de toda la aplicación.

## 🚀 Tecnologías Utilizadas

### UI y Componentes
- **Jetpack Compose** - Framework de UI declarativa
- **Material 3** - Sistema de diseño y componentes
- **Compose Foundation** - Componentes base de Compose
- **Navigation Compose** - Navegación entre pantallas

### Persistencia de Datos
- **Room Database** - Base de datos local SQLite
- **DataStore Preferences** - Almacenamiento de preferencias
- **Firebase Firestore** - Base de datos NoSQL en tiempo real
- **Firebase Auth** - Autenticación y gestión de usuarios

### Networking y APIs
- **Retrofit** - Cliente HTTP para APIs REST
- **OkHttp** - Cliente HTTP robusto y eficiente
- **Gson** - Serialización/deserialización JSON
- **Logging Interceptor** - Interceptor para logs de red

### Mapas y Ubicación
- **Mapbox Maps** - SDK de mapas avanzado
- **Mapbox Compose Extension** - Integración con Jetpack Compose

### Arquitectura y Utilidades
- **Hilt** - Inyección de dependencias
- **Kotlin Coroutines** - Programación asíncrona
- **Kotlin Serialization** - Serialización de datos
- **Timber** - Sistema de logging estructurado

### Firebase Services
- **Firebase Remote Config** - Configuración remota
- **Firebase Messaging** - Notificaciones push

## 📁 Estructura del Módulo

```
core/
├── src/
│   ├── main/
│   │   └── java/dbl/findpro/core/
│   │       ├── data/
│   │       │   ├── database/           # Room database
│   │       │   ├── network/            # Retrofit y networking
│   │       │   ├── repository/         # Repositorios de datos
│   │       │   └── datastore/          # DataStore preferences
│   │       ├── domain/
│   │       │   ├── model/              # Modelos de dominio
│   │       │   ├── repository/         # Interfaces de repositorio
│   │       │   └── usecase/            # Casos de uso
│   │       ├── ui/
│   │       │   ├── components/         # Componentes UI reutilizables
│   │       │   ├── theme/              # Tema y estilos
│   │       │   └── utils/              # Utilidades de UI
│   │       ├── di/                     # Módulos de Hilt
│   │       └── utils/                  # Utilidades generales
│   ├── test/                           # Tests unitarios
│   └── androidTest/                    # Tests de instrumentación
├── build.gradle.kts                    # Configuración de build
└── proguard-rules.pro                  # Reglas de ofuscación
```

## 🛠️ Instalación y Configuración

### Prerrequisitos
- Módulo base que no requiere configuración especial
- Todas las dependencias están gestionadas por Gradle
- Configuración de Firebase requerida para funcionalidades completas

### Dependencias Principales

```kotlin
dependencies {
    // UI Framework
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.material3:material3:1.3.1")
    
    // Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    
    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    
    // Firebase
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.2")
    implementation("com.google.firebase:firebase-auth-ktx:23.2.0")
    
    // Maps
    implementation("com.mapbox.maps:android:11.9.2")
    implementation("com.mapbox.extension:maps-compose:11.9.0")
    
    // DI
    implementation("com.google.dagger:hilt-android:2.55")
}
```

## 🏃‍♂️ Cómo Usar el Módulo

### En Otros Módulos
```kotlin
// En build.gradle.kts de otros módulos
dependencies {
    implementation(project(":core"))
}
```

### Ejemplos de Uso

#### 1. Usando Componentes UI
```kotlin
import dbl.findpro.core.ui.components.CommonButton
import dbl.findpro.core.ui.theme.FindProTheme

@Composable
fun MyScreen() {
    FindProTheme {
        CommonButton(
            text = "Click me",
            onClick = { /* acción */ }
        )
    }
}
```

#### 2. Usando Repositorios
```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    fun loadUser(id: String) {
        viewModelScope.launch {
            val user = userRepository.getUser(id)
            // procesar usuario
        }
    }
}
```

#### 3. Usando Utilidades
```kotlin
import dbl.findpro.core.utils.NetworkUtils
import dbl.findpro.core.utils.DateUtils

// Verificar conexión de red
if (NetworkUtils.isNetworkAvailable(context)) {
    // realizar operación de red
}

// Formatear fecha
val formattedDate = DateUtils.formatDate(date)
```

## 📱 Características Principales

### Componentes UI Reutilizables
- ✅ **Botones personalizados** con estilos consistentes
- ✅ **Campos de texto** con validación
- ✅ **Cards y contenedores** estilizados
- ✅ **Indicadores de carga** y estados
- ✅ **Dialogs y bottomsheets** comunes

### Gestión de Datos
- ✅ **Base de datos Room** configurada
- ✅ **Repositorios** para acceso a datos
- ✅ **Cache** y sincronización offline
- ✅ **Preferences** seguras con DataStore

### Networking
- ✅ **Cliente HTTP** configurado con Retrofit
- ✅ **Interceptors** para autenticación y logging
- ✅ **Manejo de errores** consistente
- ✅ **Cache de red** inteligente

### Utilidades
- ✅ **Extensions** de Kotlin útiles
- ✅ **Helpers** para fechas y formato
- ✅ **Validadores** comunes
- ✅ **Constantes** globales

## 🔧 Configuración de Desarrollo

### Room Database
```kotlin
@Database(
    entities = [User::class, Professional::class],
    version = 1,
    exportSchema = false
)
abstract class FindProDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun professionalDao(): ProfessionalDao
}
```

### Hilt Modules
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FindProDatabase {
        return Room.databaseBuilder(
            context,
            FindProDatabase::class.java,
            "findpro_database"
        ).build()
    }
}
```

## 🔄 Patrones de Arquitectura

### Repository Pattern
- Abstrae el acceso a datos
- Centraliza la lógica de datos
- Facilita testing y mantenimiento

### Use Cases
- Encapsula lógica de negocio
- Reutilizable entre ViewModels
- Fácil testing unitario

### Dependency Injection
- Gestión automática de dependencias
- Facilita testing con mocks
- Mejor organización del código

## 🤝 Integración con Otros Módulos

El módulo core es utilizado por:
- **app**: Configuración base y utilidades
- **authentication**: Componentes UI y repositorios
- **mapservice**: Utilidades de red y base de datos
- **userprofiles**: Componentes UI y gestión de datos

## 📊 Testing

```bash
# Ejecutar tests unitarios
./gradlew :core:test

# Ejecutar tests de instrumentación
./gradlew :core:connectedAndroidTest

# Ver cobertura de tests
./gradlew :core:testDebugUnitTestCoverage
```

## 📞 APIs y Interfaces

Este módulo expone APIs limpias y bien documentadas que pueden ser utilizadas por otros módulos. Consulte el código fuente para documentación detallada de cada interfaz.