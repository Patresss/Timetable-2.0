package com.patres.timetable.web.rest.vm

import ch.qos.logback.classic.Logger

/**
 * View Model object for storing a Logback logger.
 */
data class LoggerVM(
    var name: String = "",
    var level: String = ""
) {

    constructor(logger: Logger) : this(logger.name, logger.effectiveLevel.toString())

}
