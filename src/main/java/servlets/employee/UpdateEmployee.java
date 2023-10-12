package servlets.employee;

import Impl.EmployeeDAO;
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
import services.EmployeeService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/updateEmployee")
public class UpdateEmployee extends HttpServlet {
    DBconnection dbConnection;
    EmployeeI employeeDAO;
    EmployeeService employeeService;

    Employee employee;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        employee = new Employee();
        dbConnection = DBconnection.getInstance();
        employeeDAO = new EmployeeDAO(dbConnection);
        employeeService = new EmployeeService(employeeDAO);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeIdParam = request.getParameter("id");

        if (employeeIdParam != null && !employeeIdParam.isEmpty()) {
            try {
                Optional<Employee> employee = employeeService.getEmployeeByMatriculationNumber(employeeIdParam);
                if (employee.isPresent()) {
                    request.setAttribute("employee", employee.get());
                } else {
                    response.sendRedirect("error.jsp");
                    return;
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("error.jsp");
                return;
            }
        } else {
            response.sendRedirect("error.jsp");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/employees/updateEmployee.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        employee.setNumber(request.getParameter("id"));
        employee.setFirstName(request.getParameter("firstName"));
        employee.setLastName(request.getParameter("lastName"));
        employee.setPhone(request.getParameter("phone"));
        employee.setEmail(request.getParameter("email"));
        employee.setAddress(request.getParameter("address"));

        if (employee.getNumber() != null && !employee.getNumber().isEmpty()) {
            try {

                Optional<Employee> success = employeeService.updateEmployee(employee);

                if (success.isPresent()) {
                    response.sendRedirect(request.getContextPath() + "/employees?updateSuccess=true");
                } else {
                    response.sendRedirect(request.getContextPath() + "/employees?updateSuccess=false");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("error.jsp");
            }
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
