package Domain;

import Technical_Services.IFoodDTO;

import java.util.List;

public interface INotifikation {
    List<IFoodDTO> getExpFood(String userName);
}
