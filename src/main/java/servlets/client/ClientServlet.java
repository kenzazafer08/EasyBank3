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


@WebServlet( urlPatterns = {"/clients" , "/createClient", "/register","/deleteClient" , "/updateClient" , "/edit", "/client"})
public class ClientServlet extends HttpServlet {

    ClientService clientService;
    @Override
    public void init() throws ServletException {
        clientService = new ClientService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String choix = request.getServletPath();
        switch (choix){
            case "/clients" :
                clients(request,response);
                break;
            case "/client" :
                client(request,response);
                break;
            case "/createClient" :
                create(request,response);
                break;
            case "/register" :
                register(request,response);
                break;
            case "/deleteClient" :
                delete(request,response);
                break;
            case "/updateClient" :
                update(request,response);
                break;
            case "/edit" :
                edit(request,response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    public void clients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> clients = clientService.getClientList();

        if (clients.isEmpty()) {
            request.setAttribute("noClients", true);
        } else {
            request.setAttribute("clients", clients);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/clients/clients.jsp");
        dispatcher.forward(request, response);
    }

    public void client(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/clients/clients.jsp");
        dispatcher.forward(request, response);
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/clients/addClient.jsp");
        dispatcher.forward(request, response);

    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Views/clients/updateClient.jsp");
        dispatcher.forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        Client client = new Client();
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

    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
