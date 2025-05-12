package com.example.mobileapppro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


/**
 * ViewModel for the Sign Up screen, managing the UI state and handling user interactions.
 *
 * This ViewModel plays a crucial role in the Sign Up process by:
 * - Holding and managing the data entered by the user in the Sign Up form. This includes sensitive
 * information like the user's email, username, and password.
 * - Managing the visibility state of the password field, allowing the user to toggle whether the
 * password is displayed as plain text or obscured for security purposes.
 * - Handling the state of the "Remember Me" checkbox, which indicates the user's preference for
 * persisting their login information across sessions.
 *
 * The ViewModel leverages Jetpack Compose's `mutableStateOf` to expose this data.  `mutableStateOf`
 * creates a stateful property whose changes are observed by Compose. When the user interacts with the
 * UI (e.g., typing in a text field, toggling a checkbox), the ViewModel updates these `mutableStateOf`
 * properties.  This, in turn, triggers Compose to automatically recompose (update) the relevant parts of the
 * UI, ensuring that the displayed information always reflects the current state.
 *
 * The `SignUpViewModel` also provides functions to handle user actions:
 * - `togglePasswordVisibility()`:  This function is called when the user clicks the "show/hide password"
 * icon in the UI.  It toggles the `passwordVisible` property, which then updates the UI to display
 * the password field accordingly.
 * - `toggleRememberMe()`: This function is called when the user clicks the "Remember Me" checkbox.
 * It toggles the `rememberMe` property, updating the UI to reflect the user's choice.
 * - `signUp()`: This function is called when the user submits the Sign Up form.  It currently contains
 * placeholder logic that checks if the required fields (username, email, and password) are not empty.
 * In a real-world application, this function would be expanded to perform the actual sign-up operation,
 * typically by sending a network request to a backend server.  Upon successful registration, the
 * `onSuccess` callback is invoked to navigate the user to the next screen, usually the application's
 * home screen.
 */
class SignUpViewModel : ViewModel() {

    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var rememberMe by mutableStateOf(false)

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun toggleRememberMe() {
        rememberMe = !rememberMe
    }

    fun signUp(onSuccess: () -> Unit) {
        // TODO: Replace with real sign-up logic
        if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
            onSuccess()
        }
    }
}
