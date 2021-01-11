const express = require('express')
const auth = require('../middleware/auth')
const router = new express.Router()
const Item = require('../models/item')
router.post('/items',auth,async(req,res)=>{
    try{
        const item = await new Item({
            ...req.body,
            owner:req.user._id
        })
        await item.save()
        res.status(201).send(item)
    }
    catch(error){res.status(400).send(error)}
})
router.get('/items',async(req,res)=>{try{
    const items = await Item.find({})
    res.send(items)}
    catch(error){res.status(400).send(error)}
})
router.patch('/items/:id',auth,async (req,res)=>{
    const _id = req.params.id
    const updates = Object.keys(req.body)
    try{
        const item = await Item.findById(_id)
        if(!item){
            return res.status(404).send()
        }
        updates.forEach((update)=>item[update]=req.body[update])
        await item.save()
        res.send(item)
    }catch(e){res.status(400).send(e)}
})
router.delete('/items/:id',auth,async(req,res)=>{
    const _id=req.params.id
    try{
        const item = await Item.findById({_id,owner:req.user._id})
        if(!item){
            return res.status(404).send()
        }
        await item.remove()
        res.send(item)
    }catch(e){
        res.status(500).send()
    }
})

module.exports=router