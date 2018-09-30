package com.patres.timetable.excpetion

class ApplicationException(exceptionMessage: ExceptionMessage): Exception(exceptionMessage.message)
