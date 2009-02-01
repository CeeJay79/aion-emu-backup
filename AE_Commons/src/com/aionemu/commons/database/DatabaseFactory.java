/**
 * This file is part of aion-emu.
 *
 *  aion-emu is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-emu is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aionemu.commons.database;

// Common SQL

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

/**
 * <b>Database Factory</b><br>
 * <br>
 * This file is used for creating a pool of connections for the server.<br>
 * It utilizes database.properties and creates a pool of connections and
 * automatically recycles them when closed.<br>
 * <br>
 * DB.java utilizes the class.<br>
 * <br>
 * 
 * @depends database.properties
 * @requires commons-dbcp-1.2.2.jar
 * @requires commons-pool-1.4.jar
 * @requires mysql-connector-java-5.1.7-bin.jar
 * @author Disturbing
 */
public final class DatabaseFactory
{

	// Database settings
	/** DB Host */
	public static String			DATABASE_HOST;
	/** DB Port */
	public static int				DATABASE_PORT;
	/** DB URL */
	public static String			DATABASE_DRIVER;
	/** DB Login Alias */
	public static String			DATABASE_LOGIN_USER;
	/** DB Login Pass */
	public static String			DATABASE_LOGIN_PASS;
	/** DB Alias */
	public static String			DATABASE_NAME;
	/** DB Max Connections */
	public static int				DATABASE_MAX_CON;

	// Required Objects
	/** Logger */
	protected static final Logger	log			= Logger.getLogger(DatabaseFactory.class.getName());
	/** Data Source Generates all Connections */
	private DataSource				dataSource;
	/** Connection Pool holds all connections - Idle or Active */
	private GenericObjectPool		conPool;
	/** Factory Instance */
	private static DatabaseFactory	instance	= new DatabaseFactory();

	/**
	 * DatabaseFactory Constructor Creates Pool and Connection Factory using
	 * database.properties
	 */
	private DatabaseFactory()
	{
		try
		{
			Properties settings = new Properties();
			log.info("Loading database.properties");
			InputStream is = new FileInputStream(new File("./config/database.properties"));
			settings.load(is);

			DATABASE_HOST = settings.getProperty("DatabaseHost", "127.0.0.1");
			DATABASE_PORT = Integer.parseInt(settings.getProperty("DatabasePort", "3306"));
			DATABASE_DRIVER = settings.getProperty("DatabaseDriver", "com.mysql.jdbc.Driver");
			DATABASE_NAME = settings.getProperty("DatabaseName", "db_name");
			DATABASE_LOGIN_USER = settings.getProperty("DatabaseUser", "root");
			DATABASE_LOGIN_PASS = settings.getProperty("DatabasePass", "pass");
			DATABASE_MAX_CON = Integer.parseInt(settings.getProperty("DatabaseMaxConnections", "10"));

			settings.clear();
		}
		catch (Exception e)
		{
			log.fatal("Error while loading database.properties", e);
			throw new Error("database.properties not loaded!");
		}

		// Check if Driver exists
		try
		{
			java.lang.Class.forName(DATABASE_DRIVER).newInstance();
		}
		catch (Exception e)
		{
			log.fatal("Error obtaining DB driver", e);
			throw new Error("DB Driver doesnt exist!");
		}

		conPool = new GenericObjectPool(null);
		conPool.setMinIdle(1);
		conPool.setMaxActive((DATABASE_MAX_CON < 5) ? 10 : DATABASE_MAX_CON);

		try
		{
			dataSource = setupDataSource();
			getConnection().close();
		}
		catch (Exception e)
		{
			log.fatal("Error with connection string: " + getConStr(), e);
			throw new Error("DatabaseFactory not initialized!");
		}

		log.info("Successfully connected to database");

	}

	/**
	 * Obtain instance of DatabaseFactory. Creates instances if none exists.
	 * 
	 * @return DatabaseFactory
	 */
	public static DatabaseFactory getInstance()
	{
		return instance;
	}

	/**
	 * Get Connection String Returns Connection String used to connect to the
	 * database.
	 * 
	 * @return String Connection String;
	 */
	private String getConStr()
	{
		String driver = DATABASE_DRIVER;
		driver = driver.toLowerCase();
		String postUrl = "invalid:url";

		if (driver.contains("mysql"))
			postUrl = "jdbc:mysql:";
		else if (driver.contains("mssql"))
			postUrl = "jdbc:mssql:";

		return postUrl + "//" + DATABASE_HOST + ":" + DATABASE_PORT + "/" + DATABASE_NAME;
	}

	/**
	 * Sets up Connection Factory and Pool
	 * 
	 * @return DataSource
	 */
	private DataSource setupDataSource() throws Exception
	{
		// Create Connection Factory
		ConnectionFactory conFactory = new DriverManagerConnectionFactory(getConStr(), DATABASE_LOGIN_USER,
			DATABASE_LOGIN_PASS);

		// Makes Connection Factory Pool-able (Wrapper for two objects)
		new PoolableConnectionFactory(conFactory, conPool, null, null, false, true);

		// Create data source to utilize Factory and Pool
		return new PoolingDataSource(conPool);
	}

	/**
	 * Returns an active connection from pool. This function utilizes the
	 * dataSource which grabs an object from the ObjectPool within its limits.
	 * The GenericObjectPool.borrowObject()' function utilized in
	 * 'DataSource.getConnection()' does not allow any connections to be
	 * returned as null, thus a null check is not needed. Throws SQLException in
	 * case of a Failed Connection
	 * 
	 * @return Connection
	 */
	Connection getConnection() throws SQLException
	{
		return dataSource.getConnection();
	}

	/**
	 * Returns number of active connections in the pool.
	 * 
	 * @return int Active DB Connections
	 */
	public int getActiveConnections()
	{
		return conPool.getNumActive();
	}

	/**
	 * Returns number of Idle connections. Idle connections represent the number
	 * of instances in Database Connections that have once been connected and
	 * now are closed and ready for re-use. The 'getConnection' function will
	 * grab idle connections before creating new ones.
	 * 
	 * @return int Idle DB Connections
	 */
	public int getIdleConnections()
	{
		return conPool.getNumIdle();
	}

	/**
	 * Shuts down pool and closes connections
	 */
	public void shutdown()
	{
		try
		{
			conPool.close();
		}
		catch (Exception e)
		{
			log.warn("Failed to shutdown DatabaseFactory", e);
		}
	}
}