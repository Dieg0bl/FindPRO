# FindPRO

**Aplicación Android de marketplace de servicios profesionales** que conecta particulares con profesionales de forma geolocalizada.

## 🎯 Propósito
FindPRO es una plataforma móvil que facilita la conexión entre usuarios que necesitan servicios (particulares) y profesionales que los ofrecen. La aplicación utiliza geolocalización para encontrar profesionales cercanos y gestiona perfiles diferenciados para ambos tipos de usuario.

## 🛠️ Tecnologías Principales
- **Android Nativo** - Kotlin con Jetpack Compose
- **Arquitectura Limpia** - Separación en módulos (Clean Architecture)
- **Firebase** - Autenticación, Firestore, Analytics y Crashlytics
- **Mapbox** - Integración de mapas y servicios de geolocalización
- **Hilt/Dagger** - Inyección de dependencias
- **Coroutines** - Programación asíncrona
- **Navigation Compose** - Navegación moderna de Android

## 🏗️ Arquitectura Modular
```
├── app/           # Módulo principal de la aplicación
├── core/          # Funcionalidades compartidas
├── authentication/# Gestión de autenticación
├── userprofiles/  # Perfiles de particulares y profesionales
└── mapservice/    # Servicios de mapas y geolocalización
```

## 📱 Características
- ✅ Sistema de autenticación con Firebase
- ✅ Perfiles diferenciados (Particulares vs Profesionales)
- ✅ Integración de mapas para servicios geolocalizados
- ✅ Arquitectura escalable y mantenible
- 🚧 En desarrollo activo

## 🎯 Target
- **SDK Mínimo**: Android 8.0 (API 26)
- **SDK Objetivo**: Android 14 (API 35)
- **Arquitectura**: Clean Architecture con MVVM
