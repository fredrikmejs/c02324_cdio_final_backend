package Technical_Services;

import java.sql.Date;

public class foodDTO implements IFoodDTO{

    private Date expDate;
    private String name;
    private ELocation location;
    private ECategory category;


public foodDTO(String name, Date date, ECategory category, ELocation location ){
    this.expDate = date;
    this.name = name;
    this.category = category;
    this.location = location;
}


    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date date) {
    this.expDate = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    this.name = name;
    }

    public ELocation getLocation() {
        return location;
    }

    public void setLocation(ELocation location) {
    this.location = location;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCateGory(ECategory category) {
    this.category = category;
    }
}