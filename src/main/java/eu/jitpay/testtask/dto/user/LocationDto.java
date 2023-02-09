package eu.jitpay.testtask.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LocationDto {

    private BigDecimal latitude;
    private BigDecimal longitude;
}
