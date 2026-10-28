package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class AffiliateProject(
    @PrimaryKey val id: String,
    val title: String,
    val productCategory: String, // Fashion, Skincare, Kosmetik, Gadget, Lainnya
    val productName: String,
    val productImageUrl: String,
    val characterId: String,
    val characterName: String,
    val characterAvatar: String,
    val videoMode: String, // Try-On Outfit, Review UGC, Unboxing, Demonstrasi Produk, Dance Reference, Mirror Selfie
    val promptText: String,
    val cameraMovement: String, // Dynamic Handheld, Smooth Zoom In, Orbit 360, Slow Pan, Static Close-Up
    val videoStyle: String, // TikTok UGC Viral, Aesthetic Cinematic, Clean Studio, Casual Daily
    val durationSeconds: Int, // 5, 10, 15
    val aspectRatio: String = "9:16",
    val status: String, // COMPLETED, PROCESSING, QUEUED, FAILED, DRAFT
    val statusMessage: String = "",
    val videoUrl: String = "",
    val thumbnailUrl: String = "",
    val viewsEstimate: String = "0",
    val tags: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
