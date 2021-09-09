package com.zyb.beacon.feign;

import com.zyb.beacon.domain.dto.TokenDto;
import com.zyb.beacon.domain.po.TokenPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * javadoc TokenClient
 * <p>
 *     token 交互接口
 *     tc for token center
 *     its a fake client
 * <p>
 * @author zhang yebai
 * @date 2021/6/9 5:20 PM
 * @version 1.0.0
 **/
@FeignClient(value = "tc")
public interface TokenClient {

    /**
     * javadoc findTokenPayload
     * @apiNote 校验token
     *
     * @param dto token信息
     * @return com.zyb.beacon.domain.po.TokenPo
     * @author zhang yebai
     * @date 2021/6/9 5:20 PM
     **/
    @PostMapping(value = "/token/verification")
    TokenPo findTokenPayload(@RequestBody TokenDto dto);
}
