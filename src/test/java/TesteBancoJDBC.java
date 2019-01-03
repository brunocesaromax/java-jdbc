import dao.UserposDAO;
import model.Userposjava;
import org.junit.Test;

import java.util.List;

public class TesteBancoJDBC {

    @Test
    public void initBanco(){
        UserposDAO userposDAO = new UserposDAO();
        Userposjava userposjava = new Userposjava();

        userposjava.setId((long) 4);
        userposjava.setNome("MAga");
        userposjava.setEmail("maga@gmail.com");

        userposDAO.salvar(userposjava);
    }

    @Test
    public void listar(){

        UserposDAO userposDAO = new UserposDAO();

        try {
            List<Userposjava> list = userposDAO.listar();

            for (Userposjava u : list){
                System.out.println(u.toString()+"\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buscar(){

        UserposDAO userposDAO = new UserposDAO();

        try {
            Userposjava userposjava = userposDAO.buscarById(4L);
            System.out.println(userposjava.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
