package com.github.tanxinzheng.framework.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by jengt_000 on 2014/12/28.
 */
public class BaseModel implements Serializable {

    /** 创建人 */
    private String createdUserId;
    /** 创建时间 */
    private LocalDateTime createdTime;
    /** 更新人 */
    private String updatedUserId;
    /** 更新时间 */
    private LocalDateTime updatedTime;
    /** 数据版本号 */
    private Integer dataVersion;

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(String updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Integer dataVersion) {
        this.dataVersion = dataVersion;
    }

    public <T> T toDO(Class<T> clazz){
        return null;
    }

    public <T> T toVO(Class<T> clazz){
        return null;
    }




}
