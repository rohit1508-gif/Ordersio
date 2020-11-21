const express = require('express')
const router = new express.Router()
const Order = require('../models/order')
router.post('/orders',async(req,res)=>{
    try{
        const order = await new Order(req.body)
        await order.save()
        res.status(201).send(order)
    }
    catch(error){res.status(400).send(error)}
})
router.get('/orders',async(req,res)=>{
    try{
       const order = await Order.find({})
        res.send(order)
    }
    catch(error){res.status(500).send(error)}
})
router.delete('/orders/:id',async(req,res)=>{
    const _id = req.params.id
    try{
        const order = await Order.findById(_id)
        await order.remove()
        res.send(order)
    }catch(error){res.status(500).send(error)}
})
module.exports = router