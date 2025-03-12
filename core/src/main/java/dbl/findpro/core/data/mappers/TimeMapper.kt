package dbl.findpro.core.data.mappers

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

// 📌 Convierte una `Instant` a `Long` para Firestore
fun Instant.toFirestoreTimestamp(): Long = this.toEpochMilli()

// 📌 Convierte un `Long` de Firestore a `Instant`
fun Long.toInstantFromFirestore(): Instant = Instant.ofEpochMilli(this)

// 📌 Convierte un `Instant` a `LocalDateTime`
fun Instant.toLocalDateTime(): LocalDateTime = atZone(ZoneId.systemDefault()).toLocalDateTime()

// 📌 Convierte un `LocalDateTime` a `Long`
fun LocalDateTime.toEpochMilli(): Long = atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
