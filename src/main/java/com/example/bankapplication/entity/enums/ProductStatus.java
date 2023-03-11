package com.example.bankapplication.entity.enums;

public enum ProductStatus {
    ACTIVE,                      //активний
    INACTIVE,                   //неактивний
    TEMPORARILY_UNAVAILABLE,    //тимчасово недоступний
    PENDING_APPROVAL,           //очікує на схвалення
    ON_HOLD,                    //на утриманні: цей статус вказує на те, що банк тимчасово призупинив пропозицію продукту
    RESTRICTED,                 //обмежений: цей статус вказує на те, що продукт доступний лише для обмеженої групи клієнтів
    PILOT                       //пілотний: цей статус вказує на те, що банк тестує продукт на обмеженому ринку
}
