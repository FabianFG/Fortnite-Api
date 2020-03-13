package me.fungames.fortnite.api.exceptions

import me.fungames.fortnite.api.model.EpicError


class EpicErrorException : Exception {
    constructor(error: EpicError) : super(error.errorMessage)
    constructor(e: Exception) : super(e)
    constructor(message : String) : super(message)
}