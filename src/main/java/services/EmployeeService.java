package services;

import Impl.EmployeeDAO;
import dto.Employee;
import dao.EmployeeI;
import helpers.DBconnection;

import java.util.List;
import java.util.Optional;

public class EmployeeService {
    DBconnection dbConnection = DBconnection.getInstance();
    EmployeeI employeeDAO = new EmployeeDAO(dbConnection);

    public EmployeeService() {

    }

    public Optional<Employee> addEmployee(Employee employee) {
        Optional<Employee> e = employeeDAO.searchByMatricul(employee.getNumber());
        if(e.isPresent()){
            return Optional.empty();
        }
        return employeeDAO.add(employee);
    }

    public Optional<Employee> getEmployeeByMatriculationNumber(String matriculationNumber) {
        Optional<Employee> employee = employeeDAO.searchByMatricul(matriculationNumber);
        if(employee.isPresent()){
           if(!employee.get().getDeleted()){
               return employee;
           }
        }
        return Optional.empty();
    }

    public boolean deleteEmployee(String matriculationNumber) {
        return employeeDAO.delete(matriculationNumber);
    }

    public List<Employee> getEmployeeList() {
        return employeeDAO.showList();
    }

    public Optional<Employee> updateEmployee(Employee updatedEmployee) {
        return employeeDAO.update(updatedEmployee);
    }
}
