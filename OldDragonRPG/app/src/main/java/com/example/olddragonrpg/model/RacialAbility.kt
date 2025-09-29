package com.example.olddragonrpg.model

enum class SavingThrowType { JPC, JPD, JPS }

sealed class RacialAbility(val name: String, val description: String)

// Habilidades de Humano
object Learning : RacialAbility("Aprendizado", "+10% de bônus em todo XP adquirido.")
data class Adaptability(var chosenSavingThrow: SavingThrowType? = null) :
    RacialAbility("Adaptabilidade", "Permite escolher uma JP (+1 de bônus).")

// Habilidades de Elfo
object NaturalPerception : RacialAbility("Percepção Natural", "Detecta passagens secretas.")
object Graceful : RacialAbility("Gracioso", "+1 em JPD.")
object BowProficiency : RacialAbility("Arma Racial", "+1 de bônus de dano com arcos.")
object SleepAndGhoulParalysisImmunity : RacialAbility("Imunidades", "Imune a sono e paralisia de Carniçais.")

// Habilidades de Anão
object Miner : RacialAbility("Minerador", "Detecta anomalias em pedra.")
object Vigorous : RacialAbility("Vigoroso", "+1 em JPC.")
object LargeWeaponHandling : RacialAbility("Armas Grandes", "Armas grandes forjadas por anões contam como médias.")
object RacialEnemies : RacialAbility("Inimigos", "Ataques fáceis contra Orcs, Ogres e Hobgoblins.")

// Habilidades de Halfling
object Stealthy : RacialAbility("Furtivo", "Chance de 1-2 em d6 para se esconder (+1 no talento de Ladrão).")
object Fearless : RacialAbility("Destemido", "+1 em JPS.")
object GoodAim : RacialAbility("Boa Mira", "Ataques de arremesso são considerados fáceis.")
object SmallStature : RacialAbility("Pequeno", "Ataques de criaturas grandes ou maiores são difíceis.")
object EquipmentRestrictions : RacialAbility("Restrições de Equipamento", "Usa apenas armaduras de couro e armas pequenas/médias.")