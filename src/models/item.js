const mongoose = require('mongoose')
mongoose.connect('mongodb://127.0.0.1:27017/ordersio-api',{
    useNewUrlParser:true,
    useCreateIndex:true
})
const Item = mongoose.model('Item',{
    itemname:{type:String,required:true,trim:true},
    price:{type:String,required:true,trim:true},
})
module.exports=Item