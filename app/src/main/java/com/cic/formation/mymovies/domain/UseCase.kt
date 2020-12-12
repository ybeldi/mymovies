package com.cic.formation.mymovies.domain
import com.cic.formation.mymovies.data.utils.Results
import kotlinx.coroutines.flow.Flow

/**
 * Abstract class that needs to be implemented by all UseCases.
 * <P>: Parameters type
 * <R>: Return type
 *
 * The UseCase class handles the business logic of the app, and helps coders easily understand
 * what the business cases.
 *
 */
abstract class UseCase<in P, R> {
    abstract  operator fun invoke(parameters: P): Flow<Results<R>>
}