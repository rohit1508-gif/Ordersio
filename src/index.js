const express  = require('express')
require('./db/mongoose')
const userrouter = require('./routers/user.js')
const orderrouter = require('./routers/order.js')
const itemrouter = require('./routers/item.js')
const app = express()
const port = process.env.PORT
app.use(express.json())
app.use(userrouter)
app.use(itemrouter)
app.use(orderrouter)
app.listen(port,()=>{
    console.log('Server setup in Port ' + port)
})