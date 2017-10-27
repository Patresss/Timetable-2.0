package com.patres.timetable.web.rest.errors

import org.zalando.problem.AbstractThrowableProblem
import org.zalando.problem.Exceptional

import java.util.HashMap

import org.zalando.problem.Status.BAD_REQUEST

/**
 * Custom, parameterized exception, which can be translated on the client side.
 * For example:
 *
 * <pre>
 * throw new CustomParameterizedException(&quot;myCustomError&quot;, &quot;hello&quot;, &quot;world&quot;);
</pre> *
 *
 * Can be translated with:
 *
 * <pre>
 * "error.myCustomError" :  "The server says {{param0}} to {{param1}}"
</pre> *
 */
class CustomParameterizedException : AbstractThrowableProblem {

    // Workaround for kotlin problem with abstract method
    override fun getCause(): Exceptional? {
        return super.cause
    }

    constructor(message: String, vararg params: String) : this(message, toParamMap(*params))
    constructor(message: String, paramMap: Map<String, Any>) : super(ErrorConstants.PARAMETERIZED_TYPE, "Parameterized Exception", BAD_REQUEST, null, null, null, toProblemParameters(message, paramMap))

    companion object {

        private val serialVersionUID = 1L

        private val PARAM = "param"

        fun toParamMap(vararg params: String?): Map<String, Any> {
            val paramMap = HashMap<String, Any>()
            if (params.isNotEmpty()) {
                for (i in params.indices) {
                    paramMap.put(PARAM + i, params[i]?: "")
                }
            }
            return paramMap
        }

        fun toProblemParameters(message: String, paramMap: Map<String, Any>): Map<String, Any> {
            val parameters = HashMap<String, Any>()
            parameters.put("message", message)
            parameters.put("params", paramMap)
            return parameters
        }
    }
}
