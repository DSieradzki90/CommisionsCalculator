package com.gft.pre;

import com.opencsv.bean.CsvToBeanFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmptyLineFilter implements CsvToBeanFilter {

    public boolean allowLine(String[] line) {
        boolean blankLine = line.length == 1;
        return !blankLine;
    }
}