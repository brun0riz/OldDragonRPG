package com.example.olddragonrpg.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.olddragonrpg.controller.CharacterCreationViewModel
import com.example.olddragonrpg.controller.CreationStep
import kotlin.reflect.KClass

@Composable
fun CharacterCreationScreen(viewModel: CharacterCreationViewModel) {
    val step = viewModel.currentStep.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // UPDATED: Added the new assignment screen to the router
        when (step) {
            CreationStep.NAME -> NameScreen(viewModel)
            CreationStep.ATTRIBUTE_METHOD -> AttributeMethodScreen(viewModel)
            CreationStep.ATTRIBUTE_ASSIGNMENT -> AttributeAssignmentScreen(viewModel)
            CreationStep.RACE -> RaceScreen(viewModel)
            CreationStep.CLASS -> ClassScreen(viewModel)
            CreationStep.SUMMARY -> SummaryScreen(viewModel)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameScreen(viewModel: CharacterCreationViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Qual o nome do seu personagem?", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.player.value.name,
            onValueChange = { viewModel.setName(it) },
            label = { Text("Nome do Personagem") }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { viewModel.proceedToNextStep() }) {
            Text("Próximo")
        }
    }
}

@Composable
fun AttributeMethodScreen(viewModel: CharacterCreationViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Escolha o método de rolagem de atributos:", style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        val methods = listOf("Clássico", "Aventureiro", "Heróico")
        methods.forEach { method ->
            Button(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                // UPDATED: The proceedToNextStep() call is removed.
                // The ViewModel now handles navigation inside selectAttributeMethod().
                onClick = { viewModel.selectAttributeMethod(method) }
            ) {
                Text(method)
            }
        }
    }
}

// NEW SCREEN: The UI for interactively assigning attribute rolls.
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AttributeAssignmentScreen(viewModel: CharacterCreationViewModel) {
    var selectedAttribute by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Distribua seus Atributos", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // Section for Attributes
        Column(modifier = Modifier.weight(1f)) {
            viewModel.attributeNames.forEach { attrName ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(attrName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    val assignedValue = viewModel.assignedAttributes[attrName]
                    if (assignedValue != null) {
                        Text(
                            text = assignedValue.toString(),
                            fontSize = 20.sp,
                            modifier = Modifier
                                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                .padding(12.dp)
                        )
                    } else {
                        Button(onClick = { selectedAttribute = attrName }) {
                            Text("Atribuir")
                        }
                    }
                }
            }
        }

        // Section for available rolls
        if (selectedAttribute != null) {
            Text("Selecione um valor para: $selectedAttribute", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.unassignedRolls.value.forEach { roll ->
                    Button(onClick = {
                        viewModel.assignRoll(selectedAttribute!!, roll)
                        selectedAttribute = null // Deselect after assigning
                    }) {
                        Text(roll.toString())
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.confirmAttributeAssignments() },
            enabled = viewModel.assignedAttributes.size == 6 // Enable only when all are assigned
        ) {
            Text("Confirmar e Próximo")
        }
    }
}

@Composable
fun RaceScreen(viewModel: CharacterCreationViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Escolha a raça do seu personagem:", style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        viewModel.races.forEach { race ->
            Button(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                // UPDATED: Navigation is now handled by the ViewModel
                onClick = { viewModel.selectRace(race) }
            ) {
                Text(race.name)
            }
        }
    }
}

@Composable
fun ClassScreen(viewModel: CharacterCreationViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Escolha sua classe:", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        if (viewModel.availableClasses.isEmpty()) {
            Text("Nenhuma classe disponível para a raça selecionada.")
        } else {
            viewModel.availableClasses.forEach { kClass ->
                Button(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    // UPDATED: Navigation is now handled by the ViewModel
                    onClick = { viewModel.selectClass(kClass) }
                ) {
                    Text(kClass.simpleName ?: "Classe Desconhecida")
                }
            }
        }
    }
}


@Composable
fun SummaryScreen(viewModel: CharacterCreationViewModel) {
    val player = viewModel.player.value
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("FICHA DO PERSONAGEM", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Nome: ${player.name}", fontSize = 20.sp)
        Text("Raça: ${player.race?.name ?: "N/A"}", fontSize = 20.sp)
        Text("Classe: ${player.characterClass?.name ?: "N/A"}", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Atributos:", style = MaterialTheme.typography.headlineSmall)
        player.attributes?.let {
            Text("Força: ${it.strength}")
            Text("Destreza: ${it.dexterity}")
            Text("Constituição: ${it.constitution}")
            Text("Inteligência: ${it.intelligence}")
            Text("Sabedoria: ${it.wisdom}")
            Text("Carisma: ${it.charisma}")
        }
    }
}