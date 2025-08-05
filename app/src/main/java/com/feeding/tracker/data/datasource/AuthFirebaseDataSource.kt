package com.feeding.tracker.data.datasource

import com.feeding.tracker.data.mappers.toDomain
import com.feeding.tracker.domain.model.UserDomain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthFirebaseDataSource
    @Inject
    constructor(
        private val auth: FirebaseAuth,
    ) {
        val getCurrentUser: UserDomain? get() = auth.currentUser?.toDomain()

        fun login(
            email: String,
            password: String,
        ): Flow<Result<UserDomain>> =
            flow {
                try {
                    // Llama a la función de Firebase
                    val authResult =
                        auth
                            .createUserWithEmailAndPassword(email, password)
                            .await() // <-- Aquí es donde convertimos la 'Task' en una corrutina

                    // Si el resultado es exitoso, emitimos el usuario
                    val user = authResult.user
                    if (user != null) {
                        emit(Result.success(user.toDomain()))
                    } else {
                        emit(Result.failure(Exception("User not found after sign up")))
                    }
                } catch (e: Exception) {
                    // Si hay una excepción (ej: autenticación fallida), emitimos el error
                    emit(Result.failure(e))
                }
            }

        fun signUp(
            email: String,
            password: String,
        ): Flow<Result<UserDomain>> =
            flow {
                try {
                    val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                    val user = authResult.user

                    if (user != null) {
                        emit(Result.success(user.toDomain()))
                    } else {
                        emit(Result.failure(Exception("User not found after sign up")))
                    }
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }

//    suspend fun signInWithGoogle(credential: AuthCredential): AuthResult<FirebaseUser> =
//        withContext(Dispatchers.IO) {
//            try {
//                val result = auth.signInWithCredential(credential).await()
//                result.user?.let {
//                    AuthResult.Success(it)
//                } ?: AuthResult.Error("Google sign-in failed")
//            } catch (e: Exception) {
//                AuthResult.Error(e.message ?: "Google sign-in failed")
//            }
//        }

        fun logout() {
            auth.signOut()
        }

        fun getCurrentUserId(): String? = auth.currentUser?.uid
    }
