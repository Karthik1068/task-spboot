package com.details.management.dao;

import com.details.management.model.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    Optional<Department> findFirstByName(String departmentName);
    void deleteByName(String departmentName);
}
