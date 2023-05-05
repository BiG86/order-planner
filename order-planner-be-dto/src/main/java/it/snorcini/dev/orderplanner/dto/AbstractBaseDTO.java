package it.snorcini.dev.orderplanner.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class AbstractBaseDTO {

    protected String toIndentedString(final Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
