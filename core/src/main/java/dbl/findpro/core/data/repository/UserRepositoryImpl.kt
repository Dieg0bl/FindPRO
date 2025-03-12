package dbl.findpro.core.data.repository

import dbl.findpro.core.data.mappers.toDomainUser
import dbl.findpro.core.data.mappers.toMap
import dbl.findpro.core.data.services.IFirestoreService
import dbl.findpro.core.domain.model.userGroupsAndProfiles.ProfileType
import dbl.findpro.core.domain.model.userGroupsAndProfiles.User
import dbl.findpro.core.domain.repository.IUserRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firestoreService: IFirestoreService
) : IUserRepository {

    private val usersCollection = "users"

    override suspend fun getUserById(userId: String): User? {
        val result = firestoreService.getDocument(usersCollection, userId)
        return result.getOrNull()?.toDomainUser()
    }


    override suspend fun createUser(user: User): Boolean {
        val result = firestoreService.saveData(usersCollection, user.userId, user.toMap())
        return if (result.isSuccess) {
            Timber.i("✅ Usuario creado en Firestore: ${user.userId}")
            true
        } else {
            Timber.e("❌ Error al crear usuario en Firestore: ${result.exceptionOrNull()?.message}")
            false
        }
    }

    override suspend fun deleteUserById(userId: String): Boolean {
        val result = firestoreService.deleteDocument(usersCollection, userId)
        return if (result.isSuccess) {
            Timber.i("✅ Usuario eliminado de Firestore: $userId")
            true
        } else {
            Timber.e("❌ Error al eliminar usuario de Firestore: ${result.exceptionOrNull()?.message}")
            false
        }
    }

    override suspend fun updateUser(user: User): Boolean {
        val result = firestoreService.updateData(usersCollection, user.userId, user.toMap())
        return if (result.isSuccess) {
            Timber.i("✅ Usuario actualizado en Firestore: ${user.userId}")
            true
        } else {
            Timber.e("❌ Error al actualizar usuario en Firestore: ${result.exceptionOrNull()?.message}")
            false
        }
    }

    override suspend fun setProfileType(userId: String, profileType: ProfileType): Boolean {
        val result = firestoreService.updateData(usersCollection, userId, mapOf("profileTypeInUse" to profileType.name))
        return if (result.isSuccess) {
            Timber.i("✅ ProfileType establecido para usuario: $userId")
            true
        } else {
            Timber.e("❌ Error al establecer ProfileType para el usuario: $userId")
            false
        }
    }

    override suspend fun updateProfileTypeInUse(userId: String, type: ProfileType) {
        val result = firestoreService.updateData(usersCollection, userId, mapOf("profileTypeInUse" to type.name))
        if (result.isSuccess) {
            Timber.i("✅ ProfileType actualizado para usuario: $userId")
        } else {
            Timber.e("❌ Error al actualizar ProfileType para el usuario: $userId")
        }
    }
}
