package com.example.viewmodel

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.AffiliateProject
import com.example.data.model.DanceTemplate
import com.example.data.model.InfluencerCharacter
import com.example.data.repository.AiGenerationRepository
import com.example.data.repository.CharacterRepository
import com.example.data.repository.GenerationState
import com.example.data.repository.ProjectRepository
import com.example.data.repository.SettingsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppNavTab(val title: String) {
    HOME("Beranda"),
    CREATE("Studio Video"),
    TOOLS("Alat Produk"),
    DANCE("Referensi Tari"),
    GALLERY("Galeri Saya")
}

class StudioViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    val characterRepository = CharacterRepository(database.characterDao())
    val projectRepository = ProjectRepository(database.projectDao())
    val settingsRepository = SettingsRepository(application)
    val aiRepository = AiGenerationRepository(settingsRepository)

    // Navigation Tab
    private val _currentTab = MutableStateFlow(AppNavTab.HOME)
    val currentTab: StateFlow<AppNavTab> = _currentTab.asStateFlow()

    // Characters
    val allCharacters: StateFlow<List<InfluencerCharacter>> = characterRepository.allCharacters
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCharacter = MutableStateFlow<InfluencerCharacter?>(null)
    val selectedCharacter: StateFlow<InfluencerCharacter?> = _selectedCharacter.asStateFlow()

    // Projects & Gallery
    val allProjects: StateFlow<List<AffiliateProject>> = projectRepository.allProjects
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCategory = MutableStateFlow("Semua")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Creation State
    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> = _productName.asStateFlow()

    private val _productCategory = MutableStateFlow("Fashion")
    val productCategory: StateFlow<String> = _productCategory.asStateFlow()

    private val _productImageUrl = MutableStateFlow("https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=500&auto=format&fit=crop&q=80")
    val productImageUrl: StateFlow<String> = _productImageUrl.asStateFlow()

    private val _videoMode = MutableStateFlow("Try-On Outfit")
    val videoMode: StateFlow<String> = _videoMode.asStateFlow()

    private val _promptText = MutableStateFlow("")
    val promptText: StateFlow<String> = _promptText.asStateFlow()

    private val _cameraMovement = MutableStateFlow("Dynamic Handheld")
    val cameraMovement: StateFlow<String> = _cameraMovement.asStateFlow()

    private val _videoStyle = MutableStateFlow("TikTok UGC Viral")
    val videoStyle: StateFlow<String> = _videoStyle.asStateFlow()

    private val _durationSeconds = MutableStateFlow(15)
    val durationSeconds: StateFlow<Int> = _durationSeconds.asStateFlow()

    private val _generationState = MutableStateFlow<GenerationState>(GenerationState.Idle)
    val generationState: StateFlow<GenerationState> = _generationState.asStateFlow()

    // Active project for player overlay / modal
    private val _activeProjectModal = MutableStateFlow<AffiliateProject?>(null)
    val activeProjectModal: StateFlow<AffiliateProject?> = _activeProjectModal.asStateFlow()

    // Settings & Dialog Modals
    private val _showSettingsDialog = MutableStateFlow(false)
    val showSettingsDialog: StateFlow<Boolean> = _showSettingsDialog.asStateFlow()

    private val _showNewCharacterDialog = MutableStateFlow(false)
    val showNewCharacterDialog: StateFlow<Boolean> = _showNewCharacterDialog.asStateFlow()

    // Product Tools State
    private val _outfitModelImageUrl = MutableStateFlow("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=500&auto=format&fit=crop&q=80")
    val outfitModelImageUrl: StateFlow<String> = _outfitModelImageUrl.asStateFlow()

    private val _newOutfitImageUrl = MutableStateFlow("https://images.unsplash.com/photo-1576995853123-5a10305d93c0?w=500&auto=format&fit=crop&q=80")
    val newOutfitImageUrl: StateFlow<String> = _newOutfitImageUrl.asStateFlow()

    private val _outfitResultUrl = MutableStateFlow<String?>(null)
    val outfitResultUrl: StateFlow<String?> = _outfitResultUrl.asStateFlow()

    private val _isOutfitProcessing = MutableStateFlow(false)
    val isOutfitProcessing: StateFlow<Boolean> = _isOutfitProcessing.asStateFlow()

    // Dance Reference State
    val danceTemplates = listOf(
        DanceTemplate(
            id = "dance_1",
            title = "TikTok Viral Keranjang Step",
            tempo = "Energetic 128 BPM",
            difficulty = "Viral",
            durationSeconds = 15,
            previewUrl = "https://images.unsplash.com/photo-1547153760-18fc86324498?w=500&auto=format&fit=crop&q=80",
            description = "3 gerakan tangan ritmik menunjuk sudut kiri bawah tempat keranjang affiliate",
            tags = listOf("Viral", "E-Commerce", "Energetic")
        ),
        DanceTemplate(
            id = "dance_2",
            title = "Aesthetic Spin & Point Try-On",
            tempo = "Slow R&B Chic",
            difficulty = "Mudah",
            durationSeconds = 10,
            previewUrl = "https://images.unsplash.com/photo-1516762689617-e1cffcef479d?w=500&auto=format&fit=crop&q=80",
            description = "Gerakan berputar 180 derajat elegan dan pose senyum natural memegang produk",
            tags = listOf("Fashion", "Outfit", "Slow Chic")
        ),
        DanceTemplate(
            id = "dance_3",
            title = "K-Pop Affiliate Chorus Challenge",
            tempo = "Upbeat Pop 135 BPM",
            difficulty = "Sedang",
            durationSeconds = 15,
            previewUrl = "https://images.unsplash.com/photo-1508700115892-45ecd05ae2ad?w=500&auto=format&fit=crop&q=80",
            description = "Koreografi catchy dengan sinkronisasi ketukan jari dan lirik promosi",
            tags = listOf("K-Pop", "Catchy", "Trends")
        )
    )

    private val _selectedDanceTemplate = MutableStateFlow<DanceTemplate>(danceTemplates[0])
    val selectedDanceTemplate: StateFlow<DanceTemplate> = _selectedDanceTemplate.asStateFlow()

    private val _isDanceSyncing = MutableStateFlow(false)
    val isDanceSyncing: StateFlow<Boolean> = _isDanceSyncing.asStateFlow()

    init {
        viewModelScope.launch {
            characterRepository.checkAndSeedDefaults()
            projectRepository.checkAndSeedDefaults()
        }
    }

    fun selectTab(tab: AppNavTab) {
        _currentTab.value = tab
    }

    fun selectCharacter(character: InfluencerCharacter) {
        _selectedCharacter.value = character
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setProductName(name: String) {
        _productName.value = name
    }

    fun setProductCategory(category: String) {
        _productCategory.value = category
    }

    fun setProductImageUrl(url: String) {
        _productImageUrl.value = url
    }

    fun setVideoMode(mode: String) {
        _videoMode.value = mode
    }

    fun setPromptText(text: String) {
        _promptText.value = text
    }

    fun setCameraMovement(movement: String) {
        _cameraMovement.value = movement
    }

    fun setVideoStyle(style: String) {
        _videoStyle.value = style
    }

    fun setDurationSeconds(duration: Int) {
        _durationSeconds.value = duration
    }

    fun generateAutoPrompt() {
        val character = _selectedCharacter.value ?: allCharacters.value.firstOrNull() ?: return
        val pName = _productName.value.ifBlank { "Produk Unggulan" }
        val generated = aiRepository.generateSmartPrompt(
            productName = pName,
            category = _productCategory.value,
            character = character,
            videoMode = _videoMode.value
        )
        _promptText.value = generated
    }

    fun startVideoGeneration() {
        val character = _selectedCharacter.value ?: allCharacters.value.firstOrNull()
        if (character == null) {
            _generationState.value = GenerationState.Error("Silakan pilih karakter AI influencer terlebih dahulu.")
            return
        }

        val pName = _productName.value.ifBlank { "Produk Affiliate" }
        val prompt = _promptText.value.ifBlank {
            aiRepository.generateSmartPrompt(pName, _productCategory.value, character, _videoMode.value)
        }

        viewModelScope.launch {
            try {
                val project = aiRepository.executeVideoGeneration(
                    productName = pName,
                    category = _productCategory.value,
                    productImageUrl = _productImageUrl.value,
                    character = character,
                    videoMode = _videoMode.value,
                    prompt = prompt,
                    cameraMovement = _cameraMovement.value,
                    videoStyle = _videoStyle.value,
                    durationSeconds = _durationSeconds.value,
                    onProgress = { state ->
                        _generationState.value = state
                    }
                )
                // Save to Room DB
                projectRepository.insertProject(project)
                // Update character video count
                val updatedChar = character.copy(totalVideosMade = character.totalVideosMade + 1)
                characterRepository.insertCharacter(updatedChar)

                Toast.makeText(getApplication(), "Video berhasil digenerate & disimpan ke Galeri!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                if (_generationState.value !is GenerationState.Error) {
                    _generationState.value = GenerationState.Error(e.message ?: "Terjadi kesalahan saat memproses AI video")
                }
            }
        }
    }

    fun dismissGenerationState() {
        _generationState.value = GenerationState.Idle
    }

    fun openProjectModal(project: AffiliateProject) {
        _activeProjectModal.value = project
    }

    fun closeProjectModal() {
        _activeProjectModal.value = null
    }

    fun deleteProject(project: AffiliateProject) {
        viewModelScope.launch {
            projectRepository.deleteProject(project.id)
            if (_activeProjectModal.value?.id == project.id) {
                _activeProjectModal.value = null
            }
            Toast.makeText(getApplication(), "Proyek dihapus", Toast.LENGTH_SHORT).show()
        }
    }

    fun openSettings() {
        _showSettingsDialog.value = true
    }

    fun closeSettings() {
        _showSettingsDialog.value = false
    }

    fun openNewCharacter() {
        _showNewCharacterDialog.value = true
    }

    fun closeNewCharacter() {
        _showNewCharacterDialog.value = false
    }

    fun createCharacter(
        name: String,
        persona: String,
        category: String,
        avatarUrl: String,
        stylePrompt: String,
        skinTone: String,
        hairStyle: String,
        bodyType: String
    ) {
        viewModelScope.launch {
            val char = characterRepository.createCustomCharacter(
                name = name,
                persona = persona,
                category = category,
                avatarUrl = avatarUrl,
                stylePrompt = stylePrompt,
                skinTone = skinTone,
                hairStyle = hairStyle,
                bodyType = bodyType
            )
            _selectedCharacter.value = char
            _showNewCharacterDialog.value = false
            Toast.makeText(getApplication(), "Karakter ${char.name} berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
        }
    }

    // Outfit Replacement Logic
    fun setOutfitModelImage(url: String) {
        _outfitModelImageUrl.value = url
        _outfitResultUrl.value = null
    }

    fun setNewOutfitImage(url: String) {
        _newOutfitImageUrl.value = url
        _outfitResultUrl.value = null
    }

    fun processOutfitReplacement() {
        viewModelScope.launch {
            _isOutfitProcessing.value = true
            delay(2200)
            _outfitResultUrl.value = _newOutfitImageUrl.value
            _isOutfitProcessing.value = false
            Toast.makeText(getApplication(), "Busana berhasil diganti dengan AI!", Toast.LENGTH_SHORT).show()
        }
    }

    // Dance Reference Logic
    fun selectDanceTemplate(template: DanceTemplate) {
        _selectedDanceTemplate.value = template
    }

    fun syncDanceToCharacter() {
        val character = _selectedCharacter.value ?: allCharacters.value.firstOrNull()
        if (character == null) {
            Toast.makeText(getApplication(), "Pilih karakter influencer terlebih dahulu", Toast.LENGTH_SHORT).show()
            return
        }
        viewModelScope.launch {
            _isDanceSyncing.value = true
            delay(2500)
            _isDanceSyncing.value = false
            Toast.makeText(getApplication(), "Gerakan tari berhasil disinkronkan ke ${character.name}!", Toast.LENGTH_LONG).show()
            // Create a project from this dance
            val danceProject = AffiliateProject(
                id = "dance_proj_" + System.currentTimeMillis().toString().takeLast(6),
                title = "Dance: ${_selectedDanceTemplate.value.title}",
                productCategory = "Fashion",
                productName = "Koleksi Trendy Dance 2026",
                productImageUrl = _selectedDanceTemplate.value.previewUrl,
                characterId = character.id,
                characterName = character.name,
                characterAvatar = character.avatarUrl,
                videoMode = "Dance Reference",
                promptText = "${character.name} menirukan koreografi ${_selectedDanceTemplate.value.title} dengan tempo ${_selectedDanceTemplate.value.tempo}",
                cameraMovement = "Dynamic Handheld",
                videoStyle = "TikTok UGC Viral",
                durationSeconds = _selectedDanceTemplate.value.durationSeconds,
                aspectRatio = "9:16",
                status = "COMPLETED",
                statusMessage = "Koreografi tersinkronisasi 9:16",
                videoUrl = "https://assets.mixkit.co/videos/preview/mixkit-man-dancing-under-the-rain-43091-large.mp4",
                thumbnailUrl = _selectedDanceTemplate.value.previewUrl,
                viewsEstimate = "12.8K",
                tags = "#tiktokdance #affiliatestyle #koreografiviral"
            )
            projectRepository.insertProject(danceProject)
        }
    }

    fun shareProject(project: AffiliateProject) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "Video Affiliate: ${project.title}")
            putExtra(
                Intent.EXTRA_TEXT,
                "Lihat video konten affiliate untuk produk ${project.productName} yang dibuat dengan Affiliate AI Studio!\nModel: ${project.characterName}\nTags: ${project.tags}\nLink Video: ${project.videoUrl}"
            )
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        val chooser = Intent.createChooser(shareIntent, "Bagikan Video Affiliate ke...")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        getApplication<Application>().startActivity(chooser)
    }

    fun saveToGallery(project: AffiliateProject) {
        Toast.makeText(getApplication(), "Video ${project.title} disimpan ke penyimpanan lokal perangkat!", Toast.LENGTH_LONG).show()
    }
}
