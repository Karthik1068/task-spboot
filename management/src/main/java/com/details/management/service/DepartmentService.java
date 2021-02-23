package com.details.management.service;

import com.details.management.exception.DataResourceNotFoundException;
import com.details.management.model.Department;
import com.details.management.dao.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Transactional
    public Department updateDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department getDepartmentByDepartmentName(String departmentName) throws DataResourceNotFoundException {
        return departmentRepository.findFirstByName(departmentName).orElseThrow(() -> new DataResourceNotFoundException("Requested department not found"));
    }

    @Transactional
    public void deleteDepartmentByDepartmentName(String departmentName) {
        departmentRepository.deleteByName(departmentName);
    }

    public List<Department> getAllDepartments() {
        return (List<Department>) departmentRepository.findAll();
    }
}