package com.example.olddragonrpg.model

/**
 * A classe base para todas as classes de personagem.
 * Opcionalmente, pode receber uma especialização.
 */
sealed class CharacterClass(
    val name: String,
    val baseAbilities: List<ClassAbility>,
    val specialization: Specialization? = null
) {
    /**
     * Propriedade computada que retorna uma lista com todas as habilidades,
     * combinando as habilidades base da classe com as da especialização.
     */
    val allAbilities: List<ClassAbility>
        get() = baseAbilities + (specialization?.grantedAbilities ?: emptyList())
}

class Warrior(specialization: WarriorSpecialization? = null) : CharacterClass(
    name = "Guerreiro",
    baseAbilities = listOf(
        CanUseAllWeapons,
        CanUseAllArmor,
        WeaponMastery(level = 1),
        ExtraAttack
    ),
    specialization = specialization
)

class Cleric(specialization: ClericSpecialization? = null) : CharacterClass(
    name = "Clérigo",
    baseAbilities = listOf(
        CanUseMediumArmorAndShields,
        RestrictedToBludgeoningWeapons,
        DivineSpells,
        TurnUndead,
        MiraculousHealing
    ),
    specialization = specialization
)

class Thief(specialization: ThiefSpecialization? = null) : CharacterClass(
    name = "Ladrão",
    baseAbilities = listOf(
        CanUseLightArmorOnly,
        CanUseSmallAndMediumWeapons,
        HearNoises(level = 1),
        SneakAttack(multiplier = 2),
        ThiefTalents
    ),
    specialization = specialization
)

class Mage(specialization: MageSpecialization? = null) : CharacterClass(
    name = "Mago",
    baseAbilities = listOf(
        CanUseSmallWeaponsOnly,
        NoArmorProficiency,
        ArcaneSpells,
        DetectMagic
    ),
    specialization = specialization
)