var app = getApp();
var Zan = require('../../dist/index');
var WxParse = require('../../wxParse/wxParse.js');
var urlPrefix = getApp().globalData.ipAddress;
Page(Object.assign({}, Zan.Quantity, Zan.TopTips, {
  data: {
    imagePrefix:null,
    goodsObj:{},
    nftId:null,
    imgUrls: [],
    goodName: "加载中...",
    sellPrice:'',
    status:0,
    discription:'',
    detail: "",
    kinds: [],
    kindName: "",
    current: 0,
    total: 0,
    count: 1,
    cartGoodCount: 0,
    smpic: "",
    indicatorDots: true,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    showDialog: false,
    quantity: {},
    goOrder: true
  },
  
  onLoad(option) {
    this.setData({
      imagePrefix:urlPrefix
    })
    let that = this;
    var nftId = option.nftId
    this.data.nftId = option.nftId
     //发起请求查询商品
     wx.request({
      url: urlPrefix+'commodityService/nftCommodity/query',
      method:'POST',
      data:{
        nftTokenId:nftId
      },
      success:function(res){
        that.setData({
          goodsObj:res.data.data
        })
        console.log(that.data.goodsObj)
      },
      fail:function(){
        console.log("请求失败了")
      }
    })
    
  },
  //跳评论页面
  goComments(){
    var that = this;
    wx.navigateTo({
      url: '../comments/comment?nftId='+that.data.nftId
    })
  },

  onShow() {
  },
  
  goIndex() {
    wx.switchTab({
      url: '../index/index'
    });
  },
  goOrder(){
    const userinfos = wx.getStorageSync('userinfo')
    if(typeof(userinfos.userId) == 'undefined'){
      console.log(11111)
        wx.showToast({
          title: '请登录',
        })
        return;
    }
    var that = this
    if(that.data.goodsObj[0].status==0){
      wx.redirectTo({
        url: '../pay/pay?goodsId='+ that.data.goodsObj[0].goodsId+'&nftId='+that.data.goodsObj[0].nftTokenId
      })
    }else{
      wx.showModal({
        cancelColor: 'cancelColor',
        title:'提示',
        content:that.data.goodsObj[0].status==1?'商品维护中':that.data.goodsObj[0].status==2?'商品使用中':'商品已售出'
      })
    }
  },
  initData() {
    var that = this
    var goods = this.data.goodsObj[0]
    console.log(goods.commodity.picture)
    this.setData({
      imgUrls: goods.commodity.picture,
      goodName: goods.commodity.name,
      rentPrice: goods.commodity.rentPrice,
      sellPrice:goods.commodity.sellPrice,
      kindName: goods.commodity.categories.name,
      status:goods.status
    });
    var article = goods.detail;
    WxParse.wxParse('article', 'html', article, that);
  },
  upDateCount() {
    let count = 0;
    app.globalData.selectGoods.forEach((item, index) => {
      count += item.count;
    });
    this.setData({
      cartGoodCount: count
    })
  }
}));