package com.sample.rickandmorty.presentation.character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.rickandmorty.BuildConfig
import com.sample.rickandmorty.domain.model.Character
import com.sample.rickandmorty.domain.usecase.GetCharactersUseCase
import com.sample.rickandmorty.domain.usecase.LoadCharactersUseCase
import com.sample.rickandmorty.util.ErrorHandler
import com.sample.rickandmorty.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val errorHandler: ErrorHandler,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val loadCharactersUseCase: LoadCharactersUseCase
) : ViewModel() {

    private var canLoadMore = true

    // StateFlow to indicate loading status
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // StateFlow to hold user messages for displaying errors or other information
    private val _userMessage = MutableStateFlow<Event<Int>?>(null)
    val userMessage: StateFlow<Event<Int>?> = _userMessage.asStateFlow()

    // StateFlow to hold the character details
    val characters: StateFlow<List<UiCharacter>> = getCharactersUseCase()
        .map { it.toUiCharacters() }
        .flowOn(Default)
        .catch { e ->
            // Update userMessage with error information if an exception occurs
            _userMessage.value = Event(errorHandler.getErrorMessage(e))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Initializer block to load the first page of characters
    init {
        loadCharactersPage()
    }

    // Load the next page of characters
    fun loadCharactersPage() {
        if (shouldLoadMore()) {
            viewModelScope.launch {
                _isLoading.value = true

                loadCharacters(page = calculateNextPage())

                _isLoading.value = false
            }
        }
    }

    // Determine if more data should be loaded
    private fun shouldLoadMore(): Boolean = !isLoading.value && canLoadMore

    // Calculate the next page number based on the last character's ID
    private fun calculateNextPage(): Int {
        val lastId = characters.value.lastOrNull()?.id ?: 0
        return (lastId / BuildConfig.PAGE_SIZE) + 1
    }

    // Load characters from the use case and update the state
    private suspend fun loadCharacters(page: Int) {
        runCatching { loadCharactersUseCase(page) }
            .onSuccess {
                canLoadMore = it
            }
            .onFailure { error ->
                _userMessage.value = Event(errorHandler.getErrorMessage(error))
            }
    }
}

/**
 * Extension function to map a Character domain model to a UiCharacter for UI display.
 * Converts the domain model to a UI-specific representation.
 *
 * @return UiCharacter - The UI representation of the domain character.
 */
fun Character.toUiCharacter() = UiCharacter(
    id = id,
    name = name,
    image = image
)

/**
 * Extension function to map a list of Character domain models to a list of UiCharacter models.
 * Converts a collection of domain characters into UI-ready representations.
 *
 * @return List<UiCharacter> - The list of UI representations of the domain characters.
 */
fun List<Character>.toUiCharacters() = map(Character::toUiCharacter)
