package com.ffk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 房发科
 * @date 2021/2/26 20:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    /**
     * 钱包地址
     */
    String walletAddress;

    /**
     * 以太坊账户密码
     */
    String pwd;

    /**
     * 账户文件
     */
    String accountFile;

    /**
     * 合约地址
     */
    String contractAddress;
}
