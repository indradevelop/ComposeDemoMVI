package indra.composedemo.features.products.contract

sealed class ProductsEffect {
    data class ShowSnackbar(val message: String) : ProductsEffect()
    data class NavigateToProductDetail(val productId: Int) : ProductsEffect()
}