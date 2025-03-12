package dbl.findpro.userprofiles.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dbl.findpro.core.data.repository.ParticularProfileRepositoryImpl
import dbl.findpro.core.data.repository.ProfessionalProfileRepositoryImpl
import dbl.findpro.core.data.services.IFirestoreService
import dbl.findpro.core.domain.repository.ICategoryRepository
import dbl.findpro.core.domain.repository.IParticularProfileRepository
import dbl.findpro.core.domain.repository.IProfessionalProfileRepository
import dbl.findpro.userprofiles.domain.usecase.SaveParticularProfileUseCase
import dbl.findpro.userprofiles.domain.usecase.SaveProfessionalProfileUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserProfilesModule {

    @Provides
    @Singleton
    fun provideParticularProfileRepository(
        firestoreService: IFirestoreService
    ): IParticularProfileRepository = ParticularProfileRepositoryImpl(firestoreService)

    @Provides
    @Singleton
    fun provideProfessionalProfileRepository(
        firestoreService: IFirestoreService,
        categoryRepository: ICategoryRepository
    ): IProfessionalProfileRepository = ProfessionalProfileRepositoryImpl(
        firestoreService,
        categoryRepository
    )


    @Provides
    @Singleton
    fun provideSaveParticularProfileUseCase(
        repository: IParticularProfileRepository
    ): SaveParticularProfileUseCase = SaveParticularProfileUseCase(repository)

    @Provides
    @Singleton
    fun provideSaveProfessionalProfileUseCase(
        repository: IProfessionalProfileRepository
    ): SaveProfessionalProfileUseCase = SaveProfessionalProfileUseCase(repository)
}
