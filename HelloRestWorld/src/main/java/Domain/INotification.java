package Domain;

import Technical_Services.IFoodDTO;

import java.util.List;

public interface INotification {
    List<IFoodDTO> getExpFood(String userName);
}
