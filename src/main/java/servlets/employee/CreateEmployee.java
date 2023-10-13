package servlets.employee;

import Impl.ClientDAO;
import Impl.EmployeeDAO;
import dao.ClientI;
import dao.EmployeeI;
import dto.Employee;
import helpers.DBconnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ClientService;
import services.EmployeeService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/createEmployee")
public class CreateEmployee extends HttpServlet {
    private final DBconnection dbConnection;
    private final EmployeeI employeeDAO;
    private final EmployeeService employeeService;

    public CreateEmployee() {
        dbConnection = DBconnection.getInstance();
        employeeDAO = new EmployeeDAO(dbConnection);
        employeeService = new EmployeeService(employeeDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/employees/addEmployee.jsp");
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employee employee = new Employee();
        employee.setFirstName(request.getParameter("firstName"));
        employee.setLastName(request.getParameter("lastName"));
        employee.setPhone(request.getParameter("phone"));
        employee.setEmail(request.getParameter("email"));
        employee.setAddress(request.getParameter("address"));

        Optional<Employee> success = employeeService.addEmployee(employee);
        if (success.isPresent()) {
            try {
                response.sendRedirect(request.getContextPath() + "/employees?success=true");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/employees?success=false");
        }
    }
}
