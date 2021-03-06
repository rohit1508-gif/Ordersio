const mongoose = require('mongoose')
mongoose.connect('mongodb://127.0.0.1:27017/ordersio-api',{
    useNewUrlParser:true,
    useCreateIndex:true
})
const orderSchema = new mongoose.Schema({
    itemname:{type:String,required:true,trim:true},
    quantity:{type:Number,required:true},
    purchaserName:{type:String,required:true,trim:true},
    purchaserNumber:{type:String,required:true,trim:true},
    seller:{ type:mongoose.Schema.Types.ObjectId,required:true}
},{timestamps:true})

const Order = mongoose.model('Order',orderSchema)
module.exports=Order