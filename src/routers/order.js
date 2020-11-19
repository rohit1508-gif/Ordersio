const express = require('express')
const auth = require('../middleware/auth')
const router = new express.Router()
const Order = require('../models/order')
router.post('/orders',auth,async(req,res)=>{
    try{
        const order = await new Order({
            ...req.body,
            owner:req.user._id
        })
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
router.delete('/orders/:id',auth,async(req,res)=>{
    const _id = req.params.id
    try{
        const order = await Order.findById({_id,owner:req.user._id})
        await order.remove()
        res.send(order)
    }catch(error){res.status(500).send(error)}
})
router.patch('/orders/:id',auth,async(req,res)=>{
    const updates = Object.keys(req.body)
    const _id = req.params.id
    try{
        const order = await Order.findById({_id,owner:req.user._id})
        updates.forEach((update)=>order[update]=req.body[update])
        await order.save()
        res.send(order)
    }
    catch(error){res.status(400).send(error)}
})
module.exports = router