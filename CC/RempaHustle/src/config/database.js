const Sequelize = require('sequelize');
require('dotenv').config();

/**
 * Change variables below with these values:
 * DB_HOST      = database instance IP address
 * DB_NAME      = database name
 * DB_USER      = mysql user
 * DB_PASSWORD  = mysql user's password
 *
 * It is recommended to use environment variables
 */
const DB_HOST = process.env.DB_HOST;
const DB_NAME = process.env.DB_NAME;
const DB_USER = process.env.DB_USER;
const DB_PASS = process.env.DB_PASS;
const INSTANCE_CONNECTION_NAME = process.env.INSTANCE_CONNECTION_NAME;

const sequelize = new Sequelize(DB_NAME, DB_USER, DB_PASS, {
  socketPath: INSTANCE_CONNECTION_NAME,
  // host: DB_HOST,
  // user: DB_USER,
  // password: DB_PASS,
  database: DB_NAME,
  dialect: 'mysql',
  dialectOptions: {
    connectTimeout: 60000, // timeout in milliseconds
  }
});

// Test DB connection
const testConnection = async () => {
  try {
    await sequelize.authenticate();
    console.log('Connection to database established');
  } catch (error) {
    console.log('Failed to connect to database!');
  }
};

/**
 * Uncomment the code below to commence connection test
 */
// testConnection();

module.exports = {testConnection, connect: sequelize};