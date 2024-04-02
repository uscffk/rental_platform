import constants from "../store/constants";
import { getUser } from "./session";
import Qs from 'qs'//引入qs

export function bids(num,auctionAddress){
    //获取商家地址
    return new Promise((resolve) => {
        console.log({
            num:num,
            manufacturerAddress:getUser().contractAddress,
            auctionAddress:auctionAddress
        })
        axios.post('/api/commodityService/auction/bid',
        Qs.stringify({
            price:num,
            manufacturerAddress:getUser().contractAddress,
            auctionAddress:auctionAddress
        })
        ).then(rs =>{
            resolve(rs.data);
        }).catch(() => {
            resolve({code: constants.state.FAIL, message: '服务器连接失败'})
        })
    })
}

export function endAuctions(auctionAddress) {
    console.log(auctionAddress)
    return new Promise((resolve) => {
        axios.post('/api/commodityService/auction/end', Qs.stringify(
            {
                auctionAddress:auctionAddress
            }        
        )     
       ).then(rs => {
            resolve(rs.data);         
        }).catch(() => {
            resolve({code: constants.state.FAIL, message: '服务器连接失败'})
        })
    })
}

export function addAuction(auctionInfo) {
    console.log(auctionInfo);
    return new Promise((resolve) => {
        axios.post('/api/commodityService/auction/addByPlatform', 
            Qs.stringify({
                name:auctionInfo.name,
                category:auctionInfo.category,
                detailLoc:auctionInfo.detailLoc,
                time:auctionInfo.time
            })
       ).then(rs => {
            resolve(rs.data);
            
        }).catch(() => {
            resolve({code: constants.state.FAIL, message: '服务器连接失败'})
        })
    })
}

//分页查  前端伪分页方式太慢了
export function queryAllAuctionInfo(offset,limit){
    console.log('请求拍卖数据');
    return new Promise((resolve)=>{
        axios.post('/api/commodityService/auction/getAllAuctionInfo',Qs.stringify({
            offset:offset,
            limit:limit
        })).then(
            rs=>{
                console.log(rs.data);
                resolve(rs.data.data);
            }
        ).catch(()=>{
            resolve({code: constants.state.FAIL, message: '服务器连接失败'})
        })
    })
}

export function queryAuctionInfoByMFID(){
    console.log('请求拍卖数据');
    return new Promise((resolve)=>{
        axios.post('/api/commodityService/auction/queryAuctionInfoByMFId',Qs.stringify({
            manufacturerId:getUser().id
        })).then(
            rs=>{
                console.log(rs.data);
                resolve(rs.data.data);
            }
        ).catch(()=>{
            resolve({code: constants.state.FAIL, message: '服务器连接失败'})
        })
   
        
    })
}