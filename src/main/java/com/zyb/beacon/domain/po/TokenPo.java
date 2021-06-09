package com.zyb.beacon.domain.po;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * javadoc TokenPo
 * <p>
 *     token payload
 * <p>
 * @author zhang yebai
 * @date 2021/6/9 3:00 PM
 * @version 1.0.0
 **/
@Data
@Accessors(chain = true)
public class TokenPo {

    private String userId;

    private Long createTimeStamp;
}
