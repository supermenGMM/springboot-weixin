package com.mm;

import org.junit.Test;
import org.yaml.snakeyaml.util.UriEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class junitTest {
    @Test
    public void testPhone() {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        String mobile = "18511352466";
//        boolean matches = Pattern.matches(regex, "13011352466");
//        System.out.println(matches);
        Pattern compile = Pattern.compile(regex);
        Matcher m = compile.matcher("18511352463");
        System.out.println(m.matches());

        isMobileNO(mobile);

    }
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches()+"---");
        return m.matches();
    }


    @Test
    public void test() {
        String encode = UriEncoder.encode("http://supermeng.natapp1.cc/sell/winxin/auth?code=222&state=22");
        System.out.println(encode);
    }

    @Test
    public void testFormat() {
        System.out.println(String.format("aaa_%s","_bfd"));
    }
}