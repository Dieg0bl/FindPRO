package dbl.findpro.core.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dbl.findpro.core.data.repository.CategoryRepositoryImpl
import dbl.findpro.core.data.repository.LocationRepositoryImpl
import dbl.findpro.core.data.repository.OfferRepositoryImpl
import dbl.findpro.core.data.repository.UserRepositoryImpl
import dbl.findpro.core.data.services.FirebaseAuthServiceImpl
import dbl.findpro.core.data.services.IFirestoreService
import dbl.findpro.core.data.services.FirestoreServiceImpl
import dbl.findpro.core.data.services.IFirebaseAuthService
import dbl.findpro.core.domain.repository.ICategoryRepository
import dbl.findpro.core.domain.repository.IGeocodingRepository
import dbl.findpro.core.domain.repository.ILocationRepository
import dbl.findpro.core.domain.repository.IOfferRepository
import dbl.findpro.core.domain.repository.IProfessionalProfileRepository
import dbl.findpro.core.domain.repository.IUserRepository
import dbl.findpro.core.domain.usecase.DeleteOfferUseCase
import dbl.findpro.core.domain.usecase.DeleteProfessionalUseCase
import dbl.findpro.core.domain.usecase.FilterOffersByCategoryUseCase
import dbl.findpro.core.domain.usecase.FilterProfessionalsByCategoryUseCase
import dbl.findpro.core.domain.usecase.GetOffersUseCase
import dbl.findpro.core.domain.usecase.GetProfessionalWithOffersUseCase
import dbl.findpro.core.domain.usecase.GetProfessionalsUseCase
import dbl.findpro.core.domain.usecase.GetUserLocationUseCase
import dbl.findpro.core.domain.usecase.SaveOfferUseCase
import dbl.findpro.core.domain.usecase.SaveProfessionalUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig = Firebase.remoteConfig

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthService(
        firebaseAuth: FirebaseAuth,
    ): IFirebaseAuthService {
        return FirebaseAuthServiceImpl(
            firebaseAuth,
        )
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirestoreService(firestore: FirebaseFirestore): IFirestoreService {
        return FirestoreServiceImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        firestore: IFirestoreService
    ): IUserRepository {
        return UserRepositoryImpl(
            firestore,
        )
    }


    @Provides
    @Singleton
    fun provideCategoryRepository(): ICategoryRepository = CategoryRepositoryImpl()

    @Provides
    @Singleton
    fun provideOfferRepository(
        firestoreService: IFirestoreService,
        categoryRepository: ICategoryRepository
    ): IOfferRepository {
        return OfferRepositoryImpl(firestoreService, categoryRepository)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        @ApplicationContext context: Context,
        geocodingRepository: IGeocodingRepository
    ): ILocationRepository {
        return LocationRepositoryImpl(context, geocodingRepository)
    }


    @Provides
    @Singleton
    fun provideGetUserLocationUseCase(
        locationRepository: ILocationRepository
    ): GetUserLocationUseCase {
        return GetUserLocationUseCase(locationRepository)
    }

    @Provides
    @Singleton
    fun provideGetProfessionalsUseCase(repository: IProfessionalProfileRepository): GetProfessionalsUseCase {
        return GetProfessionalsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveProfessionalUseCase(repository: IProfessionalProfileRepository): SaveProfessionalUseCase {
        return SaveProfessionalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteProfessionalUseCase(repository: IProfessionalProfileRepository): DeleteProfessionalUseCase {
        return DeleteProfessionalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetOffersUseCase(repository: IOfferRepository): GetOffersUseCase {
        return GetOffersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveOfferUseCase(repository: IOfferRepository): SaveOfferUseCase {
        return SaveOfferUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteOfferUseCase(repository: IOfferRepository): DeleteOfferUseCase {
        return DeleteOfferUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetProfessionalWithOffersUseCase(
        professionalProfileRepository: IProfessionalProfileRepository,
        offerRepository: IOfferRepository
    ): GetProfessionalWithOffersUseCase {
        return GetProfessionalWithOffersUseCase(professionalProfileRepository, offerRepository)
    }

    @Provides
    fun provideFilterProfessionalsByCategoryUseCase(professionalProfileRepository: IProfessionalProfileRepository): FilterProfessionalsByCategoryUseCase =
        FilterProfessionalsByCategoryUseCase(professionalProfileRepository)

    @Provides
    fun provideFilterOffersByCategoryUseCase(offerRepository: IOfferRepository): FilterOffersByCategoryUseCase =
        FilterOffersByCategoryUseCase(offerRepository)

}
