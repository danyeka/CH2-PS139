const {DataTypes} = require('sequelize');
const Connection = require('../config/database');

const dbConnection = Connection.connect;

const User = dbConnection.define('users', {
  id: {
    type: DataTypes.INTEGER,
    primaryKey: true,
    allowNull: false,
    autoIncrement: true
  },
  username: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  email: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  password: {
    type: DataTypes.STRING,
    allowNull: false,
  },
});

(async () => {
  await User.sync({alter: true});
})();

module.exports = User;