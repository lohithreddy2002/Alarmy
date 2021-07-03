package com.example.gowow.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gowow.RemViewModel
import com.example.gowow.Remrepository

class RemFactory(private val repo : Remrepository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RemViewModel(repo) as T
    }
}