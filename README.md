# moneyspread
The moneyspread is moneySpread Api project for kakao 

## Information
### 1. Back End
> 1. Spring boot 2.4.0
> 2. Gradle
> 3. Java 11
> 4. MongoDB
> 5. lombok
> 6. Webflux*
...

### 3. DB
|  <center>Filed Name</center> |  <center>Data Type</center> | <center>PK</center>  |
|:--------|:--------:|--------:|:--------:|
|**roomId** | <center>String </center> | O| 
|**createUserId** | <center>String</center> | | O | 
|**roomToken** | <center>String</center> | O | 
|**id** | <center>varchar </center> | | 
|**boardType** | <center>varchar </center> | |
|**spreadMoney** | <center>datetime </center> |TIMESTAMP |
|**receivedId** | <center>datetime </center> |TIMESTAMP | 
|**totalMoney** | <center>datetime </center> |TIMESTAMP | 
|**people** | <center>datetime </center> |TIMESTAMP | 
|**roomToken** | <center>String</center> |  | 
|**createTime** | <center>String</center> |LocalDateTime |  | 

### 3. Instructions
> 1. The project is multi-module project. Both projects must be executed for the function.  
     placesearch(9090: Main), placesearch-api(9091 : RestApi)  
> 2. Accessible id is user5 ~ user9. Inaccessible id is user1~user4.    
     All ID passwords are "1".
