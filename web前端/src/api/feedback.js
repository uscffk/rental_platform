import qs from 'qs'

export function deleteFeedBack(id) {
    return new Promise((resolve) => {
        axios.post('/api/userService/feedback/deleteFeedBack', qs.stringify({ id: id }), ).then(rs => {
            resolve(rs.data.code);
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