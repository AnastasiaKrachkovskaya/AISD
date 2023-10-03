package algorithm
import dynamicarray.DynamicArray
import stack.Stack
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class Algorithm(
    infixExpression: String,
) {


    private val tokens = infixExpression.splitIntoTokensDynamicArray()

    //	Список и приоритет операторов
    private val operationPriority = mapOf(
        "(" to 0,
        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2,
        "^" to 3,
        "~" to 4,    //унарный минус
        "cos" to 5,
        "sin" to 5,
    )

    //Хранит постфиксное выражение
    private var postfixExpression: String = toPostfix()
        private set

    private fun String.isNumeric(): Boolean {
        return this.all { it.isDigit() }
    }

    fun toPostfix(): String {
        var postfixExpression = ""
        //	Инициализация стека, содержащий операторы в виде символов
        val stack = Stack<String>()
        for (_index in 0..<tokens.size) {
            val token = tokens[_index]
            var op: String = token

            when {
                token.isNumeric() -> {
                    postfixExpression += token + " "
                }

                token == "(" -> {
                    stack.push(token)
                }

                token == ")" -> {
                    while (stack.size > 0 && stack.peek() != "(") {
                        postfixExpression += "${stack.pop()} "
                    }
                    stack.pop()
                }

                operationPriority.containsKey(key = token) -> {
                    if (op == "-" && (_index == 0 || (_index > 1 && operationPriority.containsKey(tokens[_index - 1]))))
                        op = "~"

                    while (stack.size > 0 && (operationPriority[stack.peek()]!! >= operationPriority[op]!!))
                        postfixExpression += "${stack.pop()} "
                    stack.push(op)
                }
            }
        }

        val restOperators = stack.popAll()
        for (index in restOperators.size - 1 downTo 0) {
            postfixExpression += "${restOperators[index]} "
        }
        return postfixExpression
    }


    private fun String.splitIntoTokensDynamicArray(): DynamicArray<String> {
        val dynamicArray = DynamicArray<String>(initialCapacity = 0)

        var currentToken = ""
        for (symbol in this) {
            if (symbol == ' ') {
                dynamicArray.add(currentToken)
                currentToken = ""
            } else {
                currentToken += symbol
            }
        }
        if (currentToken.isNotEmpty()) dynamicArray.add(currentToken)

        return dynamicArray
    }

    private fun execute(op: String, first: Double, second: Double): Double {
        val result: Double = when (op) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> first / second
            "^" -> first.pow(second)
            else -> {
                0.0
            }
        }
        return result
    }

    fun calculator(): Double {
        //	Стек для хранения чисел
        val stack = Stack<Double>()
        //	Счётчик действий
        var counter: Int = 0
        val tokensInPostf = postfixExpression.splitIntoTokensDynamicArray()
        for (i in 0..<tokensInPostf.size) {
            val token = tokensInPostf[i]
            var op: String = token
            when {
                token.isNumeric() -> {
                    stack.push(token.toDouble())
                }

                operationPriority.containsKey(key = token) -> {
                    counter++
                    //	Проверяем, является ли данный оператор унарным
                    if (op == "~") {
                        val last: Double = if (stack.size > 0) stack.pop() else 0.0
                        stack.push(execute("-", 0.0, last))
                        continue
                    }
                    if (op == "cos") {
                        val last: Double =  stack.pop()
                        stack.push(cos(last))
                        continue
                    }
                    if (op == "sin") {
                        val last: Double =  stack.pop()
                        stack.push(sin(last))
                        continue
                    }
                    //	Получаем значения из стека в обратном порядке
                    val second: Double = if (stack.size > 0) stack.pop() else 0.0
                    val first: Double = if (stack.size > 0) stack.pop() else 0.0
                    //	Получаем результат операции и заносим в стек
                    stack.push( execute(token, first, second))
                }
            }


        }
        return stack.pop()
    }


}





