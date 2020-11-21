const express = require('express')
const auth = require('../middleware/auth')
const router = new express.Router()
const Cart = require('../models/cart')
router.post('/carts',auth,async(req,res)=>{
    try{
        const cart = await new Cart({...req.body, owner:req.user._id})
        await cart.save()
        res.status(201).send(cart)
    }
    catch(error){res.status(400).send(error)}
})
router.get('/carts',auth,async(req,res)=>{
    try{
       const cart = await Cart.find({})
        res.send(cart)
    }
    catch(error){res.status(500).send(error)}
})
router.delete('/carts/:id',async(req,res)=>{
    const _id = req.params.id
    try{
        const cart = await Cart.findById(_id)
        await cart.remove()
        res.send(cart)
    }catch(error){res.status(500).send(error)}
})
router.patch('/carts/:id',auth,async(req,res)=>{
    _id=req.params.id
    const updates = Object.keys(req.body)
    try{
        const cart=await Cart.findById(_id)
        updates.forEach((update)=>cart[update]=req.body[update])
        await cart.save()
        res.send(cart)
    }
    catch(error){res.status(400).send(error)}
})
module.exports = router