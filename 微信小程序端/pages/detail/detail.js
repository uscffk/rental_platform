var Zan = require('../../dist/index');
var urlPrefix = getApp().globalData.ipAddress;
Page(Object.assign({}, Zan.Quantity, Zan.TopTips, {
  data: {
    goodsObj:{},
    //该商品是否已被收藏 默认没有
    islike:0,
    imagePrefix:null
  },

// 响应立即前往 其实是在一个地图里面加载出对应商品Id的所有NFT位置
gomap(e){ 
    console.log(this.data.goodsObj.id)
    var that = this;
    //这里本来想页面复用带参数跳index首页的 奈何tabBar类型不支持直接跳转  而switch又不能带参数 只能用navigateTo了  算了直接加一个页面吧
    wx.navigateTo({
      url: '../goodsBygoodsId/goodsBygoodsId?goodsId='+that.data.goodsObj.id
    })
},
  onLoad(option) {
    this.setData({
      imagePrefix:urlPrefix
    })
    let that = this;
    var goodsId = option.gid
    const userinfo = wx.getStorageSync('userinfo')
     //发起请求查询商品
     wx.request({
      url: urlPrefix+'commodityService/commodities/queryById?id='+goodsId,
      success:function(res){
        that.setData({
          goodsObj:res.data.data
        })
        console.log(that.data.goodsObj)
      },
      fail:function(){
        console.log("请求失败了")
      }
    });
    //查询是否被收藏 这种发请求建议后面接手的人封装一下 写的有点烦了 我懒得改了  
    const userinfos = wx.getStorageSync('userinfo')
    if(typeof(userinfos.userId) == 'undefined'){
      return;
    }
    wx.request({
      url: urlPrefix+'userService/userLike/query',
      method:"POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      data:{
          userId:Number(userinfo.userId)
      },
      success:function(res){
        console.log(res.data.data)
        var data = res.data.data;
        //遍历
        for(let i = 0;i<data.length;i++){
          if(data[i].comId == goodsId){
            that.setData({
              islike:1
            })
          }
        }
        
      }
    })

  },
  //收藏
  like(){
    const userinfos = wx.getStorageSync('userinfo')
    if(typeof(userinfos.userId) == 'undefined'){
      console.log(11111)
        wx.showToast({
          title: '请登录',
        })
        return;
    }
    //判断是不是已经收藏
    if(this.data.islike==1){
      //已收藏
      wx.showToast({
        title: '商品已收藏',
        icon: 'none',  
        duration: 2000
      })
    }else{
       //获取用户id
    const userinfo = wx.getStorageSync('userinfo')
    var that = this
    //发起请求
    wx.request({
      url: urlPrefix+'userService/userLike/add',
      method:"POST",
      data:{
        userId:Number(userinfo.userId),
        comId:Number(that.data.goodsObj.id)
      },
      success:function(res){
        if(Number(res.data.code)==200){
          wx.showToast({
            title: '收藏成功',
            icon: 'none',
            duration: 2000
          })
          //更新
          that.setData({
            islike:1
          })
        }
      }
    })
    }
  },
  goIndex() {
    wx.switchTab({
      url: '../index/index'
    });
  },
}));