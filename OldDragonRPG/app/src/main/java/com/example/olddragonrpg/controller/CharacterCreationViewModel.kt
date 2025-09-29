package com.example.olddragonrpg.controller

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.olddragonrpg.model.*
import kotlin.reflect.KClass

// Represents the different screens/steps in our creation process
enum class CreationStep {
    NAME,
    ATTRIBUTE_METHOD,
    ATTRIBUTE_ASSIGNMENT, // Step for the interactive assignment screen
    RACE,
    CLASS,
    SUMMARY
}

class CharacterCreationViewModel : ViewModel() {

    // The player object being built.
    var player = mutableStateOf(Player())
        private set

    // The current screen we are on.
    var currentStep = mutableStateOf(CreationStep.NAME)
        private set

    // --- STATE for Attribute Assignment ---
    // The list of numbers the user rolled but hasn't used yet.
    var unassignedRolls = mutableStateOf<List<Int>>(emptyList())
        private set
    // A map to hold the attributes as they are being assigned.
    var assignedAttributes = mutableStateMapOf<String, Int>()
        private set
    // A helper list of attribute names in order.
    val attributeNames = listOf("Força", "Destreza", "Constituição", "Inteligência", "Sabedoria", "Carisma")


    // --- Game Data from Model ---
    val races = listOf(Race.Human, Race.Elf, Race.Dwarf, Race.Halfling)
    private val allGameClasses = mapOf(
        Warrior::class to listOf(Barbarian, Paladin),
        Cleric::class to listOf(Druid, Academic),
        Thief::class to listOf(Ranger, Bard),
        Mage::class to listOf(Illusionist, Necromancer)
    )

    // A computed property to get available classes based on the chosen race
    val availableClasses: List<KClass<out CharacterClass>>
        get() = player.value.race?.allowedClasses ?: emptyList()


    // --- Functions the UI will call ---

    fun setName(name: String) {
        player.value.name = name
    }

    // A simple function for buttons that just move to the next linear step
    fun proceedToNextStep() {
        when (currentStep.value) {
            CreationStep.NAME -> currentStep.value = CreationStep.ATTRIBUTE_METHOD
            CreationStep.RACE -> currentStep.value = CreationStep.CLASS
            CreationStep.SUMMARY -> { /* Maybe restart or finish */ }
            else -> { /* Other steps have custom navigation logic */ }
        }
    }

    // UPDATED: This now contains the full logic for all 3 methods
    fun selectAttributeMethod(method: String) {
        val d6 = D6()
        if (method == "Clássico") {
            player.value.attributes = Attributes.rollClassic(d6)
            currentStep.value = CreationStep.RACE // Skip assignment for Classic mode
        } else {
            // Roll the values using the appropriate method from the Model
            val rolls = when (method) {
                "Aventureiro" -> Attributes.rollAdventurerValues(d6)
                "Heróico" -> Attributes.rollHeroicValues(d6)
                else -> emptyList()
            }
            // Set up the state for the assignment screen
            unassignedRolls.value = rolls.sortedDescending()
            assignedAttributes.clear() // Ensure we start fresh
            currentStep.value = CreationStep.ATTRIBUTE_ASSIGNMENT // Go to our new screen
        }
    }

    fun selectRace(race: Race) {
        player.value.race = race
        player.value.characterClass = null // Clear class in case it's not allowed by new race
        proceedToNextStep() // Move from RACE to CLASS
    }

    fun selectClass(kClass: KClass<out CharacterClass>) {
        val newClass = when (kClass) {
            Warrior::class -> Warrior()
            Cleric::class -> Cleric()
            Thief::class -> Thief()
            Mage::class -> Mage()
            else -> throw IllegalStateException("Classe desconhecida")
        }
        player.value.characterClass = newClass
        currentStep.value = CreationStep.SUMMARY // Move to the final summary
    }


    // --- NEW FUNCTIONS for Attribute Assignment ---

    /**
     * Assigns a specific roll to an attribute, updating the lists of
     * assigned and unassigned values.
     */
    fun assignRoll(attributeName: String, roll: Int) {
        // If the attribute was already assigned, put its old value back in the unassigned list
        if (assignedAttributes.containsKey(attributeName)) {
            val oldRoll = assignedAttributes[attributeName]!!
            unassignedRolls.value = (unassignedRolls.value + oldRoll).sortedDescending()
        }
        assignedAttributes[attributeName] = roll
        unassignedRolls.value = (unassignedRolls.value - roll).sortedDescending()
    }

    /**
     * Called when the user confirms their attribute choices.
     * It builds the final Attributes object and moves to the next step.
     */
    fun confirmAttributeAssignments() {
        if (assignedAttributes.size == 6) {
            player.value.attributes = Attributes(
                strength = assignedAttributes["Força"]!!,
                dexterity = assignedAttributes["Destreza"]!!,
                constitution = assignedAttributes["Constituição"]!!,
                intelligence = assignedAttributes["Inteligência"]!!,
                wisdom = assignedAttributes["Sabedoria"]!!,
                charisma = assignedAttributes["Carisma"]!!
            )
            // Proceed to the next step in the creation process
            currentStep.value = CreationStep.RACE
        }
    }
}