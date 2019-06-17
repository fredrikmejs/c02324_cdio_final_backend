package Domain;

import Technical_Services.IFoodDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface INotification {
    List<IFoodDTO> getExpFood(int notifyDays, String userName) throws SQLException;
    Date getDate();
}
