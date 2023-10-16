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


@WebServlet( urlPatterns = {"/employees" , "/createEmployee", "/registerEmployee","/deleteEmployee" , "/updateEmployee" , "/editEmployee", "/employee"})

public class EmployeeServlet extends HttpServlet {

    DBconnection dbConnection;
    EmployeeI employeeDAO;
    EmployeeService employeeService;

    @Override
    public void init() throws ServletException {
        dbConnection = DBconnection.getInstance();
        employeeDAO = new EmployeeDAO(dbConnection);
        employeeService = new EmployeeService(employeeDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String choix = request.getServletPath();
        switch (choix){
            case "/employees" :
                employees(request,response);
                break;
            case "/employee" :
                employee(request,response);
                break;
            case "/createEmployee" :
                createEmployee(request,response);
                break;
            case "/registerEmployee" :
                register(request,response);
                break;
            case "/deleteEmployee" :
                delete(request,response);
                break;
            case "/updateEmployee" :
                updateEmployee(request,response);
                break;
            case "/editEmployee" :
                editEmployee(request,response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    public void employee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/employees/employees.jsp");
        dispatcher.forward(request, response);
    }

    public void employees(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            List<Employee> employees = employeeService.getEmployeeList();

            if (employees.isEmpty()) {
                request.setAttribute("noEmployees", true);
            } else {
                request.setAttribute("employees", employees);
            }


        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/employees/employees.jsp");
        dispatcher.forward(request, response);
    }

    public void createEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/employees/addEmployee.jsp");
        dispatcher.forward(request, response);
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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

    public void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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

    public void editEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Employee employee = new Employee();
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

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String EmployeeIdParam = request.getParameter("id");

        if (EmployeeIdParam != null && !EmployeeIdParam.isEmpty()) {
            try {
                boolean success = employeeService.deleteEmployee(EmployeeIdParam);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/employees?deleted=true");
                } else {
                    response.sendRedirect(request.getContextPath() + "/employees?deleted=false");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
