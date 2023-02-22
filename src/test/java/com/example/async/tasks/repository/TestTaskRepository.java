package com.example.async.tasks.repository;

import com.example.async.tasks.entity.Status;
import com.example.async.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTaskRepository extends JpaRepository<Task, Long> {


    @Query("""
            SELECT t.status
            FROM Task t
            WHERE t.id = :id
            """)
    Status findStatusOf(Long id);
}
