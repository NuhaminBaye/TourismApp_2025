package com.example.tourismapp.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourismapp.data.LanguageManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LanguageViewModel(private val languageManager: LanguageManager) : ViewModel() {
    private val _currentLanguage = MutableStateFlow(LanguageManager.DEFAULT_LANGUAGE)
    val currentLanguage: StateFlow<String> = _currentLanguage

    init {
        viewModelScope.launch {
            languageManager.language.collect { language ->
                _currentLanguage.value = language
            }
        }
    }

    fun setLanguage(language: String) {
        viewModelScope.launch {
            languageManager.setLanguage(language)
        }
    }
} 