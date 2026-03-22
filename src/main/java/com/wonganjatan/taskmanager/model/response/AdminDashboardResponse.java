package com.wonganjatan.taskmanager.model.response;

public class AdminDashboardResponse {

    private Long totalTasks;
    private String message;

    public AdminDashboardResponse(Long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public  AdminDashboardResponse(String message) {
        this.message = message;
    }

    // Setter
    public void setTotalTasks(Long totalTasks) { this.totalTasks = totalTasks; }
    public void setMessage(String message) { this.message = message; }

    // Getter
    public Long getTotalTasks() { return totalTasks; }
    public String getMessage() { return message; }
}
