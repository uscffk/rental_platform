export function afterAction(_this, flag, msg, url, type) {
    const message = {
        type: flag ? 'success' : 'error',
        message: flag ? msg + '成功' : msg + '失败'
    };
    _this.$message(message);
    clearTimeout(_this.timer);
    _this.timer = setTimeout(() => {
        if (type)
            routePush(_this, url, null);
        else
            window.location.reload();
    }, 1000);
}

export function routePush(_this, url, query) {
    _this.$router.push({
        path: url, /*给vue组件传递参数，并跳转*/
        query //vue页面接受的参数, 通过$router.query.id/..接受
    });
}

//消息提示
// export function messageDeal(title,message){

// }