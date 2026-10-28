package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class InfluencerCharacter(
    @PrimaryKey val id: String,
    val name: String,
    val persona: String,
    val category: String, // Skincare, Fashion, Gadget, Lifestyle, etc.
    val avatarUrl: String,
    val stylePrompt: String,
    val skinTone: String,
    val hairStyle: String,
    val bodyType: String,
    val isCustom: Boolean = false,
    val totalVideosMade: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
