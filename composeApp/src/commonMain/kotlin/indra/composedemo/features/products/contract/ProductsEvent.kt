package indra.composedemo.features.products.contract

import indra.composedemo.features.products.domain.model.Product

sealed class ProductsEvent {
    object LoadProducts : ProductsEvent()
    data class ProductClicked(val product: Product) : ProductsEvent()
}