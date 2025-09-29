package com.example.olddragonrpg.model

sealed class Specialization(val name: String, val grantedAbilities: List<ClassAbility>)

// --- Especializações de Guerreiro ---
sealed class WarriorSpecialization(name: String, grantedAbilities: List<ClassAbility>) : Specialization(name, grantedAbilities)
object Barbarian : WarriorSpecialization("Bárbaro", grantedAbilities = listOf(
    BarbarianRage,
    BarbarianVigor
))
object Paladin : WarriorSpecialization("Paladino", grantedAbilities = listOf(
    DetectEvil,
    LayOnHands,
    DivineGrace
))

// --- Especializações de Clérigo ---
sealed class ClericSpecialization(name: String, grantedAbilities: List<ClassAbility>) : Specialization(name, grantedAbilities)
object Druid : ClericSpecialization("Druida", grantedAbilities = listOf(/* Adicione habilidades do Druida aqui */))
object Academic : ClericSpecialization("Acadêmico", grantedAbilities = listOf(
    AcademicKnowledge,
    IdentifyItems,
    DecipherLanguages
))

// --- Especializações de Ladrão ---
sealed class ThiefSpecialization(name: String, grantedAbilities: List<ClassAbility>) : Specialization(name, grantedAbilities)
object Ranger : ThiefSpecialization("backend.Ranger", grantedAbilities = listOf(
    AnimalCompanion,
    Tracking
))
object Bard : ThiefSpecialization("Bardo", grantedAbilities = listOf(
    BardicKnowledge,
    InspireCourage
))

// --- Especializações de Mago ---
sealed class MageSpecialization(name: String, grantedAbilities: List<ClassAbility>) : Specialization(name, grantedAbilities)
object Illusionist : MageSpecialization("Ilusionista", grantedAbilities = listOf(
    IllusionistExclusiveSpells
))
object Necromancer : MageSpecialization("Necromante", grantedAbilities = listOf(
    NecromancerExclusiveSpells
))