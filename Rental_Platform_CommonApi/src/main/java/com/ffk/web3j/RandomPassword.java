package com.ffk.web3j;

import java.util.Random;

/**
 * @author 房发科
 * @date 2021/1/31 22:27
 */
public class RandomPassword{

    public String generatePassword (int length) {
        // 最终生成的密码
        String password = "";
        Random random = new Random();
        for (int i = 0; i < length; i ++) {
        // 随机生成0或1，用来确定是当前使用数字还是字母 (0则输出数字，1则输出字母)
        int charOrNum = random.nextInt(2);
        if (charOrNum == 1) {
        // 随机生成0或1，用来判断是大写字母还是小写字母 (0则输出小写字母，1则输出大写字母)
        int temp = random.nextInt(2) == 1 ? 65 : 97;
        password += (char) (random.nextInt(26) + temp);
        } else {
        // 生成随机数字
        password += random.nextInt(10);
        }
        }
        return password;
        }
        // length表示密码长度
}



