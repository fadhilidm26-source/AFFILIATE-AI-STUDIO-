package com.example.data.model

data class DanceTemplate(
    val id: String,
    val title: String,
    val tempo: String, // Slow & Aesthetic, Energetic 128 BPM, Catchy Jingle
    val difficulty: String, // Mudah, Sedang, Viral
    val durationSeconds: Int,
    val previewUrl: String,
    val description: String,
    val tags: List<String>
)

data class OutfitReplacementTask(
    val id: String,
    val characterId: String,
    val characterName: String,
    val originalImageUrl: String,
    val newOutfitImageUrl: String,
    val outfitName: String,
    val resultImageUrl: String,
    val status: String, // IDLE, PROCESSING, SUCCESS, FAILED
    val timestamp: Long = System.currentTimeMillis()
)

data class ProductIsolatorTask(
    val id: String,
    val productName: String,
    val originalImageUrl: String,
    val isolatedImageUrl: String,
    val status: String,
    val timestamp: Long = System.currentTimeMillis()
)
