package trees

import extenstions.removeWhitespaces

fun validateTreeString(treeString: String): Result<*> {
    return try {
        val preparedTreeString = treeString.removeWhitespaces()
        checkBracketsAmount(treeString = preparedTreeString)

        processTreeStringThroughStateMachine(treeString = preparedTreeString)
        Result.success(Unit)
    } catch (throwable: Throwable) {
        Result.failure<Unit>(exception = throwable)
    }
}

private fun checkBracketsAmount(treeString: String) {
    var bracketsCounter = 0

    treeString.forEach {
        when (it) {
            '(' -> bracketsCounter++
            ')' -> bracketsCounter--
        }
        if (bracketsCounter < 0) {
            throw InvalidBracketsOrderException()
        }
    }

    if (bracketsCounter != 0) {
        throw InvalidBracketsOrderException()
    }
}

class InvalidBracketsOrderException : Exception("Неправильно расставлены скобки.")

private fun processTreeStringThroughStateMachine(treeString: String) {

    fun throwValidationException(problemIndex: Int) {
        throw InvalidBinaryStringException(
            inputString = treeString,
            problemIndex = problemIndex
        )
    }


    if (treeString.isEmpty()) return

    var currentStateIndex = INITIAL_STATE_INDEX
    treeString.forEachIndexed { index, symbol ->
        val symbolCode = symbol.toStateMachineEdgeCode()

        val newCurrentState = BinaryTreeStringValidationStateMachine.get(
            key = (currentStateIndex to symbolCode)
        )

        newCurrentState?.let {
            currentStateIndex = it
        } ?: run { throwValidationException(problemIndex = index) }
    }

    if (currentStateIndex != FINITE_STATE_INDEX) throwValidationException(
        problemIndex = treeString.length
    )

    return
}

data class InvalidBinaryStringException(
    val inputString: String,
    val problemIndex: Int
) : Exception("Неверная строка\nВходная строка: $inputString\nОшибка на индексе $problemIndex")

// коды для обозначения символов на ребрах графа состояний
private val DIGIT_SYMBOL_CODE = "DIGIT_SYMBOL_CODE"
private val OPEN_BRACKET_CODE = "OPEN_BRACKET_CODE"
private val CLOSE_BRACKET_CODE = "CLOSE_BRACKET_CODE"
private val UNKNOWN_SYMBOL_CODE = "UNKNOWN_SYMBOL_CODE"

private fun Char.toStateMachineEdgeCode(): String {
    return when {
        this == '(' -> OPEN_BRACKET_CODE
        this.isDigit() -> DIGIT_SYMBOL_CODE
        this == ')' -> CLOSE_BRACKET_CODE
        else -> UNKNOWN_SYMBOL_CODE
    }
}

private val INITIAL_STATE_INDEX = 0
private val FINITE_STATE_INDEX = 3

private val BinaryTreeStringValidationStateMachine =
    mapOf(
        ((0 to OPEN_BRACKET_CODE) to 1),
        ((1 to DIGIT_SYMBOL_CODE) to 2),
        ((2 to DIGIT_SYMBOL_CODE) to 2),
        ((2 to OPEN_BRACKET_CODE) to 1),
        ((2 to CLOSE_BRACKET_CODE) to 3),
        ((3 to CLOSE_BRACKET_CODE) to 3),
        ((3 to OPEN_BRACKET_CODE) to 1),
    )

