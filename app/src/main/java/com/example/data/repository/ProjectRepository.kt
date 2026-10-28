package com.example.data.repository

import com.example.data.local.ProjectDao
import com.example.data.model.AffiliateProject
import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao) {
    val allProjects: Flow<List<AffiliateProject>> = projectDao.getAllProjects()

    fun getProjectsByCategory(category: String): Flow<List<AffiliateProject>> {
        return if (category == "Semua") {
            projectDao.getAllProjects()
        } else {
            projectDao.getProjectsByCategory(category)
        }
    }

    suspend fun getProjectById(id: String): AffiliateProject? = projectDao.getProjectById(id)

    suspend fun insertProject(project: AffiliateProject) = projectDao.insertProject(project)

    suspend fun updateProject(project: AffiliateProject) = projectDao.updateProject(project)

    suspend fun deleteProject(id: String) = projectDao.deleteProject(id)

    suspend fun checkAndSeedDefaults() {
        if (projectDao.getProjectCount() == 0) {
            val samples = listOf(
                AffiliateProject(
                    id = "proj_1",
                    title = "Glow Serum Niacinamide 10%",
                    productCategory = "Skincare",
                    productName = "Aura Glow Brightening Serum 30ml",
                    productImageUrl = "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=500&auto=format&fit=crop&q=80",
                    characterId = "char_anya",
                    characterName = "Anya Putri",
                    characterAvatar = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=500&auto=format&fit=crop&q=80",
                    videoMode = "Review UGC",
                    promptText = "Anya memegang botol serum di depan kamera, meneteskan pipet ke punggung tangan, menunjukkan tekstur cepat meresap dengan ekspresi kagum alami khas kreator TikTok",
                    cameraMovement = "Smooth Zoom In",
                    videoStyle = "TikTok UGC Viral",
                    durationSeconds = 15,
                    aspectRatio = "9:16",
                    status = "COMPLETED",
                    statusMessage = "Render selesai - Siap upload",
                    videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-girl-in-neon-sign-1232-large.mp4",
                    thumbnailUrl = "https://images.unsplash.com/photo-1556228720-195a672e8a03?w=500&auto=format&fit=crop&q=80",
                    viewsEstimate = "48.2K",
                    tags = "#skincareroutine #racuntiktok #serumviral"
                ),
                AffiliateProject(
                    id = "proj_2",
                    title = "Oversized Linen Shirt Pastel",
                    productCategory = "Fashion",
                    productName = "Kemeja Linen Oversized Sage Green",
                    productImageUrl = "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=500&auto=format&fit=crop&q=80",
                    characterId = "char_nadia",
                    characterName = "Nadia Wijaya",
                    characterAvatar = "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=500&auto=format&fit=crop&q=80",
                    videoMode = "Try-On Outfit",
                    promptText = "Nadia melakukan gerakan spin try-on anggun memperlihatkan jahitan rapi kemeja linen, dipadukan celana kulot putih dan tas selempang chic",
                    cameraMovement = "Dynamic Handheld",
                    videoStyle = "Aesthetic Cinematic",
                    durationSeconds = 10,
                    aspectRatio = "9:16",
                    status = "COMPLETED",
                    statusMessage = "Render selesai - Rasio 9:16",
                    videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-young-woman-posing-for-the-camera-41584-large.mp4",
                    thumbnailUrl = "https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?w=500&auto=format&fit=crop&q=80",
                    viewsEstimate = "112.5K",
                    tags = "#ootdinspo #outfitideas #shopeehaul"
                ),
                AffiliateProject(
                    id = "proj_3",
                    title = "ANC Wireless Earbuds Pro",
                    productCategory = "Gadget",
                    productName = "SoundPulse Neo True Wireless Earbuds",
                    productImageUrl = "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=500&auto=format&fit=crop&q=80",
                    characterId = "char_rian",
                    characterName = "Rian Pratama",
                    characterAvatar = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=500&auto=format&fit=crop&q=80",
                    videoMode = "Unboxing",
                    promptText = "Rian membuka magnetic case earbuds, memasang salah satu earbud ke telinga, mengangguk mengikuti bass drop dengan ekspresi terpukau",
                    cameraMovement = "Slow Pan",
                    videoStyle = "Clean Studio",
                    durationSeconds = 15,
                    aspectRatio = "9:16",
                    status = "COMPLETED",
                    statusMessage = "Audio & Video tersinkronisasi",
                    videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-man-dancing-under-the-rain-43091-large.mp4",
                    thumbnailUrl = "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&auto=format&fit=crop&q=80",
                    viewsEstimate = "76.8K",
                    tags = "#gadgetreview #racunshopee #twsbass"
                ),
                AffiliateProject(
                    id = "proj_4",
                    title = "Trending Affiliate Dance Challenge",
                    productCategory = "Fashion",
                    productName = "Koleksi Denim Jacket Vintage",
                    productImageUrl = "https://images.unsplash.com/photo-1576995853123-5a10305d93c0?w=500&auto=format&fit=crop&q=80",
                    characterId = "char_bima",
                    characterName = "Bima Sakti",
                    characterAvatar = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=500&auto=format&fit=crop&q=80",
                    videoMode = "Dance Reference",
                    promptText = "Bima menirukan koreografi viral TikTok 3 pose transisi cepat menunjuk link keranjang kuning di pojok kiri bawah",
                    cameraMovement = "Dynamic Handheld",
                    videoStyle = "TikTok UGC Viral",
                    durationSeconds = 10,
                    aspectRatio = "9:16",
                    status = "COMPLETED",
                    statusMessage = "Koreografi tersinkronisasi",
                    videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-portrait-of-a-fashion-woman-with-silver-makeup-39875-large.mp4",
                    thumbnailUrl = "https://images.unsplash.com/photo-1492562080023-ab3db95bfbce?w=500&auto=format&fit=crop&q=80",
                    viewsEstimate = "204.1K",
                    tags = "#keranjangkuning #dancechallenge #affiliateviral"
                )
            )
            projectDao.insertAll(samples)
        }
    }
}
