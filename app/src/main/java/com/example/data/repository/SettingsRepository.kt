package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("affiliate_settings", Context.MODE_PRIVATE)

    private fun resolveDefaultApiKey(): String {
        return try {
            val field = BuildConfig::class.java.getField("GEMINI_API_KEY")
            val key = field.get(null) as? String ?: ""
            if (key == "MY_GEMINI_API_KEY") "" else key
        } catch (_: Exception) {
            ""
        }
    }

    private val _apiKey = MutableStateFlow<String>(
        prefs.getString("custom_api_key", null) ?: resolveDefaultApiKey()
    )
    val apiKey: StateFlow<String> = _apiKey.asStateFlow()

    private val _provider = MutableStateFlow<String>(
        prefs.getString("selected_provider", "Gemini & Free AI Logic") ?: "Gemini & Free AI Logic"
    )
    val provider: StateFlow<String> = _provider.asStateFlow()

    private val _resolution = MutableStateFlow<String>(
        prefs.getString("video_resolution", "1080x1920 (HD 9:16)") ?: "1080x1920 (HD 9:16)"
    )
    val resolution: StateFlow<String> = _resolution.asStateFlow()

    private val _autoSaveToGallery = MutableStateFlow<Boolean>(
        prefs.getBoolean("auto_save_gallery", true)
    )
    val autoSaveToGallery: StateFlow<Boolean> = _autoSaveToGallery.asStateFlow()

    fun updateApiKey(newKey: String) {
        prefs.edit().putString("custom_api_key", newKey).apply()
        _apiKey.value = newKey
    }

    fun updateProvider(newProvider: String) {
        prefs.edit().putString("selected_provider", newProvider).apply()
        _provider.value = newProvider
    }

    fun updateResolution(newRes: String) {
        prefs.edit().putString("video_resolution", newRes).apply()
        _resolution.value = newRes
    }

    fun toggleAutoSave(enabled: Boolean) {
        prefs.edit().putBoolean("auto_save_gallery", enabled).apply()
        _autoSaveToGallery.value = enabled
    }
}
