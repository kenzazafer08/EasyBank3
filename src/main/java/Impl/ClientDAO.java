package Impl;

import dao.ClientI;
import dto.Client;
import helpers.DBconnection;
import helpers.helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ClientDAO implements ClientI {
    private final DBconnection dbConnection;

    public ClientDAO(DBconnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public Optional<Client> add(Client client) {
        Connection connection = dbConnection.getConnection();

        String insertPersonQuery = "INSERT INTO person (first_name, last_name, phone, address) " +
                "VALUES (?, ?, ?, ?)";

        String insertClientQuery = "INSERT INTO client (code, person_id) " +
                "VALUES (?, ?)";

        try {
            PreparedStatement personStatement = connection.prepareStatement(insertPersonQuery, Statement.RETURN_GENERATED_KEYS);
            personStatement.setString(1, client.getFirstName());
            personStatement.setString(2, client.getLastName());
            personStatement.setString(3, client.getPhone());
            personStatement.setString(4, client.getAddress());

            int rowsInserted = personStatement.executeUpdate();
            if (rowsInserted == 0) {
                System.out.println("Failed to insert Person record.");
                return null;
            }

            ResultSet generatedKeys = personStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int personId = generatedKeys.getInt(1);
                String clientCode = helper.generateClientCode(5);
                client.setCode(clientCode);
                PreparedStatement clientStatement = connection.prepareStatement(insertClientQuery);
                clientStatement.setString(1, client.getCode());
                clientStatement.setInt(2, personId);

                int clientRowsInserted = clientStatement.executeUpdate();
                if (clientRowsInserted > 0) {
                    return Optional.of(client);
                } else {
                    return Optional.empty();
                }
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.println("Error adding client: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<Client> searchByCode(String code) {
        String sql = "SELECT c.*, p.* FROM client c " +
                "INNER JOIN person p ON c.person_id = p.id " +
                "WHERE c.code = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, code);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = new Client();
                    client.setCode(resultSet.getString("code"));
                    client.setFirstName(resultSet.getString("first_name"));
                    client.setLastName(resultSet.getString("last_name"));
                    client.setPhone(resultSet.getString("phone"));
                    client.setAddress(resultSet.getString("address"));
                    client.setDeleted(resultSet.getBoolean("deleted"));
                    client.setId(resultSet.getInt("person_id"));
                    return Optional.of(client);                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(String id) {
        String sql = "UPDATE client SET deleted = ? WHERE code = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setBoolean(1, true);

            preparedStatement.setString(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }    }

    @Override
    public List<Client> showList() {
        List<Client> clientList = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection()) {
            String query = "SELECT c.*, p.*" +
                    "FROM client AS c " +
                    "INNER JOIN person AS p ON c.person_id = p.id";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    if(!resultSet.getBoolean("deleted")){
                    Client client = new Client();
                    client.setCode(resultSet.getString("code"));
                    client.setFirstName(resultSet.getString("first_name"));
                    client.setLastName(resultSet.getString("last_name"));
                    client.setPhone(resultSet.getString("phone"));
                    client.setAddress(resultSet.getString("address"));
                    client.setDeleted(resultSet.getBoolean("deleted"));
                    clientList.add(client);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        return clientList;
    }

    @Override
    public Optional<Client> update(Client client) {
        Connection connection = dbConnection.getConnection();
        String updatePersonQuery = "UPDATE person SET first_name = ?, last_name = ?, phone = ?, address = ? WHERE id = ?";

        try {
            Optional<Client> clientId = searchByCode(client.getCode());

            if (clientId == null) {
                return null;
            }

            PreparedStatement personStatement = connection.prepareStatement(updatePersonQuery);
            personStatement.setString(1, client.getFirstName());
            personStatement.setString(2, client.getLastName());
            personStatement.setString(3, client.getPhone());
            personStatement.setString(4, client.getAddress());
            personStatement.setInt(5, clientId.get().getId());

            int rowsUpdatedPerson = personStatement.executeUpdate();

            if (rowsUpdatedPerson > 0 ) {
                return Optional.of(client);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.println("Error updating client: " + e.getMessage());
            return Optional.empty();
        }
    }
}
