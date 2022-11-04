package com.wcq.reggie.dto;


import com.wcq.reggie.entity.Setmeal;
import com.wcq.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
