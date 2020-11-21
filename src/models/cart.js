const mongoose = require('mongoose')
mongoose.connect('mongodb://127.0.0.1:27017/ordersio-api',{
    useNewUrlParser:true,
    useCreateIndex:true
})
const cartSchema = new mongoose.Schema({
    itemname:{type:String,required:true,trim:true},
    price:{type:String,required:true,trim:true},
    quantity:{type:Number,required:true},
    owner:{
    type:mongoose.Schema.Types.ObjectId,
    required:true,
    ref:'User'
    },
    seller:{ type:mongoose.Schema.Types.ObjectId,required:true}
},{timestamps:true})

const Cart = mongoose.model('Cart',cartSchema)
module.exports=Cart