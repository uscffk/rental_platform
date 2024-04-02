var urlPrefix = getApp().globalData.ipAddress;
Page({
  data:{
    imagePrefix:null,
    deposits:null,
    tabs: [
      {
        id: 0,
        value: "有效",
        isActive: true
      },
      {
        id: 1,
        value: "已退回",
        isActive: false
      }
    ],
  },
  onLoad(){
    this.setData({
      imagePrefix:urlPrefix
    })
    //读取本地存储获取id
    const userinfo = wx.getStorageSync('userinfo');
    var userId = userinfo.userId;
    var _this = this;
    //发请求
    wx.request({
      url: urlPrefix+'depositService/deposit/queryDepositByUserId',
      method:"POST",
      data:{
        userId:parseInt(userId)
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success:function(res){
        if(res.data.data.length==0){
          wx.showToast({
            title: '空空如也',
          })
        }
        console.log(res.data.data)
        _this.setData({
          deposits:res.data.data
        })
      }
    })


  },
  handleTabsItemChange(e) {
    // 1 获取被点击的标题索引
    const { index } = e.detail;
    if(index==1){
      this.setData({
        isforPlatform:1
      })
    }else{
      this.setData({
        isforPlatform:0
      })
    }
    // 2 修改源数组
    let { tabs } = this.data;
    tabs.forEach((v, i) => i === index ? v.isActive = true : v.isActive = false);
    // 3 赋值到data中
    this.setData({
      tabs
    })
  },
})