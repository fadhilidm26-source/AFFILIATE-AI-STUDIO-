package com.example.data.repository

import com.example.data.local.CharacterDao
import com.example.data.model.InfluencerCharacter
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class CharacterRepository(private val characterDao: CharacterDao) {
    val allCharacters: Flow<List<InfluencerCharacter>> = characterDao.getAllCharacters()

    suspend fun getCharacterById(id: String): InfluencerCharacter? = characterDao.getCharacterById(id)

    suspend fun insertCharacter(character: InfluencerCharacter) = characterDao.insertCharacter(character)

    suspend fun deleteCharacter(id: String) = characterDao.deleteCharacter(id)

    suspend fun checkAndSeedDefaults() {
        if (characterDao.getCharacterCount() == 0) {
            val defaults = listOf(
                InfluencerCharacter(
                    id = "char_anya",
                    name = "Anya Putri",
                    persona = "Skincare & Beauty Enthusiast",
                    category = "Skincare",
                    avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=500&auto=format&fit=crop&q=80",
                    stylePrompt = "Indonesian female, 23 yo, porcelain smooth skin, shoulder-length glossy dark brown hair, warm friendly smile, natural clean-girl makeup, white studio background with soft warm glow",
                    skinTone = "Kuning Langsat Alami",
                    hairStyle = "Bob Sebahu Berkilau",
                    bodyType = "Ramping Proporsional",
                    isCustom = false,
                    totalVideosMade = 14
                ),
                InfluencerCharacter(
                    id = "char_rian",
                    name = "Rian Pratama",
                    persona = "Gadget & Tech Reviewer",
                    category = "Gadget",
                    avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=500&auto=format&fit=crop&q=80",
                    stylePrompt = "Indonesian male, 25 yo, sharp jawline, short side fade hair, clean tech casual black t-shirt, expressive hand gestures, modern desk setup with RGB ambient lighting",
                    skinTone = "Sawo Matang Bersih",
                    hairStyle = "Clean Fade Modern",
                    bodyType = "Tinggi Atletis",
                    isCustom = false,
                    totalVideosMade = 9
                ),
                InfluencerCharacter(
                    id = "char_nadia",
                    name = "Nadia Wijaya",
                    persona = "Fashion & OOTD Stylist",
                    category = "Fashion",
                    avatarUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=500&auto=format&fit=crop&q=80",
                    stylePrompt = "Indonesian female, 22 yo, chic aesthetic, long wavy dark hair, confident model pose, minimalist neutral apartment background with natural daylight",
                    skinTone = "Cerah Natural",
                    hairStyle = "Wavy Panjang Belah Tengah",
                    bodyType = "Model Runway Slender",
                    isCustom = false,
                    totalVideosMade = 21
                ),
                InfluencerCharacter(
                    id = "char_bima",
                    name = "Bima Sakti",
                    persona = "Sport & Lifestyle Creator",
                    category = "Lifestyle",
                    avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=500&auto=format&fit=crop&q=80",
                    stylePrompt = "Indonesian male, 26 yo, athletic build, undercut hair, energetic smile, sporty outfit, bright gym or urban outdoor setting",
                    skinTone = "Sawo Matang Eksotis",
                    hairStyle = "Athletic Undercut",
                    bodyType = "Berotot Bugar",
                    isCustom = false,
                    totalVideosMade = 6
                ),
                InfluencerCharacter(
                    id = "char_siti",
                    name = "Siti Zahra",
                    persona = "Modest Wear & Hijab Chic",
                    category = "Fashion",
                    avatarUrl = "https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?w=500&auto=format&fit=crop&q=80",
                    stylePrompt = "Indonesian female, 24 yo, wearing elegant pastel pashmina hijab, soft glowing skin, graceful modest pose, pastel aesthetic studio background",
                    skinTone = "Kuning Langsat Lembut",
                    hairStyle = "Hijab Pashmina Rapi",
                    bodyType = "Anggun & Proporsional",
                    isCustom = false,
                    totalVideosMade = 17
                )
            )
            characterDao.insertAll(defaults)
        }
    }

    suspend fun createCustomCharacter(
        name: String,
        persona: String,
        category: String,
        avatarUrl: String,
        stylePrompt: String,
        skinTone: String,
        hairStyle: String,
        bodyType: String
    ): InfluencerCharacter {
        val character = InfluencerCharacter(
            id = "custom_" + UUID.randomUUID().toString().take(8),
            name = name,
            persona = persona,
            category = category,
            avatarUrl = avatarUrl.ifBlank { "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=500&auto=format&fit=crop&q=80" },
            stylePrompt = stylePrompt,
            skinTone = skinTone,
            hairStyle = hairStyle,
            bodyType = bodyType,
            isCustom = true,
            totalVideosMade = 0
        )
        characterDao.insertCharacter(character)
        return character
    }
}
