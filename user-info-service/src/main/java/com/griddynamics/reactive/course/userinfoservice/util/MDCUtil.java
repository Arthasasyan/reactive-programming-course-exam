package com.griddynamics.reactive.course.userinfoservice.util;

import org.slf4j.MDC;
import reactor.util.context.ContextView;

import java.util.HashMap;
import java.util.Map;

public class MDCUtil {

    public static final String REQUEST_ID_MDC_VALUE = "requestId";

    public static void logWithContext(ContextView context, Runnable logStatement) {
        final Map<String, String> mdcTracingValues = extractTracingValuesFromContext(context);
        try {
            mdcTracingValues.forEach(MDC::put);
            logStatement.run();
        } finally {
            mdcTracingValues.keySet().forEach(MDC::remove);
        }
    }

    private static Map<String, String> extractTracingValuesFromContext(ContextView context) {
        final Map<String, String> result = new HashMap<>();
        result.put(REQUEST_ID_MDC_VALUE, context.get(REQUEST_ID_MDC_VALUE));
        return result;
    }
}
