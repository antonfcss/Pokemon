package com.example.pokemon.domane

data class PokemonModelDetail(
    val name: String,
    val height: Int,
    val weight: Double,
    val spritesApiModel: SpritesModel,
    val types: List<PokemonType>
) {

    fun isNotEmpty(): Boolean {
        val isNameReceived = name.isNotEmpty()
        val haveImage = spritesApiModel.frontImage != null
        val haveTypes = types.isNotEmpty()
        return haveImage && haveTypes && isNameReceived
    }
}
