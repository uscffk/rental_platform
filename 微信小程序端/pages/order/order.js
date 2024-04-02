var Zan = require('../../dist/index');
var urlPrefix = getApp().globalData.ipAddress;
Page(Object.assign({}, Zan.Tab, {
  data: {
    imagePrefix:null,
    userId:null,
    isLoading: true,
    orderTab: {
      list: [{
        id: '5',
        title: '全部'
      },
       {
        id: '0',
        title: '进行中'
      }, {
        id: '4',
        title: '已购买'
      }, {
        id: '1',
        title: '已完成'
      }],
      selectedId: '5',
      scroll: false,
      height: 45
    },
    orderlist:[],
    longitude:'',
    latitude:'',
    orderId:'',
    username:'',
    orderType:''
  },
  
  onLoad: function (options) {
    this.setData({
      imagePrefix:urlPrefix
    })
    //拿到订单类型
    var orderType = options.orderType
    console.log(orderType)
    var that = this
    that.setData({
      orderType:orderType
    })
    //读取本地存储获取id
    const userinfo = wx.getStorageSync('userinfo')
    console.log(userinfo.userId)
    this.data.userId = userinfo.userId
    if(typeof(userinfo.userId) == 'undefined'){
      console.log(11111)
        wx.showToast({
          title: '请登录',
        })
        return;
    }
    //判断订单类型是否为空
    if(typeof (orderType) == 'undefined'){
      //查询全部
      //发起请求查找全部订单
    wx.request({
      url: urlPrefix+'orderService/orders/queryOrder',
      method:'POST',
      data:{
        userId : userinfo.userId
      },
      success:function(res){
       
        console.log(res.data.data)
          that.setData({
            orderlist:res.data.data
          })
      }
    })
    }else{
      //按类型查询 需要带参数
      wx.request({
        url: urlPrefix+'orderService/orders/queryOrder',
        method:'POST',
        data:{
          userId : userinfo.userId,
          orderType: Number(orderType)
        },
        success:function(res){
          console.log(res.data.data)
            that.setData({
              orderlist:res.data.data
            })
        }
      })
    }
    // 模拟请求
    setTimeout(() => {
      this.setData({
        isLoading: false
      });
    }, 500);
  },
  //续租 这个功能不要了
  continueRent(e){
    wx.redirectTo({
      //携带商品id
      url:'../continueRent/continueRent?goodsId='+goodsId+'&orderId='+orderId
    })
  },

  //购买
  goBuy(e){
    const orderId = e.currentTarget.dataset.key
    //加载本地存储的用户名
    const userinfo = wx.getStorageSync('userinfo')
    const userId = userinfo.userId
    var that = this
    //发起请求  请求购买需要付的钱
    wx.request({
    url: urlPrefix+'rentService/rent/calculateBuyMoney',
    method:'POST',
    header: {
      'content-type': 'application/x-www-form-urlencoded' // 默认值
    },
    data:{
     orderId: parseInt(e.currentTarget.dataset.key),
     userId: parseInt(userId)
    },
    success:function(res){
       var money;
       if(res.data.code==200){
         //弹框 提示需要支付多少钱
         money = res.data.data;
         wx.showModal({
           title:"支付金额",
           cancelColor: 'cancelColor',
           content:'购买此商品您共需要支付￥'+res.data.data,
           success:function(res){
            if (res.confirm) {  
              //发购买请求
              wx.request({
                url: urlPrefix+'rentService/rent/buyCommodity',
                method:'POST',
                header: {
                  'content-type': 'application/x-www-form-urlencoded' // 默认值
                },
                data:{
                 orderId: parseInt(e.currentTarget.dataset.key),
                 userId: parseInt(userId),
                 money: parseInt(money)
                },
                success:function (res) {
                    //提示
                    if(res.data.code==200){
                      wx.showToast({
                        title: '购买成功',
                        duration:2000
                      })
                    }
                }
              })
             } else if (res.cancel) {  
          }  
          }
         })
       }else{
         wx.showToast({
           title: '购买失败,余额可能不足',
           icon: 'none',
           duration: 2000
         })
       }
    },
    fail:function(){
     wx.showToast({
       title: '购买失败',
       icon: 'none',
       duration: 2000
     })
    },
    })
  },
  //评价
  goComment(e){
    console.log(e)
    //因为设计的问题 这里绕个弯 先通过订单Id查NFT ID
    var _this = this;
    wx.request({
      url: urlPrefix+'orderService/orders/queryNFTByOrderId',
      method:"POST",
      data:{
        orderId:parseInt(e.currentTarget.dataset.key)
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success:function(res){
        var nftId = res.data.data;
        //跳评论页面
        wx.navigateTo({
          url: '../comments/comment?nftId='+nftId+'&needComment=1&orderId='+e.currentTarget.dataset.key
        })
      }
    })
  },
  //订单详情
  goOrderDetail(e){
    const {key} = e.currentTarget.dataset;
    console.log(key)
    wx.navigateTo({
      url: './orderDetail?id='+key,
    })
  },
  //tab切换
  handleZanTabChange(e) {
    var componentId = e.componentId;
    var selectedId = e.selectedId;
    var that = this
    console.log(selectedId);
    //判断是否是查询全部
    if(that.data.orderType!=''){
      //需要加上orderType
      if(selectedId=='5'){
        //发起查询
      wx.request({
        url: urlPrefix+'orderService/orders/queryOrder',
        method:"POST",
        data:{
          userId:that.data.userId,
          orderType:that.data.orderType
        },
        success:function(res){
          console.log(res.data.data)
          that.setData({
            orderlist:res.data.data
          })
        }
    })
    }else{
      //发起查询
      wx.request({
        url: urlPrefix+'orderService/orders/queryOrder',
        method:"POST",
        data:{
          userId:that.data.userId,
          status:Number(selectedId),
          orderType:that.data.orderType
        },
        success:function(res){
          console.log(res)
          that.setData({
            orderlist:res.data.data
          })
        }
      })
    }
    }else{
      if(selectedId=='5'){
        //发起查询
        console.log('查询全部')
      wx.request({
        url: urlPrefix+'orderService/orders/queryOrder',
        method:"POST",
        data:{
          userId:that.data.userId,
        },
        success:function(res){
          console.log(res.data.data)
          that.setData({
            orderlist:res.data.data
          })
        }
    })
    }else{
      console.log('111111111111111111111')
      //发起查询
      wx.request({
        url: urlPrefix+'orderService/orders/queryOrder',
        method:"POST",
        data:{
          userId:that.data.userId,
          status:Number(selectedId)
        },
        success:function(res){
          that.setData({
            orderlist:res.data.data
          })
        }
      })
    }
    }
    this.setData({
      [`${componentId}.selectedId`]: selectedId
    })
  }
}))
