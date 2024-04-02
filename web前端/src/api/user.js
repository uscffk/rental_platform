export function getBasicUserInfo() {
    return {
        id: 1,
        user_name: 'user',
        user_password: '123',
        contract_address: '0x22312312231',
        iphone: '1829138291',
        email: '23718293@qq.com',
        identity: '232183712983791',
        real_name: 'hll'
    }
}

export function queryFeedBack(id) {
    return new Promise((resolve) => {
        axios.post('/api/userService/feedback/queryFeedback', { manufacturerId: id }, {
            headers: {
                contentType: 'application/json'
            }
        }).then(rs => {
            resolve(rs.data.data);
        }).catch(() => {
            let backs = [];
            let feed = { id: null, content: null, userId: 1, manufacturerId: 1 }
            for (let i = 0; i < 11; i++) {
                backs.push(JSON.parse(JSON.stringify(feed)));
                backs[i].id = i;
                backs[i].content = '鸡你太美' + i;
            }
            resolve(backs);
        })
    })
}

export function queryUsers(parm) {
    console.log(parm)
    return new Promise((resolve)=>{
        axios.post('/api/userService/users/queryUsers', JSON.stringify(parm), {
            headers: {
                'content-type': 'application/json'
            }
        }).then(rs => {
            console.log(rs.data)
            resolve(rs.data.data);
        }).catch(() => {
            let users = [];
            for (let i = 0; i < 11; i++) {
                users.push(JSON.parse(JSON.stringify(getBasicUserInfo())));
                users[i].id = i;
            }
            resolve(users);
        })
    })
}

export function updateUser(parm){
    console.log(parm)
    return new Promise((resolve)=>{
        axios.post('/api/userService/users/updateUsers',JSON.stringify(parm),{
            headers: {
                'content-type': 'application/json' //@requestBody,不能用qs
            }
        }).then(rs=>{
            console.log(rs.data)
            resolve(rs.data.code);
        })
    })
}