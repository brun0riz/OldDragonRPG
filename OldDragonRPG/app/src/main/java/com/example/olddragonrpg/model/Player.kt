package com.example.olddragonrpg.model

// This is the corrected version
data class Player(
    var name: String = "",
    var attributes: Attributes? = null,
    var race: Race? = null,
    var characterClass: CharacterClass? = null
)