package servlets;

import Impl.ClientDAO;
import dao.ClientI;
import helpers.DBconnection;
import dto.Client;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;
import services.ClientService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/createClient")
public class CreateClient extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/addClient.jsp");
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = new Client();
        client.setFirstName(request.getParameter("firstName"));
        client.setLastName(request.getParameter("lastName"));
        client.setPhone(request.getParameter("phone"));
        client.setAddress(request.getParameter("address"));


        DBconnection dbConnection = DBconnection.getInstance();
        ClientDAO clientDAO = new ClientDAO(dbConnection);
        ClientService clientService = new ClientService(clientDAO);

        Optional<Client> success = clientService.addClient(client);
        if (success.isPresent()) {
            try {
                response.sendRedirect(request.getContextPath() + "/clients?success=true");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/clients?success=false");
        }
    }
}
