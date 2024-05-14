package com.neo.common.uilts;


import com.neo.common.entity.constants.Constants;
import com.neo.common.entity.enums.VerifyRegexEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtils {

    public static boolean verify(String regex, String value) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean verify(VerifyRegexEnum regex, String value) {
        return verify(regex.getRegex(), value);
    }

    public static boolean checkAccount(String account) {
        if (StringUtils.isEmpty(account) || account.length() < Constants.LENGTH_8 || account.length() > Constants.LENGTH_20) {
            return false;
        }
        return verify(VerifyRegexEnum.ACCOUNT, account);
    }

    public static boolean checkPassWord(String password) {
        return verify(VerifyRegexEnum.PASSWORD, password);
    }
}

