package Domain;

import Technical_Services.IFoodDTO;

import java.sql.Date;
import java.util.List;

public interface INotification {
    List<IFoodDTO> getExpFood(String userName);
    Date getDate();
}
