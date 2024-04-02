import constants from "../store/constants";
import qs from 'qs'
import { getUser } from "./session";
export function getBusinessBasicForm() {
    return {
        id: null, name: null, phone: null, username: null, pwd: null, contractAddress: null
    }
}

export function queryBusinesses(parm) {
    return new Promise((resolve) => {
        axios.post('/api/businessService/manufacturer/query', JSON.stringify(parm), {
            headers: {
                'content-Type': 'application/json'
            }
        }).then(rs => {
            resolve(rs.data.data);
        }).catch(() => {
            let goods = [{
                id: 1,
                name: '盘多多',
                username: 'haha',
                pwd: 'dsa',
                phone: '111',
                contractAddress: '0x2132131'
            }];
            for (let i = 0; i < 10; i++) {
                goods[0].id = i;
                goods.push(JSON.parse(JSON.stringify(goods[0])));
            }
            resolve(goods);
        })
    });
};

export function queryProfits() {
    console.log(33333333333333)
    return new Promise((resolve) => {
        axios.post('/api/businessService/profit/queryProfit', {manufacturerId:getUser().id}, {
            headers: {
                contentType: 'application/json'
            }
        }).then(rs => {
            console.log(111111111111111)
            resolve(rs.data);
        }).catch(() => {
            console.log(111111111111111)
            let data = [{ commodityId: 1, amount: 2 }];
            resolve({ code: constants.state.SUCCESS, message: '服务器连接失败', data: data })
        })
    })
};

export function queryBalance(id) {
    console.log(id)
    return new Promise((resolve) => {
        axios.post('/api/businessService/manufacturer/queryBalance', qs.stringify({ id:getUser().id })).then(rs => {
            console.log(rs.data)
            resolve(rs.data.data);
        }).catch(() => {
            resolve(-1);
        })
    })
}

export function addBusiness(business) {
    console.log(business);
    return new Promise((resolve) => {
        axios.post('/api/businessService/manufacturer/add', business, {
            headers: {
                contentType: 'application/json'
            }
        }).then(rs => {
            resolve(rs.data);
            
        }).catch(() => {
            resolve({code: constants.state.FAIL, message: '服务器连接失败'})
        })
    })
}