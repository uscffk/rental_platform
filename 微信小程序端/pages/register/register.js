var urlPrefix = getApp().globalData.ipAddress;

Page({
  /**
   * 页面的初始数据
   */
  data: {
    username:null,
    identity:null,
    realName:null,
    email:null,
    phone:null,
    password:null,
    province:null,
    city:null
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  },
  create_zhuce: function (e) {
    console.log(e.detail.value)
    wx.request({
      url: urlPrefix+'userService/users/addUser',
      method:"POST",
      data: e.detail.value,
      success: function (res) {
        if(res.data.code==200){
          wx.showToast({
            title: "注册成功",
            duration: 2000
          })
          setTimeout(function name(params) {
            wx.navigateTo({
              url: '../login/login',
            })
          },2000)
        }else{
          wx.showToast({
            title: "注册失败",
            duration: 2000
          })
        }
      }
    })
  },

})
