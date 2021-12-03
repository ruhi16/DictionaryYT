package com.plcoding.dictionary.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.dictionary.feature_dictionary.doman.model.Meaning
import com.plcoding.dictionary.feature_dictionary.doman.model.WordInfo

@Entity
data class WordInfoEntity(
    @PrimaryKey
    val id: Int? = null,
    val word: String,
    val phonetic: String,
    val origin: String,
    val meanings: List<Meaning>,

){
    fun toWordIInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            word = word,
            origin = origin,
            phonetic = phonetic
        )
    }



}
