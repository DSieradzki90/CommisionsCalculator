package com.gft.pre;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
public class Price {

    @CsvBindByPosition(position = 0)
    private Long id;
    @CsvBindByPosition(position = 1)
    private String currency;
    @CsvBindByPosition(position = 2)
    private BigDecimal bid;
    @CsvBindByPosition(position = 3)
    private BigDecimal ask;
    @CsvBindByPosition(position = 4)
    private String timestamp;

}
