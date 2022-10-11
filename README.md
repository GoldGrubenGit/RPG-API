## How to use the RPG-API

### SQL-Handler

1. 

```java
  private SQLHandler sqlHandler;

    public void main(String[] args) {
        sqlHandler = new SQLHandler((Plugin) this, "host", "password", "user", "database");
        sqlHandler.connect();
    }
```
