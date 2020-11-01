const mongoose = require('mongoose')
mongoose.connect('mongodb://127.0.0.1:27017/ordersio-api',{
    useNewUrlParser:true,
    useCreateIndex:true
})
const orderSchema = new mongoose.Schema({
    itemname:{type:String,required:true,trim:true},
    price:{type:String,required:true,trim:true},
    quantity:{type:Number,required:true},
    owner:{
        type:mongoose.Schema.Types.ObjectId,
        required:true,
        ref:'User'
    }
},{timestamps:true})

const Order = mongoose.model('Order',orderSchema)
module.exports=Order