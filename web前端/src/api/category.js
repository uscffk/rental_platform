import constants from "../store/constants";

export function queryCategories() {
    return new Promise(function (resolve) {
        axios.get('/api/commodityService/categories/')
        .then(rs => {
            console.log(rs.data)
            resolve(rs.data.data);
        }).catch(() => {
            console.log(1)
            resolve([{id: 1, name: '生活'}, {id: 2, name: '交通'}, {id: 3, name: '生活'}, {id: 4, name: '交通'}, {
                id: 5,
                name: '生活'
            }, {id: 6, name: '交通s'}]);
        })
    })
}

export function updateCategory(category) {
    return new Promise(function (resolve) {
        axios.put('/api/commodityService/categories/' + category.id, category).then(rs => {
            resolve(rs.data);
        }).catch(() => {
            resolve({code: constants.state.FAIL, message: '更新失败'});
        })
    })
}

export function addCategory(category) {
    return new Promise(function (resolve) {
        axios.post('/api/commodityService/categories/', category,{
            headers: {
                'content-type': 'application/json' //@requestBody,不能用qs
            }
        }).then(rs => {
            resolve(rs.data);
        }).catch(() => {
            resolve({code: constants.state.FAIL, message: '更新失败'});
        })
    })
}


export function deleteCategory(id) {
    return new Promise(function (resolve) {
        axios.post('/api/commodityService/categories/' + id).then(rs => {
            resolve(rs.data);
        }).catch(() => {
            resolve({code: constants.state.FAIL, message: '删除失败'});
        })
    })
}

export function queryById(id) {
    return new Promise(function (resolve) {
        axios.get('/api/commodityService/categories/' + id).then(rs => {
            resolve(rs.data.data);
        }).catch(() => {
            resolve({id: 1, name: '暂无信息'});
        })
    })
}