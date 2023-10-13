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
import java.util.List;
import java.util.Optional;


@WebServlet("/clients")
public class ClientServlet extends HttpServlet {

    private final DBconnection dbConnection;
    private final ClientI clientDAO;
    private final ClientService clientService;

    public ClientServlet() {
        dbConnection = DBconnection.getInstance();
        clientDAO = new ClientDAO(dbConnection);
        clientService = new ClientService(clientDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientIdParam = request.getParameter("code");

        if (clientIdParam != null && !clientIdParam.isEmpty()) {
            try {

                Optional<Client> client = clientService.getClientByCode(clientIdParam);

                if (client.isPresent()) {
                    request.setAttribute("client", client.get());
                } else {
                    request.setAttribute("noClientFound", true);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("invalidClientId", true);
            }
        } else {
            List<Client> clients = clientService.getClientList();

            if (clients.isEmpty()) {
                request.setAttribute("noClients", true);
            } else {
                request.setAttribute("clients", clients);
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/clients/clients.jsp");
        dispatcher.forward(request, response);
    }

}
