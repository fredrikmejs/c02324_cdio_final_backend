use 185140;

create view user_food_view as
SELECT food_name, loc_id, cat_id, amount, expirering_date
from Food where Food.user_name = User.user_name;


