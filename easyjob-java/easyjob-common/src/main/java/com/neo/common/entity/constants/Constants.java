package com.neo.common.entity.constants;

import lombok.Data;

/**
 * @ClassName Constants
 * @Description TODO
 * @Author Administrator
 * @Date 2023/9/13 22:23
 */
@Data
public class Constants {
    public static final String CHECK_CODE_KEY = "check_code_key";

    public static final String SESSION_KEY = "session_key";


    public static final Integer LENGTH_8 = 8;

    public static final Integer LENGTH_10 = 10;

    public static final Integer LENGTH_20 = 20;

    public static final Integer LENGTH_30 = 30;

    public static final Integer LENGTH_50 = 50;

    public static final Integer LENGTH_70 = 70;

    public static final Integer LENGTH_150 = 150;

    public static final Integer DEFUALT_ROOT_MENUID = 0;

    public static final String[] EXCEL_TITLE_QUESTION = new String[]{"标题", "分类名称", "难度", "问题描述", "答案分析"};

    public static final String[] EXCEL_TITLE_EXAM_QUESTION = new String[]{"标题", "分类名称", "难度", "问题类型", "问题选项", "问题答案", "问题描述", "答案分析"};


    public static final String TABLE_NAME_QUESTION_INFO = "question_info";

    public static final String TABLE_NAME_SHARE_INFO = "share_info";

    public static final String TRUE_STR = "正确";
    public static final String FALSE_STR = "错误";


    public static final String ZERO_STR = "0";
    public static final String ONE_STR = "1";

    public static final String[] LETTERS = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static final String APP_UPDATE_FOLDER = "/app/";
    public static final String FOLDER_AVATAR = "avatar/";

    public static final String REDIS_KEY_CHECKCODE = "easyjob:check:";

    public static final String JWT_KEY_LOGIN_TOKEN = "jwt_login_token_key";

    public static final Integer JWT_TOKEN_EXPIRES_DAYS = 7;

    public static final String READ_IMAGE_PATH = "/api/file/getImage/";
}
