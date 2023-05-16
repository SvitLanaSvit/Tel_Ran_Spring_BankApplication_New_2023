# Bank Project [Backend]

There is a prototype of the BackEnd Bank's Core Services data.
Data consist of clients, accounts, products, accounts, transactions and managers
___

### Class Diagram BankApplication
![PhotoDependencyClasses](https://github.com/SvitLanaSvit/Tel_Ran_BankApplication_New_2023/blob/main/Diagramms/jpg/Diagramm_classes.jpg)

### Sequence Diagram Account
![PhotoBase](https://github.com/SvitLanaSvit/Tel_Ran_BankApplication_New_2023/blob/main/Diagramms/jpg/Sequence%20Diagram%20Account.jpg)

### Database Diagram
![PhotoBase](https://raw.githubusercontent.com/SvitLanaSvit/Tel_Ran_BankApplication_New_2023/main/photoBase.jpg)

___
## Database Structure

### Table manager (Bank's managers table)

| Column name | Type         | Description                                   |
|-------------|--------------|-----------------------------------------------|
| id          | binary(16)   | id key of row - unique, not null, primary key | 
| first_name  | varchar(50)  | manager's name                                | 
| last_name   | varchar(50)  | manager's surname                             | 
| status      | varchar(50)  | manager's status from enum ManagerStatus      | 
| created_at  | timestamp    | timestamp of row creation                     |
| updated_at  | timestamp    | timestamp of last update                      | 


### Table client ( Bank's clients table )

| Column name | Type        | Description                                   |
|-------------|-------------|-----------------------------------------------|
| id          | binary(16)  | id key of entity - unique, not null, PK       | 
| status      | VARCHAR(20) | client's status from enum ClientStatus        |
| tax_code    | varchar(16) | client's TAX code unique                      |
| first_name  | varchar(50) | client's name                                 |
| last_name   | varchar(50) | client's surname                              |
| email       | varchar(60) | client's e-mail                               |                               
| address     | varchar(80) | client's address                              |
| phone       | varchar(20) | client's phone                                |                                
| created_at  | timestamp   | timestamp of entity creation                  |
| updated_at  | timestamp   | timestamp of last update                      |
| manager_id  | binary(16)  | manager`s id FK references managers(id)       |


### Table account (Bank's accounts table)

| Column name     | Type          | Description                             |
|-----------------|---------------|-----------------------------------------|
| id              | binary(16)    | id key of entity - unique, not null, PK |        
| name            | varchar(100)  | name of account                         |                              
| type            | varchar(50)   | account from enum AccountType           |                                   
| status          | varchar(50)   | status from enum AccountStatus          |                          
| balance         | decimal(15,2) | balance of the account                  | 
| currency_code   | varchar(50)   | currency code from enum CurrencyCode    |                          
| created_at      | timestamp     | timestamp of entity creation            |
| updated_at      | timestamp     | timestamp of last update                |
| client_id       | binary(16)    | client`s id FK references clients(id)   | 


### Table transaction (Bank's transactions table) 

| Column name        | Type          | Description                            |
|--------------------|---------------|----------------------------------------|
| id                 | binary(16)    | id key of entity - unique, not null, PK| 
| type               | byte          | transaction type  -124 bis 125         | 
| amount             | decimal(12,4) | transaction amount                     | 
| description        | varchar(255)  | description of transaction             | 
| created_at         | timestamp     | timestamp of entity creation           | 
| debit_account_id   | binary(16)    | account`s id FK references accounts(id)| 
| credit_account_id  | binary(16)    | account`s id FK references accounts(id)| 


### Table product (Bank's products table)

| Column name   | Type          | Description                               |
|---------------|---------------|-------------------------------------------|
| id            | binary(16)    | id key of entity - unique, not null, PK   |
| name          | varchar(70)   | product's name                            |
| status        | varchar(50)   | product's status from enum ProductStatus  |
| currency_code | varchar(50)   | currency code from enum CurrencyCode      |
| interest_rate | decimal(6,4)  | interest rate of product                  |
| product_limit | integer       | limit of product                          |
| created_at    | timestamp     | timestamp of entity creation              |
| updated_at    | timestamp     | timestamp of last update                  |
| manager_id    | binary(16)    | manager`s id FK references managers(id)   |


### Table agreement (Bank's agreements table)

| Column name   | Type          | Description                                 |
|---------------|---------------|---------------------------------------------|
| id            | binary(16)    | id key of entity - unique, not null, PK     |
| interest_rate | decimal(6,4)	| current interest rate of agreement          | 
| status        | VARCHAR(50)   | agreement's status from enum AgreementStatus| 
| sum           | decimal(15,2) | summe of agreement                          | 
| created_at    | timestamp     | timestamp of entity creation                | 
| updated_at    | timestamp     | timestamp of last update                    | 
| product_id    | binary(16)    | product's id FK references products(id)     |
| account_id    | binary(16)    | client's FK references accounts(id)         | 
