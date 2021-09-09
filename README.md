### Start startup.sh to run the app

#### Add employee request example

`POST http://localhost:8080/passengers`

```json 
{
    "firstName": "abc",
    "lastName": "def",
    "age": 31,
    "contractInformation": "qwerty"
}
```

#### Change employee state request example

`PATCH http://localhost:8080/passengers/{id}`

```json 
{
    "state": "IN_CHECK"
}
