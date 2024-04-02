var WxSearch = require('../wxsearchview/wxsearchview');
Page({
  data: {},
  // 搜索栏
  onLoad: function () {
    var that = this;
    WxSearch.init(
      that,  // 本页面一个引用
      // ['共享单车', '共享汽车', '共享相机', '共享雨伞'], // 热点搜索推荐，[]表示不使用
      [],
      that.mySearchFunction, // 提供一个搜索回调函数
      that.myGobackFunction //提供一个返回回调函数
    );
  },
  // 转发函数,固定部分
  wxSearchInput: WxSearch.wxSearchInput,  // 输入变化时的操作
  wxSearchKeyTap: WxSearch.wxSearchKeyTap,  // 点击提示或者关键字、历史记录时的操作
  wxSearchDeleteAll: WxSearch.wxSearchDeleteAll, // 删除所有的历史记录
  wxSearchConfirm: WxSearch.wxSearchConfirm,  // 搜索函数
  wxSearchClear: WxSearch.wxSearchClear,  // 清空函数
  wxSearchByHis: WxSearch.wxSearchByHis,
  // 搜索回调函数  
  mySearchFunction: function (value) {
    // 跳转
    wx.redirectTo({
      url: '../index/index?searchValue='+value.data.data
    })
  },
  // 返回回调函数
  myGobackFunction: function () {
    wx.redirectTo({
      url: '../index/index?searchValue=返回'  
    })
  }
})
