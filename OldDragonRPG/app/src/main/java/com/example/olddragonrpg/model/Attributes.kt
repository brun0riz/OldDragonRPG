package com.example.olddragonrpg.model

// Data class serve para armazenar os dados
data class Attributes(
    // Atributos relacionados as regras do RPG
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int
) {
    // Objetos das campanhas
    companion object {
        fun rollClassic(d6: D6): Attributes {
            return Attributes(
                strength = d6.roll(3).sum(),
                dexterity = d6.roll(3).sum(),
                constitution = d6.roll(3).sum(),
                intelligence = d6.roll(3).sum(),
                wisdom = d6.roll(3).sum(),
                charisma = d6.roll(3).sum()
            )
        }

        fun rollAdventurerValues(d6: D6): List<Int> {
            return List(6) { d6.roll(3).sum() }
        }

        // REFACTORED: Just return the rolled values.
        fun rollHeroicValues(d6: D6): List<Int> {
            val rolls = mutableListOf<Int>()
            repeat(6) {
                val dice = d6.roll(4)
                val sumTop3 = dice.sortedDescending().take(3).sum()
                rolls.add(sumTop3)
            }
            return rolls
        }
    }
}