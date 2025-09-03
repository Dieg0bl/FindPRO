# FindPRO

FindPRO es una aplicación Android moderna que conecta a particulares con profesionales, facilitando la búsqueda y contratación de servicios. La aplicación utiliza una arquitectura modular limpia con las últimas tecnologías de Android.

## 📋 Propósito del Proyecto

FindPRO tiene como objetivo crear una plataforma intuitiva y eficiente que permita:
- **Para usuarios**: Encontrar profesionales calificados en su área
- **Para profesionales**: Promocionar sus servicios y conectar con clientes potenciales
- **Para ambos**: Facilitar la comunicación y gestión de servicios a través de una interfaz moderna

## 🚀 Tecnologías Utilizadas

### Frontend
- **Kotlin** - Lenguaje principal de desarrollo
- **Jetpack Compose** - Framework de UI moderna y declarativa
- **Material 3** - Sistema de diseño de Google
- **Navigation Compose** - Navegación entre pantallas

### Backend y Servicios
- **Firebase Auth** - Autenticación de usuarios
- **Firebase Firestore** - Base de datos NoSQL en tiempo real
- **Firebase Analytics** - Análisis de uso
- **Firebase Crashlytics** - Monitoreo de errores

### Mapas y Localización
- **Mapbox SDK** - Servicios de mapas avanzados
- **Google Play Services Location** - Servicios de ubicación

### Arquitectura y Patrones
- **Hilt** - Inyección de dependencias
- **MVVM** - Patrón de arquitectura
- **Room** - Base de datos local
- **Retrofit** - Cliente HTTP para APIs
- **Coroutines** - Programación asíncrona

### Testing y Calidad
- **JUnit** - Testing unitario
- **Espresso** - Testing de UI
- **Timber** - Logging estructurado

## 📁 Estructura del Proyecto

El proyecto sigue una arquitectura modular con los siguientes módulos:

```
FindPRO/
├── app/              # Módulo principal de la aplicación
├── core/             # Funcionalidades compartidas y utilidades
├── authentication/   # Módulo de autenticación
├── mapservice/       # Servicios de mapas y ubicación
└── userprofiles/     # Gestión de perfiles de usuario
```

## 🛠️ Instalación y Configuración

### Prerrequisitos
- **Android Studio** Arctic Fox o superior
- **JDK 17** o superior
- **Android SDK** API 26 (Android 8.0) o superior
- **Cuenta de Firebase** para servicios backend
- **Token de Mapbox** para servicios de mapas

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/Dieg0bl/FindPRO.git
   cd FindPRO
   ```

2. **Configurar Firebase**
   - Crear un proyecto en [Firebase Console](https://console.firebase.google.com/)
   - Descargar el archivo `google-services.json`
   - Colocarlo en `app/src/google-services.json`

3. **Configurar Mapbox**
   - Obtener un token de acceso desde [Mapbox](https://account.mapbox.com/access-tokens/)
   - El token ya está configurado en `settings.gradle.kts` (reemplazar si es necesario)

4. **Construir el proyecto**
   ```bash
   ./gradlew build
   ```

5. **Ejecutar la aplicación**
   ```bash
   ./gradlew installDebug
   ```

## 🏃‍♂️ Cómo Ejecutar

### Desde Android Studio
1. Abrir el proyecto en Android Studio
2. Esperar a que se sincronicen las dependencias
3. Seleccionar un dispositivo/emulador
4. Hacer clic en "Run" o presionar `Ctrl+R`

### Desde línea de comandos
```bash
# Compilar y ejecutar en modo debug
./gradlew installDebug

# Ejecutar tests
./gradlew test

# Generar APK de release
./gradlew assembleRelease
```

## 📱 Características Principales

- ✅ **Autenticación segura** con Firebase Auth
- ✅ **Perfiles de usuario** personalizables
- ✅ **Mapas interactivos** con Mapbox
- ✅ **Búsqueda de profesionales** por ubicación
- ✅ **Interfaz moderna** con Material 3
- ✅ **Arquitectura escalable** y modular

## 🤝 Contribución

Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crear una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 📞 Contacto

**Desarrollador**: Dieg0bl  
**GitHub**: [@Dieg0bl](https://github.com/Dieg0bl)
