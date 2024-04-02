var urlPrefix = getApp().globalData.ipAddress;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    tabs: [
      {
        id: 0,
        value: "共享租赁",
        isActive: true,
      },
      {
        id: 1,
        value: "先租后买",
        isActive: false,
      },
      {
        id: 2,
        value: "以租代售",
        isActive: false,
      },
    ],
    collect:[],
    viewCollect:[],
    imagePrefix:null
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow(){
    this.setData({
      imagePrefix:urlPrefix
    })
    var that = this
    const userinfo = wx.getStorageSync('userinfo')
    if(typeof(userinfo.userId) == 'undefined'){
      console.log(11111)
        wx.showToast({
          title: '请登录',
        })
        return;
    }
    console.log(userinfo.userId)
    //发起请求
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
        //初始化共享租赁
        var initShareRent = [];
        for(let i=0;i<res.data.data.length;i++){
          if(res.data.data[i].commodity.sharingRent==1){
              initShareRent.push(res.data.data[i]);
          }
        }
        if(initShareRent.length==0){
          wx.showToast({
            title: '该分类收藏为空，快去收藏吧！',
          })
        }
        that.setData({
          collect:res.data.data,
          viewCollect:initShareRent
        })
      }
    })
  },
// 取消收藏
cancelLike(e){
  //弹框提示
  const id = e.currentTarget.dataset.key
  console.log(id)
  var that = this
  wx.showModal({
    title:'确定取消收藏吗',
    cancelColor: 'cancelColor',
    success:function (res) {
      if(res.confirm){
        //点击确认 发请求
        wx.request({
          url: urlPrefix+'userService/userLike/delete',
          method:"POST",
          header: {
            'content-type': 'application/x-www-form-urlencoded' // 默认值
          },
          data:{
              id:Number(id)
          },
          success:function (res) {
            if(res.data.code==200){
              wx.showToast({
                title: '取消收藏成功',
                duration:1000
              })
              //重载页面  
                console.log('重载')
                //我服了  这里刷新都没用
                  //直接请求了 不对本地数据做处理了 害
                  const userinfo = wx.getStorageSync('userinfo')
                  console.log(userinfo.userId)
                  //发起请求
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
                      //初始化共享租赁
                      var initShareRent = [];
                      for(let i=0;i<res.data.data.length;i++){
                        if(res.data.data[i].commodity.sharingRent==1){
                            initShareRent.push(res.data.data[i]);
                        }
                      }
                      if(initShareRent.length==0){
                        wx.showToast({
                          title: '该分类收藏为空，快去收藏吧！',
                        })
                      }
                      that.setData({
                        collect:res.data.data,
                        viewCollect:initShareRent
                      })
                    }
                  })
          
            }
          }
        })
      }
    }
  })
},
// 跳商品详情
goDetails(e){
  const id = e.currentTarget.dataset.key;
  wx.navigateTo({
    url: '../detail/detail?gid='+id,
  })
},
// 标题点击事件 从子组件传递过来
handleTabsItemChange(e){
  const userinfo = wx.getStorageSync('userinfo')
  if(typeof(userinfo.userId) == 'undefined'){
    console.log(11111)
      wx.showToast({
        title: '请登录',
      })
      return;
  }
    // 1 获取被点击的标题索引
    const {index}=e.detail;
    var that = this
    //共享租赁
    if(index == 0){
          //初始化共享租赁
          var initShareRent = [];
          for(let i=0;i<this.data.collect.length;i++){
            if(this.data.collect[i].commodity.sharingRent==1){
                initShareRent.push(this.data.collect[i]);
            }
          }
          if(initShareRent.length==0){
            wx.showToast({
              title: '该分类收藏为空，快去收藏吧！',
            })
          }
          console.log(initShareRent)
          that.setData({
            viewCollect:initShareRent
          })
    }else if(index == 1){  //先租后买
      //初始化先租后买
      var initShareRent = [];
      for(let i=0;i<this.data.collect.length;i++){
        if(this.data.collect[i].commodity.rentBeforeBuy==1){
            initShareRent.push(this.data.collect[i]);
        }
      }
      if(initShareRent.length==0){
        wx.showToast({
          title: '该分类收藏为空，快去收藏吧！',
        })
      }
      console.log(initShareRent)
      that.setData({
        viewCollect:initShareRent
      })

    }else if(index == 2){  //以租代售
         //初始化以租代售
      var initShareRent = [];
      for(let i=0;i<this.data.collect.length;i++){
        if(this.data.collect[i].commodity.rentForSale==1){
            initShareRent.push(this.data.collect[i]);
        }
      }
      if(initShareRent.length==0){
        wx.showToast({
          title: '该分类收藏为空，快去收藏吧！',
        })
      }
      console.log(initShareRent)
      that.setData({
        viewCollect:initShareRent
      })
    }
    // 2 修改源数组
    let {tabs}=this.data;
    tabs.forEach((v,i)=>i===index?v.isActive=true:v.isActive=false);
    // 3 赋值到data中
    this.setData({
      tabs:tabs
    })
  },
});
