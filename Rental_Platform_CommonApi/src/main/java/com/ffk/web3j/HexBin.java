package com.ffk.web3j;

/**
 * @author 房发科
 * @date 2021/1/31 22:25
 */
public final class HexBin {
    //字符串转十六进制
    public static String toChineseHex(String s)
    {
        String ss = s;
        byte[] bt = new byte[0];

        try {
            bt = ss.getBytes("UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        String s1 = "";
        for (int i = 0; i < bt.length; i++)
        {
            String tempStr = Integer.toHexString(bt[i]);
            if (tempStr.length() > 2)
                tempStr = tempStr.substring(tempStr.length() - 2);
            s1 = s1 + tempStr + "";
        }
        return s1.toUpperCase();
    }

    // 转化十六进制编码为字符串
    public static String toStringHex(String s) throws Exception {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(
                    i * 2, i * 2 + 2), 16));

        }

        // UTF-16le:Not
        s = new String(baKeyword, "utf-8");

        return s;
    }
}

