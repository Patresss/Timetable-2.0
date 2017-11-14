package com.patres.timetable.web.rest.errors

import java.io.Serializable
import java.util.ArrayList

/**
 * View Model for transferring error message with a list of field errors.
 */
class ErrorVM(
    val message: String = "",
    val description: String = "",
    private var fieldErrors: ArrayList<FieldErrorVM> = ArrayList()
) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }

    fun add(objectName: String, field: String, message: String) {
        fieldErrors.add(FieldErrorVM(objectName, field, message))
    }

}
