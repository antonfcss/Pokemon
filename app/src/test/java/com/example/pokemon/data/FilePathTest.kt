package com.example.pokemon.data

import com.example.pokemon.data.room.PathResolver
import org.junit.Assert.assertEquals
import org.junit.Test

class FilePathTest {

    @Test
    fun testGetPathById() {
        val id = 1
        val expectedPath = "/data/data/com.example.pokemon/app_imageDir/$id.jpg"
        val repository = PathResolver()
        val actualPath = repository.getPathById(id)
        assertEquals(expectedPath, actualPath)
    }

}
