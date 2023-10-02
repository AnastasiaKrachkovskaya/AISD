package algorithm

import dynamicarray.DynamicArray
import stack.Stack
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class Algorithm(
    infixExpression: String,
) {


    val tokens = infixExpression.splitIntoTokens()

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
    var postfixExpr: String = toPostfix()
        private set

    private fun String.isNumeric(): Boolean {
        return this.all { it.isDigit() }
    }

    fun toPostfix(): String {
        //	Выходная строка, содержащая постфиксную запись
        var postfixExpr = ""
        //	Инициализация стека, содержащий операторы в виде символов
        val stack = Stack<String>()
        for (_index in 0..<tokens.size) {
            val token = tokens[_index]
            var op: String = token

            when {
                token.isNumeric() -> {
                    postfixExpr += token + " "
                }

                token == "(" -> {
                    //	Заносим её в стек
                    stack.push(token)
                }

                token == ")" -> {
                    //	Заносим в выходную строку из стека всё вплоть до открывающей скобки
                    while (stack.size > 0 && stack.peek() != "(") {
                        postfixExpr += "${stack.pop()} "
                    }
                    stack.pop()
                }

                operationPriority.containsKey(key = token) -> {
                    //	Если да, то сначала проверяем является ли оператор унарным символом
                    if (op == "-" && (_index == 0 || (_index > 1 && operationPriority.containsKey(tokens[_index - 1]))))
                    //	Если да - преобразуем его в тильду
                        op = "~"

                    //	Заносим в выходную строку все операторы из стека, имеющие более высокий приоритет
                    while (stack.size > 0 && (operationPriority[stack.peek()]!! >= operationPriority[op]!!))
                        postfixExpr += "${stack.pop()} "
                    //	Заносим в стек оператор
                    stack.push(op)
                }
            }
        }

        val restOperators = stack.popAll()
        for (index in restOperators.size - 1 downTo 0) {
            postfixExpr += "${restOperators[index]} "
        }
        return postfixExpr
    }


    private fun String.splitIntoTokens(): DynamicArray<String> {
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

    fun execute(op: String, first: Double, second: Double): Double {
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

    fun calc(): Double {
        //	Стек для хранения чисел
        val stack = Stack<Double>()
        //	Счётчик действий
        var counter: Int = 0
        val tokensInPostf = postfixExpr.splitIntoTokens()
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
                        //	Проверяем, пуст ли стек: если да - задаём нулевое значение,
                        //	если нет - выталкиваем из стека значение
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
        //	По завершению цикла возвращаем результат из стека
        return stack.pop()
    }


}





