# Wallet-MicroService

## Description
Simple wallet Spring Boot microservice using JPA and h2 in memory database that provides REST APIs to manage credit/debit transactions on behalf of players. 

An account holds the current balance for a player. 
The balance can be modified by registering transactions on the account, either debit 
transactions (removing funds) or credit transactions (adding funds). 


This project uses a gradle wrapper, see https://docs.gradle.org/current/userguide/gradle_wrapper.html.

Execute 'bootRun' gradle task to run the application web server exposing the APIs on localhost:8080.
Execute 'test' gradle task to run the unit tests.

## APIs
- Player API '/api/player'
  - **createPlayer** *'/{name}'*: Creates player
  - **getPlayerIdByName** *'/{name}/id'*: Retrieve player id
- Wallet API '/api/wallet'
  - **performTransaction** *'/transaction/{type}/{playerId}/{amount}/{transactionId}'*: Performs a transaction of type DEBIT or CREDIT on a players account.
  - **getWalletBalance** *'/balance/{playerId}'*: Retrieves current wallet balance of a player.
  - **getTransactionHistory** *'/history/{playerId}'*: Retrieves a history list of transactions on a players account.
  - **generateTransactionId** *'/generateTransactionId'*: Generates a valid and unique transactionId.

## Database
Non persistent H2 database is configured. 

H2 offers an embedded GUI for browsing the contents of the database accessible on http://localhost:8080/h2-console. Login in using Driver: *org.h2.Driver*, URL: *jdbc:h2:mem:test* and credentials: *test* : *test*.
