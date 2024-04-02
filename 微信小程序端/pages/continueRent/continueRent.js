// <!-- 续租 智能共享租赁平台v2.0舍弃续租这个功能 -->
Page({

  /**
   * 页面的初始数据
   */
  data: {
   commodity:{},
   goodsId:'',
   num:0,
   totalPrice:0,
   method:0,
   freeDeposit:'',
   method:'',
   orderId:''
   
  },
  onLoad:function(options){
    //得到订单id和商品id
    var that = this
    const goodsId = options.goodsId
    const orderId = options.orderId
    that.setData({
      orderId:orderId,
      goodsId:goodsId
    })
   
    //请求商品数据
     //查询商品
     wx.request({
      url: 'http://localhost:9527/commodityService/commodities/queryById?id='+that.data.goodsId,
      success:function(res){
        that.setData({
          commodity:res.data.data,
          method:res.data.data.sharingRent==1?0:res.data.data.rentBeforeBuy==1?1:2
        })
      },
      fail:function(){
        console.log('失败')
      }
    })
   
  },
  //租用时间+
  handleItemNumEditIncrease(){
    this.setData({
      num:(parseInt(this.data.num))+1,
      totalPrice:this.data.commodity.rentPrice*((parseInt(this.data.num))+1)
    })
  },
  //租用时间-
  handleItemNumEditDecrease(){
    if(this.data.num>0){
      this.setData({
        num:(parseInt(this.data.num))-1,
        totalPrice:this.data.commodity.rentPrice*((parseInt(this.data.num))-1)
      })
    }else{
      console.log('dddd')
      wx.showToast({
        title: '已经为0啦!',
        duration: 1000
      })
    }
  },

// 点击 支付 
handleOrderPay() {
  try {
   
    //到缓存里面拿用户数据
    const userinfo = wx.getStorageSync('userinfo')
    const username = userinfo.username;
  
    //发请求
    console.log(parseInt(this.data.goodsId))
    console.log(parseInt(this.data.method))
    console.log(parseInt(this.data.num))
    // console.log(parseInt(userId))
    //发起续租请求
    wx.request({
      url:'http://localhost:9527/rentService/rent/reRent',
      method:"POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded'
        },
      data:{
        orderId:parseInt(this.data.orderId),
        time:parseInt(this.data.num),
        username:username,
      },
      success:function(res){
          //跳转到订单页面
          wx.showToast({
            title: '续租成功',
            duration: 1000
          });
          wx.redirectTo({
            url: '../order/order'
          });
      },
      fail:function(){
        wx.showToast({
          title: '续租失败!',
          duration: 1000
        });
      }
    })
      
  } catch (error) {
     wx.showToast({ title: "支付失败" })
    console.log(error);
  }
},

})