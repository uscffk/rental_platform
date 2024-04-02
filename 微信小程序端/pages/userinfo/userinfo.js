var urlPrefix = getApp().globalData.ipAddress;

Page({
  data: {
  userInfo:{},
  imagePrefix:null
   },
   chooseimage: function () {
       var _this = this;
       wx.chooseImage({
         count: 1, // 默认9
         sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
         sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
         success: function (res) {
           // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
           _this.setData({
             userInfoAvatar:res.tempFilePaths
           })
         },
         radioChange: function(e) {
          console.log('radio发生change事件，携带value值为：', e.detail.value)
        },
      })
    },
  onLoad: function () {
    this.setData({
      imagePrefix:urlPrefix
    })
    //获取本地存储的用户id
    var that=this;
    const userinfo = wx.getStorageSync('userinfo')
    const usersId = userinfo.userId
    console.log(usersId)
    //发请求获取数据
    wx.request({
      url: urlPrefix+'userService/users/queryById',
      method:"post",
      data: {
        id:usersId
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success:function(res){
        console.log(res.data.data)
        //进行赋值
        that.setData({
          userInfo:res.data.data
        })
       
      }
    })
    
  }
})