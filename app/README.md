# App Module - FindPRO

## 📋 Propósito del Módulo

El módulo **app** es el punto de entrada principal de la aplicación FindPRO. Este módulo contiene:
- La actividad principal de la aplicación
- La configuración de navegación global
- La integración de todos los módulos de funcionalidades
- La configuración de Hilt para inyección de dependencias
- Los archivos de configuración de Firebase

## 🚀 Tecnologías Utilizadas

### UI y Framework
- **Jetpack Compose** - Framework de UI declarativa
- **Material 3** - Sistema de diseño moderno
- **Navigation Compose** - Navegación entre pantallas
- **Compose BOM** - Gestión de versiones de Compose

### Backend y Servicios
- **Firebase Auth** - Autenticación de usuarios
- **Firebase Firestore** - Base de datos en tiempo real
- **Firebase Analytics** - Análisis de comportamiento
- **Firebase Crashlytics** - Monitoreo de errores
- **Firebase Remote Config** - Configuración remota

### Arquitectura
- **Hilt** - Inyección de dependencias
- **Kotlin Coroutines** - Programación asíncrona
- **Android Architecture Components** - MVVM pattern

### Herramientas de Desarrollo
- **Timber** - Logging estructurado
- **Kotlin Symbol Processing (KSP)** - Procesamiento de anotaciones
- **Mapbox** - Integración de servicios de mapas

## 📁 Estructura del Módulo

```
app/
├── src/
│   ├── main/
│   │   ├── java/dbl/findpro/
│   │   │   ├── MainActivity.kt          # Actividad principal
│   │   │   ├── FindProApplication.kt    # Clase de aplicación
│   │   │   ├── navigation/              # Navegación global
│   │   │   └── di/                      # Módulos de Hilt
│   │   ├── AndroidManifest.xml          # Manifest de la aplicación
│   │   └── res/                         # Recursos (layouts, strings, etc.)
│   ├── test/                            # Tests unitarios
│   └── androidTest/                     # Tests de instrumentación
├── google-services.json                 # Configuración Firebase
├── build.gradle.kts                     # Configuración de build
└── proguard-rules.pro                   # Reglas de ofuscación
```

## 🛠️ Instalación y Configuración

### Prerrequisitos
- Android Studio Arctic Fox o superior
- JDK 17
- Android SDK API 26+
- Configuración de Firebase completada
- Token de Mapbox configurado

### Configuración del Módulo

1. **Firebase Setup**
   ```
   - El archivo google-services.json debe estar en app/src/
   - Configurar Firebase Auth, Firestore, Analytics y Crashlytics
   ```

2. **Dependencias del Módulo**
   ```kotlin
   dependencies {
       // Módulos de la aplicación
       implementation(project(":core"))
       implementation(project(":authentication"))
       implementation(project(":mapservice"))
       implementation(project(":userprofiles"))
       
       // Firebase
       implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
       // ... otras dependencias
   }
   ```

## 🏃‍♂️ Cómo Ejecutar

### Compilar y Ejecutar
```bash
# Desde la raíz del proyecto
./gradlew :app:assembleDebug

# Instalar en dispositivo
./gradlew :app:installDebug

# Ejecutar tests
./gradlew :app:test
./gradlew :app:connectedAndroidTest
```

### Configuración de Build

El módulo está configurado con:
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 35 (Android 14+)
- **Compile SDK**: 35
- **Java Version**: 17

### Build Types
- **Debug**: Incluye logs de debug, sin ofuscación
- **Release**: Optimizado con ProGuard, logs deshabilitados

## 📱 Características Principales

- ✅ **Punto de entrada único** para toda la aplicación
- ✅ **Navegación global** entre módulos
- ✅ **Inyección de dependencias** con Hilt
- ✅ **Integración Firebase** completa
- ✅ **Configuración de build** optimizada
- ✅ **Soporte multi-módulo** bien estructurado

## 🔧 Configuración de Desarrollo

### Variables de Entorno
```kotlin
// En build.gradle.kts
buildTypes {
    debug {
        buildConfigField("boolean", "DEBUG", "true")
        isMinifyEnabled = false
    }
    release {
        buildConfigField("boolean", "DEBUG", "false")
        isMinifyEnabled = true
    }
}
```

### ProGuard Configuration
Las reglas de ProGuard están configuradas en `proguard-rules.pro` para optimizar el APK de release.

## 🤝 Integración con Otros Módulos

Este módulo integra todos los demás módulos del proyecto:
- **core**: Funcionalidades compartidas
- **authentication**: Sistema de autenticación
- **mapservice**: Servicios de mapas
- **userprofiles**: Gestión de perfiles

## 📞 Dependencias Externas

Consulte el archivo `build.gradle.kts` para la lista completa de dependencias y sus versiones.