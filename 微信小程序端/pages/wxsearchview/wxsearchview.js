// 提示集合
var __tipKeys = [];
// 搜索回调函数 
var __searchFunction = null;
// 返回函数 
var __goBackFunction = null;
// 应用变量
var __that = null;
var urlPrefix = getApp().globalData.ipAddress;
// 初始化函数
function init(that, hotKeys, tipKeys, searchFunction, goBackFunction) {
  __that = that;
  __tipKeys = tipKeys;
  __searchFunction = searchFunction;
  __goBackFunction = goBackFunction;
  var temData = {};
  var barHeight = 43;
  var view = {
    barHeight: barHeight
}
  temData.hotKeys = hotKeys;
// 获取系统参数 主要用来适配 其实每个页面都可以这样去做 能适配各种机型 当然我其他页面没做适配 你换机型可能视图会变动 建议就用iPhone6/7/8这个别去动了
  wx.getSystemInfo({
    success: function (res) {
      var wHeight = res.windowHeight;
      view.seachHeight = wHeight - barHeight;
      temData.view = view;
      __that.setData({
        wxSearchData: temData
      });
    }
  });
  getHisKeys(__that);
}

// 搜索框输入时候操作
function wxSearchInput(e) {
  var inputValue = e.detail.value;
  // 页面数据
  var temData = __that.data.wxSearchData;
  // 寻找提示值 
  var tipKeys = [];
  //发起请求 这交互其实不大合理 我不想改了 
  wx.request({
    url: urlPrefix+'commodityService/commodities/query',
    method:"POST",
    data:{
      name:e.detail.value
    },
    success:function(res){
      console.log(res.data.data)
      if (inputValue && inputValue.length > 0) {
        for (var i = 0; i < res.data.data.length; i++) {
            //展示搜索结果
            tipKeys.push((res.data.data)[i]);
            console.log(tipKeys)
        }
      }
    }
  })
  // 更新数据
  temData.value = inputValue;
  temData.tipKeys = tipKeys;
  // 更新视图
  __that.setData({
    wxSearchData: temData
  });
  console.log(__that.wxSearchData)
}

// 清空输入
function wxSearchClear() {
  // 页面数据
  var temData = __that.data.wxSearchData;
  // 更新数据
  temData.value = "";
  temData.tipKeys = [];
  // 更新视图
  __that.setData({
    wxSearchData: temData
  });
}

// 点击提示或者关键字、历史记录时的操作
function wxSearchKeyTap(e) {
    // 和v1.0不一样 这里不跳导航，还是跳详情吧
    console.log(e.currentTarget.dataset.key)
    //加入到搜索历史
    wxSearchAddHisKey(e.currentTarget.dataset.key)
    wx.navigateTo({
      url: '../detail/detail?gid='+e.currentTarget.dataset.key.id,
    })
}

function wxSearchByHis(e) {
  console.log(e)
  wx.navigateTo({
    url: '../detail/detail?gid='+e.currentTarget.dataset.key.id,
  })
}

// 点击搜索或者按回车
function wxSearchConfirm(e) {
  var key = e.target.dataset.key;
  if(key=='back'){
    __goBackFunction();
  }else{
    console.log(__that.data.wxSearchData)
    search(__that.data.wxSearchData.value);
  }
}

function search(inputValue) {
  if (inputValue && inputValue.length > 0) {
    // 添加历史记录
    // wxSearchAddHisKey(inputValue);
    // 更新
    var temData = __that.data.wxSearchData;
    temData.value = inputValue;
    __that.setData({
      wxSearchData: temData
    });
    // 回调搜索
    __searchFunction(inputValue);
  }
}

// 读取缓存
function getHisKeys() {
  var value = [];
  try {
    value = wx.getStorageSync('wxSearchHisKeys')
    if (value) {
      var temData = __that.data.wxSearchData;
      temData.his = value;
      __that.setData({
        wxSearchData: temData
      });
    }
  } catch (e) {
  }
}

// 添加缓存
function wxSearchAddHisKey(inputValue) {
  if (!inputValue || inputValue.length == 0) {
    return;
  }
  var value = wx.getStorageSync('wxSearchHisKeys');
  if (value) {
    if (value.indexOf(inputValue) < 0) {
      value.unshift(inputValue);
    }
    wx.setStorage({
      key: "wxSearchHisKeys",
      data: value,
      success: function () {
        getHisKeys(__that);
      }
    })
  } else {
    value = [];
    value.push(inputValue);
    wx.setStorage({
      key: "wxSearchHisKeys",
      data: value,
      success: function () {
        getHisKeys(__that);
      }
    })
  }
}

// 删除缓存
function wxSearchDeleteAll() {
  wx.removeStorage({
    key: 'wxSearchHisKeys',
    success: function (res) {
      var value = [];
      var temData = __that.data.wxSearchData;
      temData.his = value;
      __that.setData({
        wxSearchData: temData
      });
    }
  })
}

// 导出接口
module.exports = {
  init: init, //初始化函数
  wxSearchInput: wxSearchInput,// 输入变化时的操作
  wxSearchKeyTap: wxSearchKeyTap, // 点击提示或者关键字、历史记录时的操作
  wxSearchDeleteAll: wxSearchDeleteAll, // 删除所有的历史记录
  wxSearchConfirm: wxSearchConfirm, // 搜索函数
  wxSearchClear: wxSearchClear,  // 清空函数
  wxSearchByHis: wxSearchByHis  //使用历史记录搜索
}