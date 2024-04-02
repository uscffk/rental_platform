var login = require("../login/login")
var urlPrefix = getApp().globalData.ipAddress;
Page({
  data: {
    disabled: true,
    btnstate: "default",
    account: "",
    password: ""
  },
  login(){
    //发起请求
    wx.request({
      url: urlPrefix+'userService/login',
      method:"post",
      data: {
        username: this.data.account,
        password: this.data.password
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success:function(res){
        //代表验证成功
        if (res.data.code == 200){
          var userinfo = {
            userhead:res.data.data.userhead,
            username:res.data.data.username,
            userId:res.data.data.id
          }
          wx.setStorageSync('userinfo',userinfo)
          console.log(userinfo)
          wx.showToast({
            title: '登录成功',
            icon: 'none',
            duration: 2000
          })
          wx.switchTab({
            url: '../mine/mine',
            success:function(res){
              var page = getCurrentPages().pop();
                              if (page == undefined || page == null) return;
                              page.onLoad();
            }
          })

        
        }else{   //验证失败
          wx.showToast({
            title: res.data.message,
            icon: 'none',
            duration: 2000
          })
        }
      }
    })
  },
  goRegister(){
    console.log(33433)
    wx.navigateTo({
      url: '../register/register',
    })
  },
  accountInput: function (e) {
    var content = e.detail.value;
    console.log(content);
    if (content != "") {
      this.setData({ disabled: false, btnstate: "primary", account: content });
    }
  },
  pwdBlur: function (e) {
    var password = e.detail.value;
    if (password != "") {
      this.setData({ password: password });
    }
  }
})