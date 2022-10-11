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

2. Create a table

```java
    private SQLHandler sqlHandler;

    public void main(String[] args) {
        //connection to the sql-server
        sqlHandler.createTable("tablename", Arrays.asList(new String[] {"tableNames","tableNames","tableNames","tableNames","tableNames"}), 
                Arrays.asList(new String[] {"dataType","dataType","dataType"}));
    }
```
