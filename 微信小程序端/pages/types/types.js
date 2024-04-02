var urlPrefix = getApp().globalData.ipAddress;
Page({
  data: {
    imagePrefix:null,
    isLoading: true,
    typeData: [],
    goodData: [],
    // 被点击的左侧的菜单
    currentIndex: 0,
    // 右侧内容的滚动条距离顶部的距离
    scrollTop: 0
  },
  onLoad() {
    this.setData({
      imagePrefix:urlPrefix
    })
    let that = this;
    wx.request({
      //发请求种类
      url: urlPrefix+'commodityService/categories',
      method:'GET',
      data: {},
      success:function(res){
        console.log(res.data)
        that.setData({
          typeData:res.data.data,
          //默认显示第一个类别的产品
          currentIndex:0,
          isLoading: false
        });
        //请求第一个分类的数据
        wx.request({
          url: urlPrefix+'commodityService/commodities/query',
          method:'POST',
          data:{
            category:((res.data.data)[0]).id
          },
          success:function(res) {
            console.log(res.data)
            that.setData({
              goodData:res.data.data,
            });
          }
        })
      },
      fail:function(res){
        console.log(res.data)
      }
    })
  },
  // 左侧菜单的点击事件
  handleItemTap(e) {
    /* 
    1 获取被点击的标题身上的索引
    2 给data中的currentIndex赋值就可以了
    3 根据不同的索引来渲染右侧的商品内容
     */
    const { current } = e.currentTarget.dataset;
    const { index } = e.currentTarget.dataset;
    var that = this;
    wx.request({
      url: urlPrefix+'commodityService/commodities/query',
      method:'POST',
      data:{
        category:current
      },
      success:function(rs) {
        that.setData({
          goodData:rs.data.data,
          scrollTop: 0,
          currentIndex: index
        })
      }
    })
  },
  tapGood(event) {
    wx.navigateTo({
      url: '../detail/detail?gid=' + event.currentTarget.dataset.gid
    })
  },
})