package com.example.bankapplication.entity.enums;

public enum TransactionType {
    DEPOSIT,        //Депозит
    WITHDRAWAL,     //Зняття
    TRANSFER,       //Переказ
    PAYMENT,        //Оплата рахунків
    ATM,            //Операція через банкомат
    POS,            //транзакція, під час якої клієнт використовує дебетову або кредитну картку для придбання товарів або послуг у продавця
    WIRE_TRANSFER   //Банківський переказ
}
