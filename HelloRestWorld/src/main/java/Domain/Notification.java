package Domain;

import Backend.Backend;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//TODO: FIX THIS AND IMPLEMENT IN ERRORHANDLING INSTEAD


public class Notification implements INotification {

    //made in backend
    @Override
    public List<IFoodDTO> getExpFood(int notifyDays, String userName) throws SQLException {


        return null;
    }
    @Override
    public Date getDate() {


        return null;
    }
}
