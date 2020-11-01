const express = require('express')
const router = new express.Router()
const Item = require('../models/item')
router.get('/items',async(req,res)=>{try{
    const items = await Item.find({})
    res.send(items)}
    catch(error){res.status(400).send(error)}
})
module.exports=router