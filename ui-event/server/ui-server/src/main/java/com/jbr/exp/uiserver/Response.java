package com.jbr.exp.uiserver;

import java.time.LocalDateTime;

public class Response {
    private LocalDateTime updateTime;

    public Response(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
