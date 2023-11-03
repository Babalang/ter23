package seleniumTests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseForTests{

    @Test
    void successfulLogin()  {
        login("Chef", "mdp");
        assertTrue(driver.getTitle().contains("Page des TER de M1"));
    }

    @Test
    void passwordError(){
        login("Chef", "erreur");
        WebElement  error= driver.findElement(By.className("alert-danger"));
        String errorText=read(error);
        assertTrue(errorText.contains("Bad credentials"));
    }

    @Test
    void scenario1Test(){
        login("Turing","turing");
        String test = Acces_List_Teacher();
        if (driver.getTitle().contains("Error")){
            Retourner();
            Acces_List();
            String test2 = GetSource();
            assertEquals(test,test2);
        }
    }

}

