package com.distributedcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ExpressionEvaluator {
    private static final Pattern EXPRESSION_PATTERN =
            Pattern.compile("\\s*([+-]?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*([+-]?\\d+(?:\\.\\d+)?)\\s*");

    private ExpressionEvaluator() {
    }

    public static String evaluate(String expression) {
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("formato invalido. Use: numero operador numero");
        }

        BigDecimal left = new BigDecimal(matcher.group(1));
        String operator = matcher.group(2);
        BigDecimal right = new BigDecimal(matcher.group(3));

        BigDecimal result;
        switch (operator) {
            case "+":
                result = left.add(right);
                break;
            case "-":
                result = left.subtract(right);
                break;
            case "*":
                result = left.multiply(right);
                break;
            case "/":
                result = divide(left, right);
                break;
            default:
                throw new IllegalArgumentException("operador nao suportado.");
        }

        return result.stripTrailingZeros().toPlainString();
    }

    private static BigDecimal divide(BigDecimal left, BigDecimal right) {
        if (right.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("divisao por zero nao e permitida.");
        }

        return left.divide(right, 10, RoundingMode.HALF_UP);
    }
}
