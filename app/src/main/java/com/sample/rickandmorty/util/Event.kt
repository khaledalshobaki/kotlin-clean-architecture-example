package com.sample.rickandmorty.util

/**
 * A wrapper class for data that represents an event.
 * This class helps in handling events that should be consumed only once, like showing a message or performing an action.
 */
open class Event<out T>(private val content: T) {

    // Tracks whether the event has already been handled or not.
    private var hasBeenHandled = false

    /**
     * Returns the content if it hasn't been handled yet. Once the content is accessed, it is marked as handled.
     * This prevents the same event from being processed multiple times.
     *
     * @return The content if it has not been handled, otherwise returns null.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it has already been handled.
     * This can be used to get the value without marking it as handled.
     *
     * @return The content.
     */
    fun peekContent(): T = content
}
