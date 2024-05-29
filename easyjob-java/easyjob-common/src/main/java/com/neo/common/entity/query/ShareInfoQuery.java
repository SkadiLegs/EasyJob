package com.neo.common.entity.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareInfoQuery extends BaseParam {


    /**
     * 自增ID
     */
    private Integer shareId;

    /**
     * 标题
     */
    private String title;

    private String titleFuzzy;

    /**
     * 0:无封面 1:横幅  2:小图标
     */
    private Integer coverType;

    /**
     * 封面路径
     */
    private String coverPath;

    private String coverPathFuzzy;

    /**
     * 内容
     */
    private String content;

    private String contentFuzzy;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 0:未发布 1:已发布
     */
    private Integer status;

    /**
     * 用户ID
     */
    private String createUserId;

    private String createUserIdFuzzy;

    /**
     * 姓名
     */
    private String createUserName;

    private String createUserNameFuzzy;

    /**
     * 阅读数量
     */
    private Integer readCount;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 0:内部 1:外部投稿
     */
    private Integer postUserType;

    private Boolean queryTextContent;

    private String[] shareIds;

    private Integer nextType;

    private Integer currentId;


}
