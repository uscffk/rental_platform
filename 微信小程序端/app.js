App({
  globalData: {
    selectGoods: [],
    ipAddress:'http://localhost:9527/',
    // ipAddress:'http://192.168.137.1:9527/'  //使用真机调试功能请用下面这个 勾选局域网调试 ip地址自己用ipconfig看换一下就好
  },
  getUserInfo: function (cb) {
    var that = this
    if (this.globalData.userInfo) {
      typeof cb == "function" && cb(this.globalData.userInfo);
    } else {
      //调用登录接口
      wx.login({
        success: function (res) { // 获取用户openid
          let code = res.code;
          wx.getUserInfo({
            success: function (res) {
              let r_data = {};
              r_data.code = code;
              r_data.encryptedData = res.encryptedData;
              r_data.iv = res.iv;
              that.globalData.userInfo = res.userInfo;
              typeof cb == "function" && cb(that.globalData.userInfo);
            }
          });
        }
      });
    }
  }
});
