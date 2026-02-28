package com.Exp_traking.App_service.Pojo.Account;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreditCardPojo{
    private BigDecimal outstanding;
    private BigDecimal creditLimit;
    private LocalDate dueDate;
    private LocalDate billDate;
}
