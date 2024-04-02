var urlPrefix = getApp().globalData.ipAddress;

Page({
  /**
   * 页面的初始数据
   */
  data: {
   imagePrefix:null,
   commodity:{},
   goodsId:'',
   num:0,
   totalPrice:0,
   method:0,
   freeDeposit:'',
   nftId:null
   
  },
  onLoad:function(options){
    this.setData({
      imagePrefix:urlPrefix
    })
    //加载本地存储判断是否为免押用户
    var that = this;

    //获取商品id
    that.setData({
      goodsId:options.goodsId,
      nftId:options.nftId
    })
    //查询商品
    wx.request({
      url: urlPrefix+'commodityService/commodities/queryById?id='+that.data.goodsId,
      success:function(res){
        that.setData({
          commodity:res.data.data,
          method:res.data.data.rentForSale==1?1:res.data.data.rentBeforeBuy==1?2:0
        })
      },
      fail:function(){
        console.log('失败')
      }
    })
  },
  //租用时间+
  handleItemNumEditIncrease(){
    //判断
    if((parseInt(this.data.num))+1>this.data.commodity.timeNumber){
      wx.showModal({
        cancelColor: 'cancelColor',
        title:'提示',
        content:'期数超过最大上限'
      })
    }else{
      this.setData({
        num:(parseInt(this.data.num))+1,
        totalPrice:this.data.commodity.rentPrice*((parseInt(this.data.num))+1)
      })
    }
    
  },
  //租用时间-
  handleItemNumEditDecrease(){
    if(this.data.num>0){
      this.setData({
        num:(parseInt(this.data.num))-1,
        totalPrice:this.data.commodity.rentPrice*((parseInt(this.data.num))-1)
      })
    }else{
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
    const userId = userinfo.userId;

    wx.request({
      url:urlPrefix+'rentService/rent/addOrder',
      method:"POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded'
        },
      data:{
        goodId:parseInt(this.data.goodsId),
        userId:parseInt(userId),
        method:parseInt(this.data.method),
        totalTime:parseInt(this.data.num),
        nftId:parseInt(this.data.nftId)
      },
      success:function(res){
        console.log(res)
        if(res.data.code==444){
          wx.showModal({
            cancelColor: 'cancelColor',
            content:res.data.message
          })
          
          return;
        }
          console.log(res)
          //跳转到订单页面
          wx.showToast({
            title: '下单成功',
            duration: 1000
          });
          setTimeout(function () {
            wx.redirectTo({
              url: '../order/order'
            });
          },500)
         
      }
    })
    
      
  } catch (error) {
     wx.showToast({ title: "支付失败" })
    console.log(error);
  }
},
  
})