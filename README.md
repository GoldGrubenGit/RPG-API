## How to use the RPG-API

### SQL-Handler

1. Create a connection to the SQL-Server

```java
    private SQLHandler sqlHandler;

    public void main(String[] args) {
        sqlHandler = new SQLHandler((Plugin) this, "host", "password", "user", "database");
        sqlHandler.connect();
    }
```
