package servlets.employee;

import Impl.EmployeeDAO;
import dao.EmployeeI;
import dto.Client;
import dto.Employee;
import helpers.DBconnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.EmployeeService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {

    DBconnection dbConnection;
    EmployeeI employeeDAO;
    EmployeeService employeeService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbConnection = DBconnection.getInstance();
        employeeDAO = new EmployeeDAO(dbConnection);
        employeeService = new EmployeeService(employeeDAO);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientIdParam = request.getParameter("number");

        if (clientIdParam != null && !clientIdParam.isEmpty()) {
            try {

                Optional<Employee> employee = employeeService.getEmployeeByMatriculationNumber(clientIdParam);

                if (employee.isPresent()) {
                    request.setAttribute("employee", employee.get());
                } else {
                    request.setAttribute("noEmployeeFound", true);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("invalidEmployeeId", true);
            }
        }else {
            List<Employee> employees = employeeService.getEmployeeList();

            if (employees.isEmpty()) {
                request.setAttribute("noEmployees", true);
            } else {
                request.setAttribute("employees", employees);
            }

        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/employees/employees.jsp");
        dispatcher.forward(request, response);
    }
}
