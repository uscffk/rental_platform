var urlPrefix = getApp().globalData.ipAddress;

Page({
  data: {
    latitude: 0,
    longitude: 0,
    markers: [],
    backLocations:[],
  },
  onReady: function () {
    var _this = this
    wx.getLocation({
      type:'wgs84',
      altitude: true,
      success(res){
      //设置当前定位
        _this.setData({
          latitude : res.latitude,
          longitude : res.longitude,
        });
      }
    });
      //向后端发请求  这里先直接拿所有数据过来标点
      //后续有需要再写一个接口 加载一定半径区域内的商品
        var _this = this;
        wx.request({
          url: urlPrefix+'commodityService/backLocation/queryAll',
          method:'POST',
          data:{},
          //成功回调
          success:function(res){
            console.log(res);
            _this.backLocations = res.data.data;
            //视图中写了所以可以使用set赋值
            _this.setData({
             markers:_this.getFalseDataLocations()     
            })
            console.log(_this.data.markers)
          },
          //失败回调
          fail:function(){
            wx.showToast({
              title: 'error',
              icon:'error',
              duration:2000
            })
          }
      })
        _this.mapCtx = wx.createMapContext('myMap')
  },
  // 搜索入口  
  wxSearchTab: function () {
      wx.redirectTo({
        url: '../search/search',
      })
    },
  //获取mark标记点数据
  getFalseDataLocations(){
    let myMarker = [];
    var backLocations = this.backLocations;
    for (let item of backLocations){
      //创建标记点
       let singleMark = this.createMyMarker(item);
       myMarker.push(singleMark)
    }
    console.log(myMarker)
    return myMarker;
  },
  //创建标记点
  createMyMarker(point){
    let mark = {
      id: point.id || 0,
      detailLoc:point.detailLoc|| 0,
      commodityId:point.commodityId||0,
      //这个打算进行拼接
      iconPath: '../../images/_租赁业 .png',
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
   console.log(e)
   //获取点击的纬度
    var lat = '';
    //获取点击的经度
    var lon = '';
    //获取点击的详细地址
    var name = '';
    //获取点击的markers id
    var markerId = e.markerId
    console.log(markerId)
    //获取markers列表
    var markers = e.currentTarget.dataset.markers;
    console.log(markers)
    //遍历
    for(let item of markers){
      console.log(item)
      if(item.id === markerId){
        console.log(1111111)
        lat = item.latitude;
        lon = item.longitude;
        console.log(lat)
        name = item.detailLoc;
        let endPoint = JSON.stringify({  //终点
          'name': name,
          'latitude': Number(lat),
          'longitude': Number(lon)   
        });
        //请求商品数据
        wx.request({
          url: urlPrefix+'commodityService/commodities/queryById',
          method:'GET',
          data:{
            id:Number(item.commodityId)
          },
          header: {
            'content-type': 'application/x-www-form-urlencoded' // 默认值
          },
          success:function (res) {
            //弹框
            if(res.data.code==200){
              wx.showModal({
                title:"是否立即前往",
                cancelColor: 'cancelColor',
                content:'商品名称:'+res.data.data.name,
                success:function(res){
                    if (res.confirm) {  
                      wx.navigateTo({
                        url: 'plugin://routePlan/index?key=' + 'Y74BZ-K3XWQ-M2W5I-G2QBG-26PN7-TTFKZ' + '&referer=' + '智能化共享租赁平台' + '&endPoint=' + endPoint
                      });
                    } else if (res.cancel) {  
                    console.log('用户点击取消')  
                  }  
                  }
                })
            }
          
          }
        })
        
        break;
      }
}},
  //获取中心位置
  getCenterLocation: function () {
    this.mapCtx.getCenterLocation({
      success: function(res){
        console.log(res.longitude)
        console.log(res.latitude)
      }
    })
  },
  moveToLocation: function () {
    this.mapCtx.moveToLocation()
  },
  translateMarker: function() {
    this.mapCtx.translateMarker({
      markerId: 1,
      autoRotate: true,
      duration: 1000,
      destination: {
        latitude:23.10229,
        longitude:113.3345211,
      },
      animationEnd() {
        console.log('animation end')
      }
    })
  },
  includePoints: function() {
    this.mapCtx.includePoints({
      padding: [10],
      points: [{
        latitude:23.10229,
        longitude:113.3345211,
      }, {
        latitude:23.00229,
        longitude:113.3345211,
      }]
    })
  }
})
