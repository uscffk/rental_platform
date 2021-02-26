package com.ffk.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author 房发科
 * @date 2021/2/24 0:05
 */
@FeignClient(value = "ManufacturerService")
public interface IManufacturerService {

}
