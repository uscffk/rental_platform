import constants from "../store/constants";
import qs from 'qs'
import {getUser} from '../api/session'
// import { resolve } from "core-js/fn/promise";
export function getBasicForm() {
    return {
        id: null,
        name: null,
        rentPrice: 0,
        sellPrice: 0,
        manufacturer: getUser().id,
        category: null,
        picture: null,
        discription: null,
        sharingRent: 1,
        rentBeforeBuy: 1,
        rentForSale: 1,
        billMethod: 0,
        status: 0,
        timeNumber: 0,
        deposit: 0,
        detailLoc:"",
        stock: {
            number:0
        }
    };
}

export function queryGoodByCondition(params) {
    return new Promise((resolve) => {
        let goods = [];

        axios.post('/api/commodityService/commodities/query', JSON.stringify(params), {
            headers: {
                'content-type': 'application/json' //@requestBody,不能用qs
            }
        }).then(rs => {
            console.log(rs.data)
            goods = rs.data.data;
            resolve(goods);
        }).catch(() => { //模拟数据
            console.log(111111111111111)
            for (let i = 0; i < 12; i++) {
                goods.push(JSON.parse(JSON.stringify(getBasicForm())));
                goods[i].id = i;
                goods[i].name = '商品' + i
            }
            resolve(goods);
        });
    })
}

export function queryGoods(params) {
    console.log(params)
    if (getUser().id==null) {
        return new Promise((resolve) => {
            let goods = [];
            axios.post('/api/commodityService/commodities/query',JSON.stringify(params), {
                headers: {
                    'content-type': 'application/json' //@requestBody,不能用qs
                }
            }).then(rs => {
                console.log(rs.data)
                goods = rs.data.data;
                resolve(goods);
            }).catch(() => { //模拟数据
                console.log(111111111111111)
                for (let i = 0; i < 12; i++) {
                    goods.push(JSON.parse(JSON.stringify(getBasicForm())));
                    goods[i].id = i;
                    goods[i].name = '商品' + i
                }
                resolve(goods);
            });
        })
    }else{
        console.log(getUser().id)
        // 遗憾的是，带有异步请求，无法直接return，会出错，只能promise
        return new Promise((resolve) => {
        let goods = [];
        
        axios.post('/api/commodityService/commodities/query', JSON.stringify({ manufacturerId: getUser().id,
                                                                               offset:typeof(params) == 'undefined'?null:params.offset,
                                                                               limit:typeof(params) == 'undefined'?null:params.limit}), {
            headers: {
                'content-type': 'application/json' //@requestBody,不能用qs
            }
        }).then(rs => {
            console.log(rs.data)
            goods = rs.data.data;
            resolve(goods);
        }).catch(() => { //模拟数据
            console.log(111111111111111)
            for (let i = 0; i < 12; i++) {
                goods.push(JSON.parse(JSON.stringify(getBasicForm())));
                goods[i].id = i;
                goods[i].name = '商品'+i
            }
            resolve(goods);
        });
    })
    }
    
}

export function addGood(params) {
    //遗憾的是，带有异步请求，无法直接return，会出错，只能promise
    return new Promise((resolve) => {
        axios.post('/api/commodityService/commodities/add', params, {
            headers: {
                'content-type': 'application/json' //@requestBody,不能用qs
            }
        }).then(rs => {
            resolve(rs.data);
        }).catch(() => { //模拟数据
            console.log(1111111111111)
            resolve({code: constants.state.FAIL, message: '添加商品失败'});
        });
    })
}


export function updateGood(params) {
    console.log(1111)
    console.log(params)
    // 遗憾的是，带有异步请求，无法直接return，会出错，只能promise
    return new Promise((resolve) => {
        axios.post('/api/commodityService/commodities/update', JSON.stringify(params), {
            headers: {
                'content-type': 'application/json' //@requestBody,不能用qs
            }
        }).then(rs => {
            console.log('success')
            resolve(rs.data);
        }).catch(() => { //模拟数据
            resolve({code: constants.state.FAIL, message: '修改商品失败'});
        });
    })
}


export function queryGoodById(id) {
    console.log("哈哈哈哈哈哈")
    console.log(id)
    return new Promise((resolve) => {
        axios.get('/api/commodityService/commodities/queryById?id='+id
        ).then(rs => {
            resolve(rs.data);
        }).catch(() => { //模拟数据
            resolve({code: constants.state.FAIL, message: '获取商品信息失败'});
        });
    })
}


export function queryTotal() {
    return new Promise((resolve) => {
        axios.post('/api/commodityService/commodities/queryTotal'
        ).then(rs => {
            resolve(rs.data);
        }).catch(() => { //模拟数据
            resolve({code: constants.state.FAIL, message: '获取商品信息失败'});
        });
    })
}



export function addNFT(parm){
    console.log(parm)
    return new Promise((resolve)=>{
        axios.post('/api/commodityService/nftCommodity/add',qs.stringify({
            amount:parm.amount,
            longitude:parm.longitude,
            latitude:parm.latitude,
            detailLoc:parm.detailLoc,
            goodsId:parm.id
        })).then(rs => {
            resolve(rs.data);
        }).catch(() => { //模拟数据
            resolve({code: constants.state.FAIL, message: '上架失败'});
        });
    })
}

export function updateNFT(parm){
    console.log(parm)
    return new Promise((resolve)=>{
        axios.post('/api/commodityService/nftCommodity/update',JSON.stringify(parm),{
            headers: {
                'content-type': 'application/json' //@requestBody,不能用qs
            }}).then(rs=>{
                resolve(rs.data);
            }).catch(()=>{
                resolve({code: constants.state.FAIL, message: '更新NFT失败'});
            })
    })
}



//查询站点
export function queryStation(row){
    console.log(row);
    return new Promise((resolve)=>{
        axios.post('/api/commodityService/backLocation/queryById',qs.stringify({
            id:row.id
        })).then(rs =>{
            resolve(rs.data);
        }).catch(() => {
            resolve({code: constants.state.FAIL, message: '查询站点失败'});
        });
    })
}


export function addToStation(station){
    console.log(station);
    return new Promise((resolve)=>{
        axios.post('/api/commodityService/backLocation/add',JSON.stringify(station),{
            headers: {
                'content-type': 'application/json' //@requestBody,不能用qs
            }}).then(rs=>{
                resolve(rs.data);
            }).catch(()=>{
                resolve({code: constants.state.FAIL, message: '添加站点失败'});
            })
    })
}


export function queryAllBackLocation(){
    return new Promise((resolve)=>{
        axios.post('/api/commodityService/backLocation/queryByMid',qs.stringify({
            mid: getUser().id
        })).then(rs=>{
                console.log(rs.data)
                resolve(rs.data);
            }).catch(()=>{
                resolve({code: constants.state.FAIL, message: '查询站点失败'});
            })
    })
}
