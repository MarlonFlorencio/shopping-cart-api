package br.com.marlon.shoppingcart.domain.util;

import br.com.marlon.shoppingcart.domain.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;

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

}
