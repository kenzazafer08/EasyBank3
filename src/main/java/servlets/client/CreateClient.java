package servlets.client;

import Impl.ClientDAO;
import dao.ClientI;
import dto.Client;
import helpers.DBconnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ClientService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/createClient")
public class CreateClient extends HttpServlet {
    private final DBconnection dbConnection;
    private final ClientI clientDAO;
    private final ClientService clientService;

    public CreateClient() {
        dbConnection = DBconnection.getInstance();
        clientDAO = new ClientDAO(dbConnection);
        clientService = new ClientService(clientDAO);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/clients/addClient.jsp");
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Client client = new Client();
        client.setFirstName(request.getParameter("firstName"));
        client.setLastName(request.getParameter("lastName"));
        client.setPhone(request.getParameter("phone"));
        client.setAddress(request.getParameter("address"));


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
