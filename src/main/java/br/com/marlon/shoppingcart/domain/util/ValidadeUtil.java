package br.com.marlon.shoppingcart.domain.util;

import br.com.marlon.shoppingcart.domain.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class ValidadeUtil {

    public static void validateIsBlank(String value, String message) {
        if(StringUtils.isBlank(value)) {
            throw new BadRequestException(message);
        }
    }

    public static void validateIsNull(Object value, String message) {
        if(value == null) {
            throw new BadRequestException(message);
        }
    }

    public static void validateIsGreaterThanZero(BigDecimal value, String message) {
        if (BigDecimal.ZERO.compareTo(value) >= 0) {
            throw new BadRequestException(message);
        }
    }

}
