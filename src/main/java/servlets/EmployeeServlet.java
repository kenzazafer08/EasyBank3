package servlets;

import Impl.ClientDAO;
import Impl.EmployeeDAO;
import dao.ClientI;
import dao.EmployeeI;
import dto.Client;
import dto.Employee;
import helpers.DBconnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ClientService;
import services.EmployeeService;

import java.io.IOException;
import java.util.List;



@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBconnection dbConnection = DBconnection.getInstance();
        EmployeeI employeeDAO = new EmployeeDAO(dbConnection);
        EmployeeService employeeService = new EmployeeService(employeeDAO);
        List<Employee> employees = employeeService.getEmployeeList();

        if (employees.isEmpty()) {
            request.setAttribute("noEmployees", true);
        } else {
            request.setAttribute("employees", employees);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/employees.jsp");
        dispatcher.forward(request, response);
    }
}
