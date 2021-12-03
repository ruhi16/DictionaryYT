package com.plcoding.dictionary.feature_dictionary.doman.model

data class WordInfo(
    val meanings: List<Meaning>,
    val origin: String,
    val phonetic: String,
    val word: String
)
