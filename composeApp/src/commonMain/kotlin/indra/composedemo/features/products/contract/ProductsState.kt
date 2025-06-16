package indra.composedemo.features.products.contract

import indra.composedemo.features.products.domain.model.Product

data class ProductsState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)