package com.akun.demo.utils;

/**
 * @Package: com.akun.demo
 * @ClassName: suanfa
 * @Author: akun
 * @CreateTime: 2022/4/24 21:49
 * @Description:
 */
public class suanfa {
    public static void main(String[] args) {
        String result = addFunWithStr("1b","2X");
        System.out.println("result = " + result);
    }
    public static int getIntFromChar(char ch){
        int ret =-1;
        if (ch >= '0' && ch <= '9'){
            ret = ch - '0';
        }else if (ch>= 'a' && ch <= 'z'){
            ret = (ch -'a')+10;
        }
        return ret;
    }
    public  static  String addFunWithStr(String param1,String param2){
        StringBuffer stringBuffer = new StringBuffer();
        String symbol = "0123456789abcdefghijklmnopqrstuvwxyz";
        int param1Len = param1.length();
        int param2Len = param2.length();
        int i = param1Len - 1;
        int j = param2Len - 1;
        if (i<0||j<0){
            return null;
        }
        int temp=0;
        while(i>=0&&j>=0){
            char ch_1 = param1.charAt(i);
            char ch_2 = param2.charAt(j);
            int v1 = getIntFromChar(ch_1);
            int v2 = getIntFromChar(ch_2);
            int ret = v1 + v2;
            if (ret >=36){
                int index = ret - 36 + temp;
                char sv = symbol.charAt(index);
                stringBuffer.append(sv);
                temp = 1;//进位
            }else{
                int index = ret + temp;
                char sv = symbol.charAt(index);
                stringBuffer.append(sv);
                temp = 0;
            }
            i--;
            j--;
        }
        while (i>=0){
            char ch_2 =param2.charAt(i);
            stringBuffer.append(ch_2);
            j++;
        }
        StringBuffer result = stringBuffer.reverse();
        return result.toString();
    }
}
