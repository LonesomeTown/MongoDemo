package com.smu.homework.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Food
 *
 * @author T.W 10/14/22
 */
@Data
@Accessors(chain = true)
public class Food {
    private String name;
    private String origin;
}
