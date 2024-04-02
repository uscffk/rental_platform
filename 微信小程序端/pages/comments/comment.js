var urlPrefix = getApp().globalData.ipAddress;
Page({
  /**
   * 页面的初始数据
   */
  data: {
      commentList:{},
      nftId:null,
      needComment:null,
      orderId:null
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this
    var nftId = options.nftId;
    var needComment = options.needComment;
    var orderId = options.orderId;
    //看是从租赁页面跳过来的还是从订单那里跳过来的
    if(needComment != 'undefined'){
      this.setData({
        needComment:parseInt(needComment)
    })
    }
    if(orderId != 'undefined'){
      this.data.orderId = orderId;
    }
    if(nftId != 'undefined'){
      this.data.nftId = nftId;
    }
    //发请求
    wx.request({
      url: urlPrefix+'commodityService/comments/queryComment',
      method:'POST',
      data:{
          nftId:nftId
      },
      success:function(res){
          if(res.data.data.length==0){
            //消息
            wx.showToast({
              title: '商品还没有评论!',
              duration:2000
            })
          }
          that.setData({
              commentList:res.data.data
          })
      }
    })
  },
  //肯定评论
  addAgree(e){
    //需要needComment=1才有效
    console.log(e);
    var _this = this;
    if(this.data.needComment==1){
      wx.showModal({
        cancelColor: 'cancelColor',
        title:'支持该评论?',
        success:function(res){
          if(res.confirm){
            wx.request({
              url: urlPrefix+'commodityService/comments/addAgree',
              method:"POST",
              data:{
                commentId:parseInt(e.currentTarget.dataset.key)
              },
              header: {
                'content-type': 'application/x-www-form-urlencoded' // 默认值
              },
              success:function(res) {
                if(parseInt(res.data.data)==1){
                    //更新
                    wx.showToast({
                      title: '支持成功',
                      duration:3000
                    });
                    //遍历commentList 找到 id为e.currentTarget.dataset.key的更新 setData
                    for(let i = 0;i<_this.data.commentList.length;i++){ 
                      if(_this.data.commentList[i].id==e.currentTarget.dataset.key){
                        _this.data.commentList[i].agree= _this.data.commentList[i].agree+1;
                        console.log(_this.data.commentList[i].agree)
                        break;
                      }
                    }
                    _this.setData({
                      commentList:_this.data.commentList
                    })
                }
              }
            })
          }
        }
      }) 
    }
  },
  //否定评论
  addDeny(e){
        //需要needComment=1才有效
        console.log(e);
        var _this = this;
        if(this.data.needComment==1){
          wx.showModal({
            cancelColor: 'cancelColor',
            title:'反对该评论?',
            success:function(res){
              if(res.confirm){
                wx.request({
                  url: urlPrefix+'commodityService/comments/addDeny',
                  method:"POST",
                  data:{
                    commentId:parseInt(e.currentTarget.dataset.key)
                  },
                  header: {
                    'content-type': 'application/x-www-form-urlencoded' // 默认值
                  },
                  success:function(res) {
                    if(parseInt(res.data.data)==1){
                        //更新
                        wx.showToast({
                          title: '反对成功',
                          duration:3000
                        });
                        //遍历commentList 找到 id为e.currentTarget.dataset.key的更新 setData
                        for(let i = 0;i<_this.data.commentList.length;i++){ 
                          if(_this.data.commentList[i].id==e.currentTarget.dataset.key){
                            _this.data.commentList[i].deny= _this.data.commentList[i].deny+1;
                            break;
                          }
                        }
                        _this.setData({
                          commentList:_this.data.commentList
                        })
                    }
                  }
                })
              }
            }
          }) 
        }
  },
  //发布评论
  submitComment(data){
    var that = this;
    console.log(data.detail.value)
    //读取本地存储获取id
    const userinfo = wx.getStorageSync('userinfo');
    wx.request({
      url: urlPrefix+'commodityService/comments/addComment',
      method:"POST",
      data:{
        content:data.detail.value.comment,
        nftId:parseInt(that.data.nftId),
        orderId:parseInt(that.data.orderId)
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值 不加这个报400
      },
      success:function(res){
          if(res.data.data==1){
              wx.showToast({
                title: '评论成功',
                success:function name(params) {
                  setTimeout(function (params) {
                    wx.redirectTo({
                      url: '../comments/comment?nftId='+that.data.nftId,
                     })
                  },2000)
               }
              })
          }
      }
    })
  }
})