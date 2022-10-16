## How to use the RPG-API

### First of all you need our repository and dependency:

```xml
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>

    <dependency>
        <groupId>com.github.GoldGrubenGit</groupId>
        <artifactId>RPG-API</artifactId>
        <version>RELEASE-Version</version>
    </dependency>
```

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
        sqlHandler.createTable("databaseName", Arrays.asList(new String[] {"tableNames","tableNames","tableNames","tableNames","tableNames"}), 
                Arrays.asList(new String[] {"dataType","dataType","dataType"}));
    }
```

3. Set a value into a table

```java
    private SQLHandler sqlHandler;

    public void main(String[] args) {
        //connection to the sql-server
        sqlHandler.set((Plugin) MainClass.getInstance(), "databaseName", "tableName", "line", "value", "column");
    }
```

I know it's a confusing, but its the efficient way
In the future this SQLAPI will be updated that you can use it easier
So an example:

```java
    private SQLHandler sqlHandler;

    Player player;

    public void main(String[] args) {
        sqlHandler = new SQLHandler((Plugin) this, "host", "password", "user", "database");
        sqlHandler.connect();
        sqlHandler.createTable("stats", Arrays.asList("uuid","name","kills","deaths"),
                Arrays.asList("varchar(64)","varchar(64)","int", "int"));
        sqlHandler.set((Plugin) this, "stats", "uuid", player.getUniqueId().toString(), 
                Integer.valueOf(Integer.parseInt(sqlHandler.getFromTable("stats", "uuid", 
                        player.getUniqueId().toString(), "deaths")) + 1), "deaths");
    }
```
