import constants from "../store/constants";
import {getUser} from "../api/session"

import qs from 'qs'
export function recharge(form) {
    console.log(form)
    return new Promise(function (resolve) {
        axios.post('/api/platformService/platforms/recharge', qs.stringify(form)).then(rs => {
            resolve(rs.data);
        }).catch(() => {
            resolve({code: constants.state.FAIL, message: '服务器连接失败'})
        })
    })
}


//这是给平台合约本身充值  就是平台外部账户给平台合约账户打钱  
export function rechargeToSelf(amount){
    return new Promise(function(resolve){
        axios.post('/api/platformService/platforms/rechargeToSelf', qs.stringify({amount:amount})).then(rs => {
            console.log(rs.data)
            resolve(rs.data);
        }).catch(() => {
            resolve({code: constants.state.FAIL, message: '服务器连接失败'})
        })
    })
}

// 查询充值提现
export function queryRecharge(parm) {
    console.log(getUser().id)
    console.log(typeof (getUser().id) != "undefined" ? JSON.stringify({ manufacturerId: getUser().id }) : {})
        return new Promise(function (resolve) {
            axios.post('/api/platformService/platforms/queryRecord', typeof (getUser().id) != "undefined" ? JSON.stringify({ 
                                                                                                                             identity:1,
                                                                                                                             type:parm.type,
                                                                                                                             fromId:getUser().id}):{identity:parm.identity,type:parm.type,fromId:parm.fromId}, {
                headers: {
                    'content-Type': "application/json"
                }
            }).then(rs => {
                resolve(rs.data.data);
            }).catch(() => {
                resolve({ code: constants.state.FAIL, message: '服务器连接失败' })
            })
        })
}



export function queryPlatInfo(){
    return new Promise(function (resolve) {
        axios.post('/api/platformService/platInfo/queryPlatInfo',{}, {
            headers: {
                contentType: "application/json"
            }
        }).then(rs => {
            console.log(rs.data.data)
            resolve(rs.data.data);
        }).catch(() => {
            resolve({ code: constants.state.FAIL, message: '服务器连接失败' })
        })
    })
}


//把平台合约中的钱转移给平台的外部账户 有人会说那这不是动了合约的钱吗 押金的设计不是被推翻了吗? 其实是没有推翻的。想到这里的再去思考一下吧，仔细去看看合约 记得一句话 合约在公链上是公开的，平台不可能再押金合约定义一个把钱非法转出去的函数 别人也不买账的（因为是公开的）
export function transferToExteneralAccount(){
    return new Promise(function(resolve){
        axios.post('/api/platformService/platforms/transferToExteneralAccount',{}).then(rs => {
            console.log(rs.data)
            resolve(rs.data);
        }).catch(() => {
            resolve({ code: constants.state.FAIL, message: '服务器连接失败' })
        })
    })
}




export function deleteRecharge(row){
    return new Promise(function(resolve){
        axios.post('/api/platformService/platforms/deleteRecharge',qs.stringify({id:row.id})
        ).then(rs => {
            resolve(rs.data);
        }).catch(() => {
            resolve({ code: constants.state.FAIL, message: '删除失败' })
        })
    })
}


export function queryBalance(){
    return new Promise(function(resolve){
        axios.post('/api/platformService/platforms/queryBalance',{}
        ).then(rs => {
            console.log(rs.data)
            resolve(rs.data);
        }).catch(() => {
            resolve({ code: constants.state.FAIL, message: 'queryBalance失败' })
        })
    })
}


export function getRMB(form){
    console.log(form)
    return new Promise(function(resolve){
        axios.post('/api/platformService/platforms/getRMB',qs.stringify(form)
        ).then(rs => {
            console.log(rs.data)
            resolve(rs.data);
        }).catch(() => {
            resolve({ code: constants.state.FAIL, message: 'getRMB失败' })
        })
    })
}