//index.js
//获取应用实例
const app = getApp()

// 引用百度地图微信小程序JSAPI模块 换成你的文件路径 
var bmap = require('../../utils/bmap-wx.min.js'); 

var wxMarkerData = [];  //定位成功回调对象 

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'), 
    ak: "去找自己的ak", //填写申请到的ak 
    city: '',
    weatherData:[]
  },
  

  onLoad: function () {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }


    //查询地址和天气
    this.bindViewTap();
  },//end onload
  
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },


//获取城市
bindViewTap: function () {
    var self = this
    wx.getLocation({
      type: 'wgs84',
      altitude: true,
      success: function (res) {
        console.log('city')
        var log = res.longitude
        var lat = res.latitude
        self.loadCity(log, lat)
      },
      fail: function (res) { },
      complete: function (res) { },
    })
  },
  loadCity: function (log, lat) {
    var self = this
    wx.request({
      url: 'https://api.map.baidu.com/geocoder/v2/?ak=' + self.data.ak+'&location=' + lat + ',' + log + '&output=json',
      data: {},
      header: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        console.log('loadCity'+res)
        var city = res.data.result.addressComponent.city;
        console.log(city)
        self.setData({ city: city });

        self.loadWeather();
      },
      fail: function () {
        console.log('fail')
      },
      complete: function () { }
    })
  },

//获取天气
  loadWeather:function(){
    var that = this;
    // 新建bmap对象   
    var BMap = new bmap.BMapWX({
      ak: that.data.ak
    });
    var fail = function (data) {
      console.log(data);
    };
    var success = function (data) {
      console.log('loadWeather'+data);
      var weatherData = data.currentWeather[0];
      var futureWeather = data.originalData.results[0].weather_data;
      console.log(futureWeather);
      weatherData = '城市：' + weatherData.currentCity + '\n' + 'PM2.5：' + weatherData.pm25 + '\n' + '日期：' + weatherData.date + '\n' + '温度：' + weatherData.temperature + '\n' + '天气：' + weatherData.weatherDesc + '\n' + '风力：' + weatherData.wind + '\n';
      that.setData({
        weatherData: weatherData,
        futureWeather: futureWeather
      });
    }
    // 发起weather请求   
    BMap.weather({
      fail: fail,
      success: success
    });      
  }
  
})
