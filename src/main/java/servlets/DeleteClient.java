package servlets;


import Impl.ClientDAO;
import dao.ClientI;
import helpers.DBconnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ClientService;

import java.io.IOException;

@WebServlet(name = "DeleteClient", urlPatterns = {"/deleteClient"}, initParams = {
        @WebInitParam(name = "allowedMethods", value = "DELETE")
})
public class DeleteClient extends HttpServlet {

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
        String clientIdParam = request.getParameter("id");

        if (clientIdParam != null && !clientIdParam.isEmpty()) {
            try {
                DBconnection dbConnection = DBconnection.getInstance();
                ClientI clientDAO = new ClientDAO(dbConnection);
                ClientService clientService = new ClientService(clientDAO);
                boolean success = clientService.deleteClient(clientIdParam);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/clients?deleted=true");
                } else {
                    response.sendRedirect(request.getContextPath() + "/clients?deleted=false");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
