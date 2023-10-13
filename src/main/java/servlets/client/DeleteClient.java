package servlets.client;


import Impl.ClientDAO;
import dao.ClientI;
import helpers.DBconnection;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ClientService;

import java.io.IOException;

@WebServlet("/deleteClient")
public class DeleteClient extends HttpServlet {
    private final DBconnection dbConnection;
    private final ClientI clientDAO;
    private final ClientService clientService;

    public DeleteClient() {
        dbConnection = DBconnection.getInstance();
        clientDAO = new ClientDAO(dbConnection);
        clientService = new ClientService(clientDAO);
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
        String clientIdParam = request.getParameter("id");

        if (clientIdParam != null && !clientIdParam.isEmpty()) {
            try {
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
