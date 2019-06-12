use s185140;

create view user_food_view as
SELECT food_name, Location.location, Category.category, expirering_date,
from Food where Food.user_name = User.user_name;


