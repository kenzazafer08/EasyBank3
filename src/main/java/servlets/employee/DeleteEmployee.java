package servlets.employee;


import Impl.EmployeeDAO;
import dao.EmployeeI;
import helpers.DBconnection;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.EmployeeService;

import java.io.IOException;

@WebServlet("/deleteEmployee")
public class DeleteEmployee extends HttpServlet {
    private final DBconnection dbConnection;
    private final EmployeeI employeeDAO;
    private final EmployeeService employeeService;

    public DeleteEmployee() {
        dbConnection = DBconnection.getInstance();
        employeeDAO = new EmployeeDAO(dbConnection);
        employeeService = new EmployeeService(employeeDAO);
    }
    protected void doGet(HttpServletRequest request , HttpServletResponse response){
        try {
            doDelete(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
