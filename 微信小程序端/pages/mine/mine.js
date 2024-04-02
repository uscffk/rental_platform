var app = getApp();
var urlPrefix = getApp().globalData.ipAddress;
Page({
  data: {
    userinfo: {},
    cartGoodCount: 0,
    imagePrefix:null
  },
  onLoad() {
    this.setData({
      imagePrefix:urlPrefix
    })
    //加载本地存储
    const userinfos = wx.getStorageSync('userinfo')
    console.log(userinfos)
    this.setData({
      userinfo:userinfos
    })
  },
  gofeedback(){
    wx.navigateTo({
      url: '../feedback/feedback'
    });
  },
  loginOut(){
    wx.clearStorageSync();
    wx.navigateTo({
      url: '../login/login',
    })
  },
  rentbeforebuy(){
    wx.navigateTo({
      url: '../order/order?orderType=2'
    });
  },
  sharingrent(){
    wx.navigateTo({
      url: '../order/order?orderType=0'
    });
  },
  rentforsale(){
    wx.navigateTo({
      url: '../order/order?orderType=1'
    });
  },
  goUserInfo(){
    wx.navigateTo({
      url: '../userinfo/userinfo'
    });
  },
  goRecharge(){
    wx.navigateTo({
      url: '../recharge/recharge'
    });
  },
  goOrder(event) {
    wx.navigateTo({
      url: '../order/order'
    });
  },
  goCart() {
    wx.navigateTo({
      url: '../like/like',
    })
  },
  goDeposit(){
    wx.navigateTo({
      url: '../deposit/deposit'
    })
  },
  onShow() {
    this.upDateCount();
  },
  expenseRecord(){
    wx.navigateTo({
      url: '../bill/bill',
    })
  },
  upDateCount() {
    let count = 0;
    app.globalData.selectGoods.forEach((item, index) => {
      count += item.count;
    });
    this.setData({
      cartGoodCount: count
    })
  }
})