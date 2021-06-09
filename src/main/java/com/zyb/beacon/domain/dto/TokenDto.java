package com.zyb.beacon.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * javadoc TokenDto
 * <p>
 *
 * <p>
 * @author zhang yebai
 * @date 2021/6/9 5:18 PM
 * @version 1.0.0
 **/
@Data
@Accessors(chain = true)
public class TokenDto {

    private String token;
}
