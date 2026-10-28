# Affiliate AI Studio - Aplikasi Android Pembuat Video Affiliate AI

Affiliate AI Studio adalah aplikasi Android berbasis kecerdasan buatan untuk kreator affiliate (TikTok Shop, Shopee Video, Instagram Reels, YouTube Shorts). Aplikasi ini dirancang untuk memproduksi konten video promosi produk (fashion, skincare, gadget, kosmetik) dengan karakter AI influencer yang konsisten, fitur outfit replacement, reference dance, pengaturan rasio vertikal 9:16, dan penyimpanan proyek berbasis Room Database lokal.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> Berdasarkan konfirmasi pilihan pengguna:
> 1. **Model & API AI**: Menggunakan integrasi AI Logic / Gemini API dengan kemampuan konfigurasi custom API key/endpoint penyedia video AI (seperti Minimax, Kling, Luma, atau Hugging Face) dengan status rendering nyata dan transparan tanpa simulasi palsu.
> 2. **Karakter AI Influencer**: Menyediakan katalog preset karakter AI lokal Indonesia (berbagai gaya persona, etnis, dan tone) sekaligus mendukung pembuatan dan upload karakter kustom baru dengan identitas konsisten.
> 3. **Penyimpanan Data**: Menggunakan **Room Database** lokal di perangkat untuk mengelola data karakter, riwayat proyek video, draf kampanye produk, dan galeri media agar cepat, hemat biaya, dan menjamin privasi pengguna.

---

## 1. Overview & Core Concept

- **Tujuan Aplikasi**: Memungkinkan kreator affiliate memproduksi video promosi produk berkualitas tinggi dalam hitungan menit tanpa perlu menyewa model, studio foto, atau peralatan mahal.
- **Target Pengguna**: Konten kreator TikTok Affiliate, Shopee Creator, dropshipper, brand owner UMKM di kategori fashion, kecantikan, lifestyle, dan gadget.
- **Nilai Utama**:
  - **Konsistensi Karakter**: Wajah dan penampilan influencer tetap sama di seluruh seri video kampanye.
  - **Efisiensi Produksi**: Mengubah foto produk menjadi video try-on atau ulasan UGC 9:16 secara otomatis.
  - **Kamera & Koreografi Fleksibel**: Pilihan mode gerak kamera (Dynamic UGC, Handheld, Smooth Orbit, Zoom) dan replikasi koreografi tari viral.
  - **Zero Fake Claims**: Desain antarmuka transparan dengan status API, validasi token/kunci, antrian generasi, dan penanganan error yang jelas.

---

## 2. User Experience & Visual Design

### 5 Navigasi Utama (Bahasa Indonesia)
1. **Beranda (Home)**:
   - Ringkasan statistik konten & kampanye aktif.
   - Pilihan cepat template affiliate populer (Fashion Try-On, Skincare Review, Gadget Unboxing, Trending Dance).
   - Kartu karakter influencer aktif & tombol cepat ganti/buat karakter baru.
2. **Studio Video (Create Video)**:
   - Alur pembuatan video interaktif: Pemilihan Karakter Influencer -> Upload/Pilih Foto Produk -> Tipe Konten (Review UGC, Try-on, Unboxing, Demonstrasi) -> Prompt & Pengaturan Kamera (Zoom, Pan, Handheld, Tilt) -> Durasi & Format 9:16.
   - Pengaturan gaya video (Casual TikTok, Aesthetic Studio, Natural Outdoor, Mirror Selfie).
   - Panel eksekusi & progress generator dengan log status transparan.
3. **Alat Produk (Product Tools)**:
   - *AI Outfit & Product Replacement*: Mengganti busana model dengan pakaian produk secara presisi.
   - *Product Isolator / Background Clean*: Memisahkan produk dari latar belakang untuk visual bersih.
4. **Referensi Tari (Dance Reference)**:
   - Input video referensi koreografi TikTok / klip pendek.
   - Pengaturan tempo, pose awal, dan transfer gerakan ke karakter AI.
5. **Galeri Saya (My Gallery)**:
   - Riwayat video & gambar hasil render berformat 9:16.
   - Filter berdasarkan kategori produk (Fashion, Skincare, Gadget, Kosmetik, Lainnya) dan nama kampanye.
   - Pemutar video vertikal, tombol simpan ke galeri Android, dan integrasi tombol bagikan (Share Sheet) ke TikTok, Instagram Reels, dan YouTube Shorts.
   - Dialog pengaturan API Key & Model AI Provider.

### Tema & Visual Identity
- **Aesthetic Direction**: *Creator Studio Dark Mode* — nuansa studio produksi modern dengan kontras tinggi, elegan, dan fokus pada konten visual.
- **Palet Warna**:
  - `Background`: Deep Midnight Slate (`#0F1117`)
  - `Surface`: Slate Elevated (`#1A1D27`)
  - `Surface Container`: Surface Accent (`#242938`)
  - `Primary Accent`: Electric Violet / Neon Rose (`#8B5CF6` / `#EC4899`) yang identik dengan platform video pendek dan kreator digital.
  - `Secondary Accent`: Warm Amber (`#F59E0B`) untuk highlight badge affiliate dan status proses.
  - `Text Primary`: Pristine White (`#F8FAFC`)
  - `Text Muted`: Cool Slate (`#94A3B8`)
- **Tipografi**: Hierarchy yang tegas dengan judul semibold dan label chip teknis yang mudah dibaca.
- **Interaksi**: Animasi status berdenyut (pulsing progress bar), touch target minimal 48dp, dan umpan balik haptic saat menekan tombol aksi utama.

---

## 3. Key Product Decisions & Trade-Offs

- **Penyimpanan Lokal vs Cloud**:
  - *Keputusan*: Room Database lokal (SQLite) + MediaStore/Local File Storage.
  - *Alasan*: Bebas biaya langganan cloud database, instan tanpa latency internet untuk membuka draf/galeri, dan privasi konten pengguna tetap berada di ponsel.
- **Arsitektur Provider AI Fleksibel**:
  - *Keputusan*: Membuat modul `AiVideoRepository` dengan dukungan Gemini API (via BuildConfig/Secrets) serta adapter penyedia video eksternal (API Key kustom pengguna atau mode preview draf terverifikasi).
  - *Alasan*: Pengguna tidak terkunci pada satu penyedia; saat kuota suatu API habis, pengguna dapat mengganti key atau beralih provider tanpa mengubah kode aplikasi.
- **Kompatibilitas Format Video Vertikal (9:16)**:
  - *Keputusan*: Seluruh canvas pratinjau dan pemutar video menggunakan rasio aspek 9:16 standar TikTok/Reels dengan kontrol overlay minimalis.

---

## 4. Technical Architecture & Data Strategy

### Diagram Arsitektur Sistem

```
┌────────────────────────────────────────────────────────────────────────┐
│                        Affiliate AI Studio App                         │
├────────────────────────────────────────────────────────────────────────┤
│ UI Layer (Jetpack Compose - Material 3)                                │
│ ┌───────────────┬─────────────────┬───────────────┬──────────────────┐ │
│ │ HomeScreen    │ StudioCreate    │ ProductTools  │ GalleryScreen    │ │
│ │ (Stats/Preset)│ (Video Pipeline)│ (Outfit/Swap) │ (Player & Export)│ │
│ └───────┬───────┴────────┬────────┴───────┬───────┴────────┬─────────┘ │
│         │                │                │                │           │
│ ┌───────▼────────────────▼────────────────▼────────────────▼─────────┐ │
│ │ ViewModels (HomeViewModel, StudioViewModel, GalleryViewModel)       │ │
│ └───────────────────────────────┬────────────────────────────────────┘ │
├─────────────────────────────────┼──────────────────────────────────────┤
│ Domain & Data Layer             │                                      │
│ ┌───────────────────────────────▼────────────────────────────────────┐ │
│ │ Repositories:                                                      │ │
│ │  - CharacterRepository (Preset Influencer & Custom Characters)     │ │
│ │  - ProjectRepository (Video Drafts, Prompt Logs, Render History)    │ │
│ │  - AiGenerationRepository (Gemini API / Custom Video API Client)   │ │
│ │  - SettingsRepository (API Keys, Video Defaults, Preferences)      │ │
│ └───────────────────────────────┬────────────────────────────────────┘ │
├─────────────────────────────────┼──────────────────────────────────────┤
│ Storage & Networking            │                                      │
│ ┌───────────────────────────────▼───────────┐ ┌──────────────────────┐ │
│ │ Room Database (AppDatabase)               │ │ Remote AI Providers  │ │
│ │  - CharacterEntity (Influencer details)   │ │  - Gemini API Client │ │
│ │  - ProjectEntity (Video projects & status)│ │  - Custom Video API  │ │
│ │  - MediaItemEntity (Gallery assets)       │ │  - Http / OkHttp     │ │
│ └───────────────────────────────────────────┘ └──────────────────────┘ │
└────────────────────────────────────────────────────────────────────────┘
```

### Data Model & Entitas Room
1. `InfluencerCharacter`: ID, nama, persona (e.g., "Anya - Skincare Enthusiast", "Rian - Tech Reviewer"), foto URL/drawable, deskripsi gaya, etnis/karakteristik wajah.
2. `AffiliateProject`: ID, judul proyek, kategori produk (Fashion, Skincare, Gadget, dll.), mode video (Try-on, Review UGC, Unboxing, Dance), prompt, durasi (5s, 10s, 15s), camera movement, status proses (`DRAFT`, `QUEUED`, `PROCESSING`, `COMPLETED`, `FAILED`), URL video/gambar hasil, timestamp.
3. `ProductItem`: ID, nama produk, foto produk referensi, catatan busana/atribut.

### Alur Kerja & Penanganan State
- **Input Validation**: Memastikan foto produk atau karakter sudah dipilih sebelum memicu tombol render.
- **State Feedback**: State generation menampilkan tahapan jelas: *Menyiapkan prompt AI -> Memproses konsistensi wajah -> Menyesuaikan busana/gerakan -> Meng-generate video*.
- **Pemutar & Ekspor**: Komponen pemutar video dan pratinjau vertikal dengan tombol download ke penyimpanan perangkat dan tombol bagikan langsung ke media sosial melalui Android Intent.
