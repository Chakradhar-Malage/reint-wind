package com.Chakradhar.reint_wind.model;

import java.util.List;
import lombok.Data;

@Data
public class ElexonResponse<T> {
    private List<T> data;
}