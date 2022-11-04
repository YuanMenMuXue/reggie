package com.wcq.reggie.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wcq.reggie.entity.OrderDetail;
import com.wcq.reggie.entity.Orders;
import lombok.Data;
import java.util.List;

/**
 * @author LJM
 * @create 2022/5/3
 *
 */
@Data
public class OrderDto extends Orders  {

    private List<OrderDetail> orderDetails;
}