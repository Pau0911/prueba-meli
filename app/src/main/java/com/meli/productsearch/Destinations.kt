package com.meli.productsearch

sealed class Destinations(val route: String) {

    object SearchView : com.meli.productsearch.Destinations("searchView")
    object ResultsView : com.meli.productsearch.Destinations("resultsView/{searchText}") {
        fun createRoute(searchText: String) = "resultsView/$searchText"
    }

    object DetailsView : com.meli.productsearch.Destinations("detailsView/{id}") {
        fun createRoute(id: String) = "detailsView/$id"
    }

}