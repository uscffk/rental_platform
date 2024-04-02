var urlPrefix = getApp().globalData.ipAddress;
  /**
   * 页面的初始数据
   */
  Page({
    data: {
      activeIndex: 0, //默认选中第一个
      numArray: [20, 30, 50, 80, 100,'m'],
      balance:'',
      amount:''
    },
    onLoad(){
      var that = this
      //获取本地存储
      const userinfos = wx.getStorageSync('userinfo')
      if(typeof(userinfos.userId) == 'undefined'){
        console.log(11111)
          wx.showToast({
            title: '请登录',
          })
          return;
      }
      const username = userinfos.username
      //查询余额
      wx.request({
        url: urlPrefix+'userService/users/queryBalance',
      method:"POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded'
        },
      data:{
        username:username+""
      },
      success:function(res){
        that.setData({
          balance:res.data.data
        })
      }
      })
    },
    recharge(){
      var that = this
      const userinfos = wx.getStorageSync('userinfo')
      if(typeof(userinfos.userId) == 'undefined'){
        console.log(11111)
          wx.showToast({
            title: '请登录',
          })
          return;
      }
  
      //获取要充值的金额
      var amount = that.data.numArray[that.data.activeIndex]
      if(amount=='m'){
        amount = that.data.amount
      }
      //发请求
      wx.request({
        url: urlPrefix+'platformService/platforms/recharge',
        method:"POST",
        data:{
          userId:Number(userId),
          amount:Number(amount),
          from:Number(0)
        },
        header: {
          'content-type': 'application/x-www-form-urlencoded' // 默认值
        },
        success:function(res){
          console.log(res)
          if(res.data.code==200){
            wx.showToast({
              title: '充值成功',
              icon: 'none',
              duration: 2000
            })
          }else{
            wx.showToast({
              title: '充值失败',
              icon: 'none',
              duration: 2000
            })
          }
          //刷新页面
          that.setData({
            balance:Number(that.data.balance)+Number(amount)
          })
        }
      })
    },
    getRmb(){
      var that = this
      const userinfos = wx.getStorageSync('userinfo')
      if(typeof(userinfos.userId) == 'undefined'){
        console.log(11111)
          wx.showToast({
            title: '请登录',
          })
          return;
      }
      const username = userinfos.username
      //获取要提现的金额
      var amount = that.data.numArray[that.data.activeIndex]
      if(amount=='m'){
        amount = that.data.amount
      }
      if(Number(amount)>Number(that.data.balance)){
        wx.showToast({
          title: '钱包余额不足',
          icon: 'none',
          duration: 2000
        })
        return;
      }
      //发请求
      wx.request({
        url: urlPrefix+'platformService/platforms/getRMB',
        method:"POST",
        data:{
          id:Number(userinfos.userId),
          amount:Number(amount),
          from:Number(0)
        },
        header: {
          'content-type': 'application/x-www-form-urlencoded' // 默认值
        },
        success:function(res){
          wx.showToast({
            title: '提现成功',
            icon: 'none',
            duration: 2000
          })
          //刷新页面
          that.setData({
            balance:Number(that.data.balance)-Number(amount)
          })
        },
       
        
      })
    },
    amountInput: function(e) {
      this.setData({
        amount: e.detail.value
      })
    },
    activethis: function (event) { //点击选中事件
      var thisindex = event.currentTarget.dataset.thisindex; //当前index
      this.setData({
        activeIndex: thisindex
      })
    }
  })
  

