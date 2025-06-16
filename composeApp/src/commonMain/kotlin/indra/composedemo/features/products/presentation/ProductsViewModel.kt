package indra.composedemo.features.products.presentation

import indra.composedemo.features.products.contract.ProductsEffect
import indra.composedemo.features.products.contract.ProductsEvent
import indra.composedemo.features.products.contract.ProductsState
import indra.composedemo.features.products.data.remote.ProductRemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class ProductsViewModel(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val coroutineScope: CoroutineScope? = null // For testing flexibility
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProductsEffect>()
    val effect = _effect.asSharedFlow()

    init {
        handleEvent(ProductsEvent.LoadProducts)
        /*viewModelScope.launch {
            effect.collect { effect ->
                println("ViewModel: Received effect: $effect")
            }
        }*/
    }

    fun handleEvent(event: ProductsEvent) {
        when (event) {
            ProductsEvent.LoadProducts -> loadProducts()
            is ProductsEvent.ProductClicked -> {
                viewModelScope.launch {
                    _effect.emit(ProductsEffect.NavigateToProductDetail(event.product.id))
                    _effect.emit(ProductsEffect.ShowSnackbar("Product: ${event.product.title} clicked!"))
                }
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val products = productRemoteDataSource.getProducts()
                _state.value = _state.value.copy(products = products, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Failed to load products: ${e.message}",
                    isLoading = false
                )
                _effect.emit(ProductsEffect.ShowSnackbar("Error: Failed to load products."))
            }
        }
    }
}