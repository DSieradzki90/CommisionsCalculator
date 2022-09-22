package com.gft.pre;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class MessageReceiver {

    private final RestTemplate restTemplate;

    List<Price> onMessage(String message) {
        StringReader stringReader = new StringReader(message);
        List<Price> beans = new CsvToBeanBuilder(stringReader)
                .withType(Price.class)
                .withFilter(new EmptyLineFilter())
                .build()
                .parse();

        Collections.reverse(beans);
        Set<String> nameSet = new HashSet<>();
        List<Price> priceList = beans.stream()
                .filter(e -> nameSet.add(e.getCurrency()))
                .map(this::calculatePriceAfterCommissions)
                .collect(Collectors.toList());
        priceList.forEach(System.out::println);

        restTemplate.postForObject(
                "http://localhost:8080/prices/",
                priceList,
                Price.class);
        return priceList;
    }

    Price calculatePriceAfterCommissions(Price price) {
        price.setBid(price.getBid().add(price.getBid().multiply(BigDecimal.valueOf(0.01))));
        price.setAsk(price.getAsk().subtract(price.getAsk().multiply(BigDecimal.valueOf(0.01))));
        return price;
    }
}
