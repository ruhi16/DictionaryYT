package com.plcoding.dictionary.feature_dictionary.data.repository

import com.plcoding.dictionary.core.util.Resource
import com.plcoding.dictionary.feature_dictionary.data.local.WordInfoDao
import com.plcoding.dictionary.feature_dictionary.data.remote.DictionaryApi
import com.plcoding.dictionary.feature_dictionary.doman.model.WordInfo
import com.plcoding.dictionary.feature_dictionary.doman.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
    ): WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        //before start loading, emit loading status, display the progress bar
        emit(Resource.Loading())

        //read the current word from db, emit the cashed one
        val wordInfos = dao.getWordInfos(word).map{ it.toWordIInfo() }
        emit(Resource.Loading(data = wordInfos))

        try {
            //initiate the api call, get data, delete old & insert new content
            val remoteWordInfos = api.getWordInfo(word)
            dao.deletewordInfos(remoteWordInfos.map { it.word })
            dao.inserWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

        }catch (e: HttpException){
            //any error in api call
            emit(Resource.Error(wordInfos, "Oops Sometng wrong"))
        }catch (e: IOException){
            //internet connection error
            emit(Resource.Error(wordInfos, "Could not reach server"))
        }


        //get data from db, send to our ui
        val newWordInfos = dao.getWordInfos(word).map{ it.toWordIInfo()}
        emit(Resource.Success(newWordInfos))
    }
}