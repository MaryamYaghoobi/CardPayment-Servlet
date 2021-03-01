package ir.dotin.share;

import ir.dotin.repository.ManagerDao;

import java.util.List;

public class Validation {

    public boolean checkUsername(String usernameEntered) {
        ManagerDao managerDao = new ManagerDao();
        List<String> allUsername = managerDao.searchAllUsername();
        for (String username : allUsername) {
            if (usernameEntered.equals(username)) ;
              /*  System.out.println("Username Exists");
            } else
                System.out.println("Username Not Exists");*/
        }
        return true;
    }
}
