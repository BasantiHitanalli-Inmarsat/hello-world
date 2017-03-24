package com.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.drools.compiler.lang.dsl.DSLMapParser.statement_return;
import org.kie.api.KieServices;
import org.kie.api.builder.Message.Level;
import org.kie.api.builder.Results;
import org.kie.api.definition.type.Position;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {
	private static Connection con = null;
    public static final void main(String[] args) {
        try {
           //Making the changes for GIT learning
        	//This is new commit
        	// load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");
        	Results results = kContainer.verify();
        	boolean hasErrors = results.hasMessages(Level.ERROR);
        	if(hasErrors){
        		throw new IllegalArgumentException("Rule Errors.");
        	}
        	System.out.println("results has errors: " + hasErrors);
        	getConnection();
            // go !
            Message message = new Message();
            message.setMessage("Hello World");
            message.setStatus(Message.HELLO);
            kSession.insert(message);
            
            Message m = new Message();
            m.setMessage("Hello");
            kSession.insert(m);
            Message m1 = new Message();
            m1.setMessage("Hello ");
            kSession.insert(m1);
            
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    public static void getConnection() throws SQLException {
    	
    	if(con==null) {
    		Statement stmt = null;
        	ResultSet rs = null;
    		try {
    	String driver = "com.mysql.jdbc.Driver";
    	String myUrl = "jdbc:mysql://localhost:3306/myDB";
    	String myUser = "root";
    	String myPass = "Password-1";

    	Class.forName(driver);
    	
    	con = DriverManager.getConnection(myUrl,myUser, myPass);
    	System.out.println("New database connection....");
    	 stmt = con.createStatement();
    	 rs = stmt.executeQuery("select * from bu_dp_mapping");
    	Map<String, String> map = new HashMap<>();
    	if(rs != null){
    		while (rs.next()) {
    		System.out.println("got resultset");
    	map.put(rs.getString("dpID"), rs.getString("buName"));}
    		map.put("1020", "asdasd");
    		map.put("1020", "fryrfhf");
    	}
    	System.out.println("Map: " + map.containsValue("Maritime"));
    	System.out.println("Map: " + map.get("1020"));
    	    	}
    	
    	catch(Exception ex) {
    	ex.printStackTrace();
    	// handle any errors
        System.out.println("SQLException: " + ex.getMessage());
//        System.out.println("SQLState: " + ex.getSQLState());
//        System.out.println("VendorError: " + ex.getErrorCode());
    	}finally{
    		con.close();
    		stmt.close();
    		rs.close();
    	}
    	}
    	else {
    	System.out.println("Old connection from cache...");
    	}
//    	For the mysql jdbc driver, you must arrange <<mysql-5.0.4-java-driver.jar>> as per your mysql version.
    	//Next, while running java application, the driver jar file must be in your classpath.
//    	return con;
    	} 
    
    public static class Message {

        public static final int HELLO = 0;
        public static final int GOODBYE = 1;
        @Position
        private String message;

        private int status;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

    }

}
