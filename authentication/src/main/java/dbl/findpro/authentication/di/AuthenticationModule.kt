package dbl.findpro.authentication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dbl.findpro.authentication.data.repository.AuthenticationRepositoryImpl
import dbl.findpro.authentication.domain.usecase.*
import dbl.findpro.core.data.services.IFirebaseAuthService
import dbl.findpro.core.domain.repository.IAuthenticationRepository
import dbl.findpro.core.domain.repository.IUserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        firebaseAuthService: IFirebaseAuthService
    ): IAuthenticationRepository {
        return AuthenticationRepositoryImpl(firebaseAuthService)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authenticationRepository: IAuthenticationRepository): LoginUseCase =
        LoginUseCase(authenticationRepository)

    @Provides
    @Singleton
    fun provideForgotPasswordUseCase(authenticationRepository: IAuthenticationRepository): ForgotPasswordUseCase =
        ForgotPasswordUseCase(authenticationRepository)

    @Provides
    @Singleton
    fun provideRegisterUseCase(
        authenticationRepository: IAuthenticationRepository,
        userRepository: IUserRepository
    ): RegisterUseCase =
        RegisterUseCase(
            authenticationRepository,
            userRepository = userRepository
        )

    @Provides
    @Singleton
    fun provideGoogleSignInUseCase(authenticationRepository: IAuthenticationRepository): GoogleSignInUseCase =
        GoogleSignInUseCase(authenticationRepository)

    @Provides
    @Singleton
    fun provideGoogleSignUpUseCase(authenticationRepository: IAuthenticationRepository): GoogleSignUpUseCase =
        GoogleSignUpUseCase(authenticationRepository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(authenticationRepository: IAuthenticationRepository): LogoutUseCase =
        LogoutUseCase(authenticationRepository)

    @Provides
    @Singleton
    fun provideGetGoogleIdTokenUseCase(authenticationRepository: IAuthenticationRepository): GetGoogleIdTokenUseCase =
        GetGoogleIdTokenUseCase(authenticationRepository)

    @Provides
    @Singleton
    fun provideValidateUserUseCase(authenticationRepository: IAuthenticationRepository): ValidateUserUseCase =
        ValidateUserUseCase(authenticationRepository)
}
