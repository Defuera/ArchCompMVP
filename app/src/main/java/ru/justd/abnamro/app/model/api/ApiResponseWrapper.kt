package ru.justd.abnamro.app.model.api

import ru.justd.abnamro.app.model.Venue

data class ApiResponseWrapper (
        val meta : Meta,
        val response : Response
)

data class Meta (
        val code : Int
)

data class Response(
        val venues : List<Venue>
)