var urlPrefix = getApp().globalData.ipAddress;

Page({
  data:{
    order:null,
    imagePrefix:null
  },
  onLoad:function (options) {
    this.setData({
      imagePrefix:urlPrefix
    })
    var orderId = options.id;
    console.log(orderId)
    var _this = this;
      //加载本地存储的用户名
      const userinfo = wx.getStorageSync('userinfo')
      const userId = userinfo.userId
    //发请求
    wx.request({
      url: urlPrefix+'orderService/orders/queryOrder',
      method:'POST',
      data:{
        orderId:orderId,
        userId:userId
      },
      success:function (rs) {
        console.log(rs);
        _this.setData({
          order:rs.data.data[0]
        })
      }
    })
  }
})