package com.springshopecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {

    private List<Long> manufacturers;
    private List<String> cpus;
    private List<String> rams;
    private  List<String> colors;
    private List<String> screenSizes;


    public boolean isEmptyLists() {
        return manufacturers.size() == 0 && cpus .size() == 0 && rams .size() == 0 && colors .size() == 0 &&  screenSizes.size() == 0;
    }
}
