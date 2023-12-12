const express = require('express')
const app = express()
require('dotenv').config()
const port = process.env.APP_PORT || 5000

const userRouter = require('./api_src/users/user.router');

app.use(express.json());

app.use('/', userRouter);

// !important! 
// you need to install the following libraries |express|[dotenv > if required]
// or run this command >> npm i express dotenv 

app.get('/' , (req , res)=>{

   res.send('hello from simple server :)');

});

app.listen(port , ()=> {
    console.log('> Server is up and running on port : ' + port);
})
