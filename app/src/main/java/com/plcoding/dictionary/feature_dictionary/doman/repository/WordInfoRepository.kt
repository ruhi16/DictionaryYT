package com.plcoding.dictionary.feature_dictionary.doman.repository

import com.plcoding.dictionary.core.util.Resource
import com.plcoding.dictionary.feature_dictionary.doman.model.WordInfo
import kotlinx.coroutines.flow.Flow



interface WordInfoRepository {
    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}