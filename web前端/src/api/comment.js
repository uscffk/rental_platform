export function getComments(parm){
    console.log(parm)
    return new Promise((reslove)=>{
        axios.post('/api/commodityService/comments/queryComment',JSON.stringify(parm),{
            headers: {
                'content-Type': 'application/json'
            }
        }
       ).then(rs=>{
            console.log(rs.data.data)
            reslove(rs.data.data);
        })
    })
}