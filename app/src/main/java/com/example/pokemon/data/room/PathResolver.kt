package com.example.pokemon.data.room

import javax.inject.Inject

class PathResolver @Inject constructor() {

    fun getPathById(id: Int) = "/data/data/com.example.pokemon/app_imageDir/$id.jpg"

}
