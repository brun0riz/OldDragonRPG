package com.example.olddragonrpg.model

import kotlin.reflect.KClass

// Usei o sealed class para que possa ter controle sobre as classes que quero que exista
sealed class Race(
    val name: String,
    val movement: Int,
    val infravision: Int,
    val alignment: String,
    val abilities: List<RacialAbility>,
    val allowedClasses: List<KClass<out CharacterClass>>
) {
    // Aqui criei apenas instâncias das raças, já que elas são apenas 'conceitos'
    object Human : Race(
        name = "Humano", movement = 9, infravision = 0, alignment = "Qualquer",
        abilities = listOf(Learning, Adaptability()),
        allowedClasses = listOf(Warrior::class, Cleric::class, Thief::class, Mage::class) // Limitar as classes permitidas
    )

    object Elf : Race(
        name = "Elfo", movement = 9, infravision = 18, alignment = "Neutro",
        abilities = listOf(
            NaturalPerception,
            Graceful,
            BowProficiency,
            SleepAndGhoulParalysisImmunity
        ),
        allowedClasses = listOf(Warrior::class, Thief::class, Mage::class)
    )

    object Dwarf : Race(
        name = "Anão", movement = 6, infravision = 18, alignment = "Ordeiro",
        abilities = listOf(Miner, Vigorous, LargeWeaponHandling, RacialEnemies),
        allowedClasses = listOf(Warrior::class, Cleric::class) // Inaptos para Magia Arcana
    )

    object Halfling : Race(
        name = "Halfling", movement = 6, infravision = 0, alignment = "Neutro",
        abilities = listOf(Stealthy, Fearless, GoodAim, SmallStature, EquipmentRestrictions),
        allowedClasses = listOf(Warrior::class, Thief::class)
    )
}