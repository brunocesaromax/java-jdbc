package dao;

import conexaojdbc.SingleConnection;
import model.Userposjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserposDAO {

    private Connection connection;

    public UserposDAO() { // Ao inicializar objeto um conexão com o banco é criada
        this.connection = SingleConnection.getConnection();
    }

    public void salvar(Userposjava userposjava){

        try {
            String sql = "insert into userposjava (id, nome, email) values (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userposjava.getId());
            statement.setString(2, userposjava.getNome());
            statement.setString(3, userposjava.getEmail());
            statement.execute();
            connection.commit();// Salva no banco


        } catch (SQLException e) {
            try {
                connection.rollback();// Reverte operação
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }


    }

}
