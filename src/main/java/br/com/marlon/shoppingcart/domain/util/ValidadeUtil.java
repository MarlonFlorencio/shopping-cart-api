package br.com.marlon.shoppingcart.domain.util;

import br.com.marlon.shoppingcart.domain.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

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

    public static void validateEmailIsValid(String value, String message) {
        if (!getEmailRegex().matcher(value).find()) {
            throw new BadRequestException(message);
        }
    }

    private static Pattern getEmailRegex()  {
        return Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    }
}
