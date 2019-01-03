package dao;

import conexaojdbc.SingleConnection;
import model.BeanUserTelefone;
import model.Telefone;
import model.Userposjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserposDAO {

    private Connection connection;

    public UserposDAO() { // Ao inicializar objeto um conexão com o banco é criada
        this.connection = SingleConnection.getConnection();
    }

    public void salvar(Userposjava userposjava) {

        try {
            String sql = "insert into userposjava (nome, email) values (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userposjava.getNome());
            statement.setString(2, userposjava.getEmail());
            statement.execute();
            connection.commit();// Salva no banco


        } catch (SQLException e) {
            try {
                connection.rollback();// Reverte operação realizada
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }


    }

    public void salvarTelefone(Telefone telefone) {

        try {

            String sql = "insert into telefone (numero, tipo, userposjava_id) values (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, telefone.getNumero());
            statement.setString(2, telefone.getTipo());
            statement.setLong(3, telefone.getUserposjava_id());
            statement.execute();
            connection.commit();// Salva no banco


        } catch (SQLException e) {
            try {
                connection.rollback();// Reverte operação realizada
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }


    }

    public List<Userposjava> listar() throws Exception {

        List<Userposjava> list = new ArrayList<Userposjava>();

        String sql = "select * from userposjava";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) { // Enquanto houver novos objetos no resultado fazer iteração

            Userposjava userposjava = new Userposjava();
            userposjava.setId(resultado.getLong("id"));
            userposjava.setNome(resultado.getString("nome"));
            userposjava.setEmail(resultado.getString("email"));
            list.add(userposjava);
        }

        return list;

    }

    public Userposjava buscarById(Long id) throws Exception {

        Userposjava retorno = new Userposjava();

        String sql = "select * from userposjava where id = " + id;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultado = statement.executeQuery();

        while (resultado.next()) { // Retorna apenas um ou nenhum objeto

            retorno.setId(resultado.getLong("id"));
            retorno.setNome(resultado.getString("nome"));
            retorno.setEmail(resultado.getString("email"));
        }

        return retorno;

    }

    public void atualizar(Userposjava userposjava) {

        try {

            String sql = "update userposjava set nome = ? where id = " + userposjava.getId();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userposjava.getNome());
            statement.execute();
            connection.commit();

        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void deletarById(Long id) {

        try {

            String sql = "delete from userposjava where id = " + id;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void deletarTelefonesByIdUsuario(Long idUsuario) {

        try {

            String sqlTelefone = "delete from telefone where userposjava_id = " + idUsuario;
            String sqlUser = "delete from userposjava where id = " + idUsuario;
            PreparedStatement statement;

            //Primeiro deleta o objeto filho (Telefone)
            statement = connection.prepareStatement(sqlTelefone);
            statement.executeUpdate();
            connection.commit();

            //Posteriormente deleta a classe pai (userposjava)
            statement = connection.prepareStatement(sqlUser);
            statement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {

            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }


    }

    public List<BeanUserTelefone> listaUserTelefone(Long idUserposjava) {

        List<BeanUserTelefone> beanUserTelefones = new ArrayList<BeanUserTelefone>();

        String sql = " select nome, numero, email from telefone as t ";
        sql += " inner join userposjava as u ";
        sql += " on t.userposjava_id = u.id ";
        sql += " where u.id = " + idUserposjava;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultado = statement.executeQuery();

            while (resultado.next()){
                BeanUserTelefone beanUserTelefone = new BeanUserTelefone();
                beanUserTelefone.setEmail(resultado.getString("email"));
                beanUserTelefone.setNome(resultado.getString("nome"));
                beanUserTelefone.setNumero(resultado.getString("numero"));
                beanUserTelefones.add(beanUserTelefone);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return beanUserTelefones;
    }

}
