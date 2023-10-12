package servlets;

import Impl.ClientDAO;
import dto.Client;
import helpers.DBconnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ClientService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/updateClient")
public class UpdateClient extends HttpServlet {
    Client client = new Client();
    DBconnection dbConnection = DBconnection.getInstance();
    ClientDAO clientDAO = new ClientDAO(dbConnection);
    ClientService clientService = new ClientService(clientDAO);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientIdParam = request.getParameter("id");

        if (clientIdParam != null && !clientIdParam.isEmpty()) {
            try {
                Optional<Client> client = clientService.getClientByCode(clientIdParam);
                if (client.isPresent()) {
                    request.setAttribute("client", client.get());
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/updateClient.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        client.setCode(request.getParameter("id"));
        client.setFirstName(request.getParameter("firstName"));
        client.setLastName(request.getParameter("lastName"));
        client.setPhone(request.getParameter("phone"));
        client.setAddress(request.getParameter("address"));

        if (client.getCode() != null && !client.getCode().isEmpty()) {
            try {

                Optional<Client> success = clientService.updateClient(client);

                if (success.isPresent()) {
                    response.sendRedirect(request.getContextPath() + "/clients?updateSuccess=true");
                } else {
                    response.sendRedirect(request.getContextPath() + "/clients?updateSuccess=false");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("error.jsp");
            }
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
