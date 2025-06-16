package indra.composedemo.di

import indra.composedemo.features.products.data.remote.ProductRemoteDataSource
import indra.composedemo.features.products.presentation.ProductsViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }

    single {
        ProductsViewModel(
            ProductRemoteDataSource(
                httpClient = get(),
                baseUrl = "https://fakestoreapi.com"
            )
        )
    }
}