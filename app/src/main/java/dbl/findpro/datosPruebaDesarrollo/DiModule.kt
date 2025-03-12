package dbl.findpro.datosPruebaDesarrollo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dbl.findpro.core.data.services.IFirestoreService
import dbl.findpro.core.domain.repository.ICategoryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun provideFirebaseTestDataUploader(
        IFirestoreService: IFirestoreService,
        ICategoryRepository: ICategoryRepository
    ): FirebaseDataUploader {
        return FirebaseDataUploader(
            IFirestoreService,
            ICategoryRepository
        )
    }
}
