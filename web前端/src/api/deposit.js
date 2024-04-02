export function queryAllDeposit(){
    return new Promise((reslove)=>{
        axios.post('/api/depositService/deposit/queryAllDeposit').then(rs=>{
            console.log(rs.data.data)
            reslove(rs.data.data);
        })
    })
}

//查询押金池余额
export function queryDepositPoolBalance(){
    return new Promise((reslove)=>{
        axios.post('/api/depositService/deposit/queryDepositPoolBalance').then(rs=>{
            console.log(rs.data.data)
            reslove(rs.data.data);
        })
    })
}
