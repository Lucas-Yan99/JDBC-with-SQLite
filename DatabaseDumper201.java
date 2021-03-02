import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Class which needs to be implemented.  ONLY this class should be modified
 */
public class DatabaseDumper201 extends DatabaseDumper {
    protected Connection con = null;
    private DatabaseMetaData metaData;

    /**
     * 
     * @param c connection which the dumper should use
     * @param type a string naming the type of database being connected to e.g. sqlite
     */
    public DatabaseDumper201(Connection c,String type) {
        super(c,type);
        this.con = c;
        try {
            this.metaData = con.getMetaData();
        }catch (SQLException e){

        }
    }
    /**
     * 
     * @param c connection to a database which will have a sql dump create for
     */
    public DatabaseDumper201(Connection c) {
        super(c,c.getClass().getCanonicalName());
        this.con = c;
        try {
            this.metaData = con.getMetaData();
        }catch (SQLException e){

        }
    }

    public List<String> getTableNames() {
        List<String> result = new ArrayList<>();
        try{
            DatabaseMetaData metaData = con.getMetaData();
            String[] types = {"TABLE"};
            //Retrieving the columns in the database
            ResultSet tables = metaData.getTables(null, null, "%", types);
            while (tables.next()) {
                result.add(tables.getString("TABLE_NAME"));
            }
        }catch (SQLException e){}
        //your code above here
        return result;
    }
    
    @Override
    public List<String> getViewNames() {
        List<String> result = new ArrayList<>();
        try{
            DatabaseMetaData metaData = con.getMetaData();
            String[] types = {"VIEW"};
            //Retrieving the columns in the database
            ResultSet tables = metaData.getTables(null, null, "%", types);
            while (tables.next()) {
                result.add(tables.getString("TABLE_NAME"));
            }
        }catch (SQLException e){}
        //your code above here
        return result;
    }

    @Override
    public String getDDLForTable(String tableName) {
        // TODO Auto-generated method stub
        if (tableName == null){
            return  "";
        }
        StringBuilder result = new StringBuilder("CREATE TABLE \"" + tableName + "\" (" + "\n");
        try{
            ResultSet rs = metaData.getColumns(null, null, tableName, null);
            while(rs.next()){
                result.append(" \"" + rs.getString("COLUMN_NAME") + "\" ");
                result.append(rs.getString("TYPE_NAME")).append(",\n");
            }


            ResultSet re2 = metaData.getPrimaryKeys(null, null, tableName);
            result.append(" PRIMARY KEY (");
            while(re2.next()){
                result.append(" \"" + re2.getString("COLUMN_NAME") + "\", ");
            }
            result.deleteCharAt(result.lastIndexOf(","));
            result.append("),\n");


            ResultSet re3 = metaData.getImportedKeys(null, null, tableName);
            while (re3.next()){
                result.append(" FOREIGN KEY (\"" + re3.getString("FKCOLUMN_NAME") + "\") REFERENCES \"");
                result.append(re3.getString("PKTABLE_NAME") + "\"(\"" + re3.getString("PKCOLUMN_NAME") + "\")\n");
            }
            result.deleteCharAt(result.lastIndexOf(","));
            result.append("),\n");
        }catch(SQLException exception){

        }
        return result.toString();
    }

    @Override
    public String getInsertsForTable(String tableName) {
        // TODO Auto-generated method stub
        String query = "SELECT * FROM " + tableName;
        int VARCHAR = 12;
        StringBuilder result = new StringBuilder();
        try {
            ResultSet r = metaData.getColumns(null, null, tableName, null);
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnNum = rsmd.getColumnCount();

            while (r.next()) {
                result.append("INSERT INTO \"" + tableName + "\" VALUES(\n");
                for (int i= 1; i <= columnNum; i++){
                    if(rsmd.getColumnType(i) == VARCHAR){
                        result.append("\"" + rs.getString(i).replaceAll("\"", "") + "\"");
                    }
                    else{
                        result.append(rs.getString(i));
                    }

                    if (i < columnNum){
                        result.append(", ");
                    }
                }
                result.append(");\n");
            }
        }catch (SQLException e){
        }
        return result.toString();
    }

    @Override
    public String getDDLForView(String viewName) {
        // TODO Auto-generated method stub
        StringBuilder result = new StringBuilder("CREATE TABLE \"view_" + viewName + "\" (" + "\n");
        try {
            ResultSet rs =con.getMetaData().getColumns(null, null, viewName, null);
            while (rs.next()){
                result.append(" \"" + rs.getString("COLUMN_NAME") + "\" " + rs.getString("TYPE_NAME") + ",");
            }
            result.deleteCharAt(result.lastIndexOf(","));
            result.append(");\n");
        }catch (SQLException e){
        }
        return result.toString();
    }

    @Override
    public String getDumpString() {
        // TODO Auto-generated method stub
        StringBuilder result = new StringBuilder();
        List<String> tables = getTableNames();
        result.append("/*\nDatabaseDumper201 JDBC using JAVA")
                .append("Version:1.0\n")
                .append("Author:Lucas Yan\n")
                .append("Student ID: 37709313\n")
                .append("*/\n");

        for(String table : tables)
        {
            result.append("DROP TABLE IF EXISTS ").append(table).append(";\n");
        }
        for (String table : tables)
        {
            result.append(getDDLForTable(table)).append("\n");
        }
        for (String table : tables)
        {
            result.append(getInsertsForTable(table)).append("\n");
        }
        result.append(getDatabaseIndexes());
        while (result.charAt(result.length() - 1) == '\n') {
            result.deleteCharAt(result.length() - 1);
        }
        // TODO Auto-generated method stub
        return result.toString();
    }

    @Override
    public void dumpToFileName(String fileName) {
        // TODO Auto-generated method stub
        try {
            FileWriter fw = new FileWriter(fileName);
            String a = getDumpString();
            fw.write(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dumpToSystemOut() {
        // TODO Auto-generated method stub
        System.out.println(getDumpString());
    }

    @Override
    public String getDatabaseIndexes() {
        // TODO Auto-generated method stub
        return null;
    }

}
