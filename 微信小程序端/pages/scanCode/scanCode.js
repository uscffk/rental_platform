 //首先我们在data里定义一个变量
 var urlPrefix = getApp().globalData.ipAddress;
 Page({
 //首先我们在data里定义一个变量
 data: {
  on: true
},
//紧接着我们在onShow里边定义
//这里我们不能定义到onLoad里，否则只能加载一次。
onShow: function () {
  console.log('onShow')
  var that = this
  if (that.data.on) {
    var call = that.data.call
    wx.scanCode({
      success: (res) => {
        var id = res.result
        //读取本地存储获取id
        const userinfo = wx.getStorageSync('userinfo');
        var userId = userinfo.userId;
        console.log(parseInt(id))
        //判断跳转到哪个页面
        wx.request({
          url: urlPrefix+'rentService/rent/judge',
          method:"POST",
          data:{
            nftId:parseInt(id),
            userId:parseInt(userId)
          },
          header: {
            'content-type': 'application/x-www-form-urlencoded' // 默认值
          },
          success:function(res){
            if(res.data.data===1){
              //跳退租\订单详情页面
              wx.redirectTo({
              url: '/pages/backrent/backrent?nftId=' + id,
            })
            }else{
                wx.redirectTo({
                  //进入商品详情页面
                  url: '/pages/rent/rent?nftId=' + id,
                })
            }
            
          }
        })
      
      },
      fail: (err) => {
        console.log(err, 'fail')
        wx.switchTab({
          url: '/pages/index/index',
        })
      }
    })
  }
},
//我们在监听onHide事件中
onHide: function () {
  var that = this
  var on = that.data.on
  that.setData({
    on: !on
  })
},


 })

