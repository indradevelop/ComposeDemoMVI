package indra.composedemo.features.products.data.remote

import indra.composedemo.features.products.domain.model.Product
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProductRemoteDataSource(private val httpClient: HttpClient, private val baseUrl: String){
    suspend fun getProducts(): List<Product> {
        return httpClient.get(baseUrl+"/products").body()
    }
}