package com.shafiq.data.network

import com.shafiq.data.model.request.AddToCartRequest
import com.shafiq.data.model.response.CartResponse
import com.shafiq.data.model.response.CategoriesListResponse
import com.shafiq.data.model.response.ProductListResponse
import com.shafiq.domain.model.CartItemModel
import com.shafiq.domain.model.CartModel
import com.shafiq.domain.model.CategoriesListModel
import com.shafiq.domain.model.ProductListModel
import com.shafiq.domain.model.request.AddCartRequestModel
import com.shafiq.domain.network.NetworkService
import com.shafiq.domain.network.ResultWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import java.io.IOException

class NetworkServiceImpl(val client: HttpClient) : NetworkService {

    private val baseUrl = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com/v2"

    override suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel> {

        val url = if (category != null) "$baseUrl/products/category/$category" else "$baseUrl/products"

        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { dataModule: ProductListResponse ->
                dataModule.toProductList()
            }
        )
    }

    override suspend fun getCategories(): ResultWrapper<CategoriesListModel> {
        val url = "$baseUrl/categories"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { categories: CategoriesListResponse ->
                categories.toCategoriesList()
            }
        )
    }

    override suspend fun addProductToCart(request: AddCartRequestModel): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/1"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Post,
            body = AddToCartRequest.fromCartRequestModel(request),
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            }
        )
    }

    override suspend fun getCart(): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/1"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Get,
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            }
        )
    }

    override suspend fun updateQuantity(cartItemModel: CartItemModel): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/1/${cartItemModel.id}"
        return makeWebRequest(
            url = url,
            method = HttpMethod.Put,
            body = AddToCartRequest(
                productId = cartItemModel.productId,
                quantity = cartItemModel.quantity
            ),
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            }
        )
    }

    suspend inline fun <reified T, R> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
    ): ResultWrapper<R> {
        return try {
            val response = client.request(url) {
                this.method = method
                // Apply query parameters
                url {
                    this.parameters.appendAll(Parameters.build {
                        parameters.forEach { (key, value) ->
                            append(key, value)
                        }
                    })
                }
                // Apply headers
                headers.forEach { (key, value) ->
                    header(key, value)
                }
                // Set body for POST, PUT, etc.
                if (body != null) {
                    setBody(body)
                }
                // Set content type
                contentType(ContentType.Application.Json)
            }.body<T>()
            val result: R = mapper?.invoke(response) ?: response as R
            ResultWrapper.Success(result)
        } catch (e: ClientRequestException) {
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            ResultWrapper.Failure(e)
        } catch (e: IOException) {
            ResultWrapper.Failure(e)
        } catch (e: Exception) {
            ResultWrapper.Failure(e)
        }
    }
}