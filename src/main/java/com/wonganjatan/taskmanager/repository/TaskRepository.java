package com.wonganjatan.taskmanager.repository;

import com.wonganjatan.taskmanager.model.entity.Task;
import com.wonganjatan.taskmanager.model.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    long countByAssignedUserId(Long id);

    @Modifying  
    @Query("UPDATE Task t SET t.status = :status WHERE t.id = :id")
    void updateTaskStatus(@Param("id") Long id, @Param("status") Status status);
}
