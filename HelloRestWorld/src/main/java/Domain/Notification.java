package Domain;

import Technical_Services.IFoodDTO;

import java.sql.Date;
import java.util.List;
//TODO: FIX THIS AND IMPLEMENT IN ERRORHANDLING INSTEAD
public class Notification implements INotification {
    @Override
    public List<IFoodDTO> getExpFood(String userName) {
        return null;
    }

    @Override
    public Date getDate() {
        return null;
    }
}
