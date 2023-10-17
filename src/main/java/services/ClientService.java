package services;

import dto.Client;
import dao.ClientI;
import Impl.ClientDAO;
import helpers.DBconnection;

import java.util.List;
import java.util.Optional;

public class ClientService {
    DBconnection dbConnection = DBconnection.getInstance();
    ClientI clientDAO = new ClientDAO(dbConnection);

    public ClientService() {

    }

    public Optional<Client> addClient(Client client) {
        return clientDAO.add(client);
    }

    public Optional<Client> getClientByCode(String code) {
        Optional<Client> client = clientDAO.searchByCode(code);
        if(client.isPresent()){
            if(!client.get().getDeleted()){
                return client;
            }
        }
        return Optional.empty();
    }

    public boolean deleteClient(String code) {
        return clientDAO.delete(code);
    }

    public List<Client> getClientList() {
        return clientDAO.showList();
    }

    public Optional<Client> updateClient(Client client) {
        return clientDAO.update(client);
    }
}
