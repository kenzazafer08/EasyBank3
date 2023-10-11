package dao;

import dto.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeI {
    Optional<Employee> add(Employee employee);
    Optional<Employee> searchByMatricul(String matriculationNumber);
    boolean delete(String id);
    List<Employee> showList();
    Optional<Employee> update(Employee updatedEmployee);
}
