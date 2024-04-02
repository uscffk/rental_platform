var urlPrefix = getApp().globalData.ipAddress;

Page({
  data:{
    latitude: 0,
    longitude: 0,
    detailLoc:null,
    order:{},
    nftCommodity:{},
    nftId:null,
    markers:[]
  },
  onLoad:function(options){
      var nftId = options.nftId;
      this.data.nftId = options.nftId
      var _this = this
      //请求定位
      wx.getLocation({
        type:'wgs84',
        altitude: true,
        success(res){
          //设置当前定位
          console.log(res)
          _this.setData({
            latitude : res.latitude,
            longitude : res.longitude,
          });
        }
      });
      _this.mapCtx = wx.createMapContext('myMap')
      //发请求 订单信息
      wx.request({
        url: urlPrefix+'orderService/orders/queryByNFTID',
        method:"POST",
        data:{
          nftId:parseInt(nftId)
        },
        header: {
          'content-type': 'application/x-www-form-urlencoded' // 默认值
        },
        success:function(res){
          console.log(res)
          _this.setData({
            order:res.data.data
          })
        }
      })
      //请求NFT信息
      wx.request({
        url: urlPrefix+'commodityService/nftCommodity/queryById',
        method:"POST",
        header: {
          'content-type': 'application/x-www-form-urlencoded' // 默认值
        },
        data:{
          nftId:parseInt(nftId)
        },
        success:function(res){
          _this.setData({
            nftCommodity:res.data.data
          })
          //设置归还站点 marker
          _this.setData({
            markers:_this.getFalseDataLocations(res.data.data.commodity.backLocations)
          })
        }
      })
  },
  //获取mark标记点数据
  getFalseDataLocations(backLocations){
    let myMarker = [];
    for (let item of backLocations){
      //创建标记点
       let singleMark = this.createMyMarker(item);
       myMarker.push(singleMark)
    }
    return myMarker;
  },
  //创建标记点
  createMyMarker(point){
    let mark = {
      id:point.id,
      detailLoc:point.detailLoc|| 0,
      //这个打算进行拼接
      iconPath: '../../images/退租.png',
      latitude: point.latitude || 0,
      longitude: point.longitude || 0,
      width: 30,
      height: 30
    };
    return mark;
  },
  //marker的点击 
  clickMarker(e){
    this.gomap(e);
 },
 //地图导航
 gomap(e){
   //获取点击的纬度
    var lat = '';
    //获取点击的经度
    var lon = '';
    //获取点击的详细地址
    var name = '';
    //获取点击的markers id
    var markerId = e.markerId
    //获取markers列表
    var markers = e.currentTarget.dataset.markers;
    //遍历
    for(let item of markers){
      if(item.id === markerId){
        lat = item.latitude;
        lon = item.longitude;
        name = item.detailLoc;
        let endPoint = JSON.stringify({  //终点
          'name': name,
          'latitude': Number(lat),
          'longitude': Number(lon)   
        });
        //弹框
        wx.showModal({
          title:"是否立即前往",
          cancelColor: 'cancelColor',
          content:'归还站点:'+name+" \r\n ",
          success:function(res){
            if (res.confirm) {  
              wx.navigateTo({
                url: 'plugin://routePlan/index?key=' + 'Y74BZ-K3XWQ-M2W5I-G2QBG-26PN7-TTFKZ' + '&referer=' + '智能化共享租赁平台' + '&endPoint=' + endPoint
              });
             } else if (res.cancel) {  
               //不做处理
          }  
          }
        })
        break;
      }
}},
  //点击退租
  backRent(){
    if(this.data.order.orderType==1){
      wx.showToast({
        title: '以租代售不退租',
      })

      return;
    }
    var _this = this;
    //弹出框
      wx.showModal({
        title:'确定退租吗?',
        cancelColor: 'cancelColor',
        success:function(res){
          if(res.confirm){
            wx.request({
              url: urlPrefix+'backService/backCommodity',
              method:"POST",
              data:{
                orderId:parseInt(_this.data.order.orderId),
                longitude:_this.data.longitude,
                latitude:_this.data.latitude,
                nftId:parseInt(_this.data.nftId)
              },
              header: {
                'content-type': 'application/x-www-form-urlencoded' // 默认值 不加这个报400
              },
              success:function(res){
                if(res.data.data==0){
                  wx.showToast({
                    title: '不在退租范围内',
                    icon:'error',
                    duration:2000
                  })
                }else if(res.data.data==1){
                    wx.showToast({
                      title: '余额不足请充值',
                      icon:'error',
                      duration:2000
                    })
                }else if(res.data.data==2){
                  wx.showToast({
                    title: '退租成功!',
                    icon:'success',
                    duration:4000,
                    success:function(){
                      //停0.5s
                      setTimeout(function () {
                         //跳首页
                        wx.switchTab({
                          url: '../index/index'
                        });
                      },500)
                    }
                  })
                }    
              }
    })
          }else{
            //不退租继续使用
          }
        }
      })
  },
// 回首页
  goIndex(){
    // 注意区分什么时候用wx.switchTab wx.redirectTo 和wx.navigateTo
    wx.switchTab({
      url: '../index/index',
    })
  }
})