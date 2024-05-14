package com.neo.common.entity.vo.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 用户在线考试
 */
public class AppExamVO implements Serializable {


    private static final long serialVersionUID = 6896657108007075743L;
    /**
     * 自增ID
     */
    private Integer examId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 0:未完成 1:已完成
     */
    private Integer status;

    /**
     * 用时分钟
     */
    private BigDecimal useTimeMin;

    public BigDecimal getUseTimeMin() {
        if (endTime != null && startTime != null) {
            return new BigDecimal(endTime.getTime() - startTime.getTime()).divide(new BigDecimal(1000 * 60), 2, BigDecimal.ROUND_HALF_UP);
        }
        return new BigDecimal(0);
    }

    public void setUseTimeMin(BigDecimal useTimeMin) {
        this.useTimeMin = useTimeMin;
    }

    private List<ExamQuestionVO> examQuestionList;

    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ExamQuestionVO> getExamQuestionList() {
        return examQuestionList;
    }

    public void setExamQuestionList(List<ExamQuestionVO> examQuestionList) {
        this.examQuestionList = examQuestionList;
    }
}
