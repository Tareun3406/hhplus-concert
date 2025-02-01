package kr.tareun.concert.common.aop.util

import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext

class CustomSpringELParser {
    fun parseSpELKeys(prefix: String, variableKeys: Array<String>, args: Array<Any>, parameterNames: Array<String>): List<String> {
        val context = StandardEvaluationContext()
        val spELParser = SpelExpressionParser()

        // 메서드 매개변수와 값 매핑
        args.forEachIndexed { index, arg ->
            context.setVariable(parameterNames[index], arg)
        }

        // SpEL 키 파싱 및 결과 생성
        val parsedPrefix = spELParser.parseExpression(prefix).getValue(context, String::class.java)
        return variableKeys.flatMap { key ->
            val parsedKey = spELParser.parseExpression(key).getValue(context, Any::class.java)
            when (parsedKey) {
                is Collection<*> -> parsedKey.map { parsedPrefix + it.toString() } // List나 Set인 경우 각각의 값을 사용
                is Array<*> -> parsedKey.map { parsedPrefix + it.toString() } // 배열인 경우 각각의 값을 사용
                else -> listOf(parsedPrefix + (parsedKey?.toString() ?: key)) // 단일 값 처리
            }
        }
    }
}