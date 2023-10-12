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



@WebServlet("/clients")
public class ClientServlet extends HttpServlet {

    DBconnection dbConnection;
    ClientI clientDAO;
    ClientService clientService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbConnection = DBconnection.getInstance();
        clientDAO = new ClientDAO(dbConnection);
        clientService = new ClientService(clientDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> clients = clientService.getClientList();

        if (clients.isEmpty()) {
            request.setAttribute("noClients", true);
        } else {
            request.setAttribute("clients", clients);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/clients/clients.jsp");
        dispatcher.forward(request, response);
    }
}
