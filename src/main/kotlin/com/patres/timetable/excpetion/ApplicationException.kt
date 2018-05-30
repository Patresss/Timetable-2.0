package com.patres.timetable.excpetion

class ApplicationException(var exceptionMessage: ExceptionMessage): Exception(exceptionMessage.message)
