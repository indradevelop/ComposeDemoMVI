package indra.composedemo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import indra.composedemo.di.networkModule
import org.jetbrains.compose.ui.tooling.preview.Preview

import indra.composedemo.features.products.presentation.ProductsScreen
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(networkModule)
    }) {
        MaterialTheme {
            ProductsScreen()
        }
    }
}