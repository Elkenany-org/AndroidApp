package com.elkenany.entities.search

data class SearchResult(
    val result: List<Result>,
)

data class Result(
    val id: Long,
    val name: String,
    val type: String,
)