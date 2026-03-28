package com.wonganjatan.taskmanager.model.dto;

import com.wonganjatan.taskmanager.model.entity.enums.Status;

public class UpdateStatus {

    private Status status;

    // Setter
    public void setStatus(Status status) { this.status = status; }

    // Getter
    public Status getStatus() { return this.status; }
}
