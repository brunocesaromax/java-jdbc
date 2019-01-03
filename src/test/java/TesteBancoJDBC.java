import dao.UserposDAO;
import model.Userposjava;
import org.junit.Test;

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

}
