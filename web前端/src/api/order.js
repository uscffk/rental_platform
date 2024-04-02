import { getUser } from '../api/session'


export function getOrders(parm) {
    return new Promise((resolve) => {

        let orders = [];
        axios.post('/api/orderService/orders/queryOrder', JSON.stringify({ manufacturerId: getUser().id,
                                                                           orderType:parm.orderType,
                                                                           status:parm.status,
                                                                           comment:parm.comment,
                                                                           billMethod:parm.billMethod}), {
            headers: {
                'content-type': 'application/json' //@requestBody,不能用qs
            }
        }).then(rs => {
            console.log(rs.data)
            orders = rs.data.data;
            resolve(orders);
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