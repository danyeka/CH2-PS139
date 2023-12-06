const express = require('express')
const app = express()
require('dotenv').config()
const port = process.env.APP_PORT || 8080

const userRouter = require('./src/routes/user.routes');

app.use(express.json());

app.use('/api/users', userRouter);

app.get('/', function(req,res) {
    return res.send('Hello World!');
});

app.listen(port , ()=> {
    console.log('> Server is up and running on port : ' + port);
})