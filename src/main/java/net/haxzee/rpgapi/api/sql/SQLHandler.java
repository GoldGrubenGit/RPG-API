package net.haxzee.rpgapi.api.sql;

import net.haxzee.rpgapi.APIService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SQLHandler {
    final ExecutorService executorService = Executors.newCachedThreadPool();

    private String host;

    private String database;

    private String user;

    private String password;

    private Plugin plugin;

    private Connection connection;

    public SQLHandler(Plugin plugin, String host, String password, String user, String database) {
        this.plugin = plugin;
        this.host = host;
        this.password = password;
        this.user = user;
        this.database = database;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":3306/" + this.database + "?useJDBCCompliantTimezoneShift=true&&serverTimezone=UTC&&useUnicode=true&autoReconnect=true", this.user, this.password);
            APIService.sendLogMessage("Successfully connected to Database!");
        } catch (Exception ex) {
            APIService.sendLogMessage(ex.getMessage());
        }
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this.plugin, () -> getResult("SELECT 1"), 0L, 1200L);
    }

    public void disconnect() {
        try {
            if (this.connection != null)
                this.connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    /**
     * update
     * @param sqlQuery
     */
    public void update(String sqlQuery) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(sqlQuery);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            connect();
            e.printStackTrace();
        } finally {
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public ResultSet getResult(String sqlQuery) {
        PreparedStatement ps = null;
        try {
            ps = this.connection.prepareStatement(sqlQuery);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * create a table
     * @param tablename
     * @param objects
     * @param types
     */

    public void createTable(String tablename, List<String> objects, List<String> types) {
        String upload = "CREATE TABLE IF NOT EXISTS " + tablename + "(" + (String)objects.get(0) + " " + (String)types.get(0);
        for (int i = 1; i < objects.size(); ) {
            upload = upload + "," + (String)objects.get(i) + " " + (String)types.get(i);
            i++;
        }
        upload = upload + ");";
        update(upload);
    }

    /**
     * checks if a line exists
     * @param tablename
     * @param type
     * @param set
     * @return
     */

    public boolean existsLine(String tablename, String type, String set) {
        ResultSet resultSet = null;
        try {
            resultSet = getResult("SELECT * FROM " + tablename + " WHERE " + type + "= '" + set + "'");
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * @param tablename
     * @param types
     * @param list
     */

    public void createLine(String tablename, List<String> types, List<String> list) {
        String upload = "INSERT INTO " + tablename + "(" + (String)types.get(0);
        int i;
        for (i = 1; i < types.size(); ) {
            upload = upload + ", " + (String)types.get(i);
            i++;
        }
        upload = upload + ") VALUES ('" + (String)list.get(0) + "'";
        for (i = 1; i < list.size(); ) {
            upload = upload + ", '" + (String)list.get(i) + "'";
            i++;
        }
        upload = upload + ");";
        update(upload);
    }

    public Connection getConnection() {
        return this.connection;
    }

    /**
     * @param tableName
     * @param column
     * @param value
     * @param neededColumn
     * @return
     */

    public String getFromTable(String tableName, String column, String value, String neededColumn) {
        String query = "SELECT * FROM " + tableName + " WHERE " + column + "='" + value + "'";
        ResultSet resultSet = getResult(query);
        try {
            if (resultSet.next())
                return resultSet.getString(neededColumn);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * @param plugin
     * @param database
     * @param search
     * @param getLine
     * @param i
     * @param line
     */

    public void set(Plugin plugin, String database, String search, String getLine, Object i, String line) {
        this.executorService.execute(() -> update("UPDATE " + database + " SET " + line + "= '" + i + "' WHERE " + search + "= '" + getLine + "';"));
    }

    /**
     * @param table
     * @return
     */

    public Integer getSelectionAmount(String table) {
        int count = 0;
        ResultSet resultSet = getResult("SELECT * FROM " + table);
        try {
            while (resultSet.next())
                count++;
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Integer.valueOf(count);
    }
}
