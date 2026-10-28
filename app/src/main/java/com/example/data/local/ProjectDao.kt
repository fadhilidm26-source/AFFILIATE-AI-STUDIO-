package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.AffiliateProject
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects ORDER BY createdAt DESC")
    fun getAllProjects(): Flow<List<AffiliateProject>>

    @Query("SELECT * FROM projects WHERE productCategory = :category ORDER BY createdAt DESC")
    fun getProjectsByCategory(category: String): Flow<List<AffiliateProject>>

    @Query("SELECT * FROM projects WHERE id = :id LIMIT 1")
    suspend fun getProjectById(id: String): AffiliateProject?

    @Query("SELECT COUNT(*) FROM projects")
    suspend fun getProjectCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: AffiliateProject)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(projects: List<AffiliateProject>)

    @Update
    suspend fun updateProject(project: AffiliateProject)

    @Query("DELETE FROM projects WHERE id = :id")
    suspend fun deleteProject(id: String)
}
