package com.example.bankapplication.entity.enums;

public enum ClientStatus {
    ACTIVE,         //активний
    INACTIVE,       //неактивний
    BLOCKED,        //блокований
    DECEASED,       //померлий
    PENDING,        //очікує: цей статус вказує на те, що рахунок клієнта в банку знаходиться в процесі відкриття
    VIP,
    BLACKLISTED     //у чорному списку
}
