package com.patres.timetable.web.rest.vm

/**
 * View Model object for storing the user's key and password.
 */
data class KeyAndPasswordVM(
    var key: String = "",
    var newPassword: String = ""
)
