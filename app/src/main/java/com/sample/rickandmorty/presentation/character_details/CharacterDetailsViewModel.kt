package com.sample.rickandmorty.presentation.character_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.rickandmorty.domain.model.Character
import com.sample.rickandmorty.domain.usecase.GetCharacterByIdUseCase
import com.sample.rickandmorty.presentation.Screen
import com.sample.rickandmorty.util.DateUtils
import com.sample.rickandmorty.util.ErrorHandler
import com.sample.rickandmorty.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase, // Injected use case for getting character by ID
    private val errorHandler: ErrorHandler // Injected error handler for managing error messages
) : ViewModel() {

    // Extracting the characterId key from the SavedStateHandle
    private val characterId: Int =
        savedStateHandle.get<Int>(Screen.CharacterDetails::characterId.name)
            ?: throw IllegalArgumentException("Character ID is missing")

    // StateFlow to hold user messages for displaying errors or other information
    private val _userMessage: MutableStateFlow<Event<Int>?> = MutableStateFlow(null)
    val userMessage: StateFlow<Event<Int>?> = _userMessage.asStateFlow()

    // StateFlow to hold the character details
    val character: StateFlow<UiCharacterDetails?> = getCharacterByIdUseCase(characterId)
        .map { it?.toUiCharacterDetails() }
        .catch { e ->
            // Update userMessage with error information if an exception occurs
            _userMessage.value = Event(errorHandler.getErrorMessage(e))
        }
        .stateIn(
            scope = viewModelScope, // Define the scope for StateFlow
            started = SharingStarted.WhileSubscribed(5000), // Automatically restart the flow if it's not subscribed for 5 seconds
            initialValue = null // Initial value of the StateFlow
        )
}

/**
 * Extension function to map a Character domain model to a UiCharacterDetails for detailed UI display.
 * Converts the domain model to a UI-specific representation including additional details.
 *
 * @return UiCharacterDetails - The UI representation of the domain character with additional details.
 */
fun Character.toUiCharacterDetails() = UiCharacterDetails(
    id = id,
    name = name,
    image = image,
    gender = gender,
    species = species,
    status = status,
    createdAt = DateUtils.formatDate(createdAt)
)

