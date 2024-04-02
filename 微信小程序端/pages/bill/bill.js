var urlPrefix = getApp().globalData.ipAddress;

Page({
  /**
   * 页面的初始数据
   */
  data: {
    records:[],
    type:0,
    tab:1
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
    var that = this
    const userinfo = wx.getStorageSync('userinfo')
    if(typeof(userinfo.userId) == 'undefined'){
      console.log(11111)
        wx.showToast({
          title: '请登录',
        })
        return;
    }
    //查询
    wx.request({
      url: urlPrefix+'platformService/platforms/queryRecord',
      method:"POST",
      data:{
        //类型 充值还是提现
        type:Number(that.data.type),
        fromId:Number(userinfo.userId)
      },
      success:function(res){
        //更新视图
        that.setData({
          records:res.data.data
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */

  /**
   * 充值
   */
  recharge(){
    const userinfo = wx.getStorageSync('userinfo')
    if(typeof(userinfo.userId) == 'undefined'){
      console.log(11111)
        wx.showToast({
          title: '请登录',
        })
        return;
    }
    var that = this
    that.setData({
      tab:1
    })
     //查询
     wx.request({
      url: urlPrefix+'platformService/platforms/queryRecord',
      method:"POST",
      data:{
        type:Number(0),
        fromId:Number(userinfo.userId)
      },
      success:function(res){
        that.setData({
          records:res.data.data
        })
      }
    })
  },

  /**
  * 提现
  */
  getRMB(){
    const userinfo = wx.getStorageSync('userinfo')
    if(typeof(userinfo.userId) == 'undefined'){
      console.log(11111)
        wx.showToast({
          title: '请登录',
        })
        return;
    }
    var that = this
    that.setData({
      tab:2
    })
     //查询
     wx.request({
      url: urlPrefix+'platformService/platforms/queryRecord',
      method:"POST",
      data:{
        type:Number(1),
        fromId:Number(userinfo.userId)
      },
      success:function(res){
        that.setData({
          records:res.data.data
        })
      }
    })
  }
})