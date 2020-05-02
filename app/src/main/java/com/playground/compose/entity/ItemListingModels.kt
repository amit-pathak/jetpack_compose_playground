package com.playground.compose.entity

data class Item(val title: String, val description : String, var image : Int)

data class ItemListingError(val title : String,val description: String)