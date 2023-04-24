// pages/userInfo/userInfo.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {}
    },

    /**
     * 根据openid获取数据库用户信息
     */
    getUser() {
        const openid = wx.getStorageSync('openid');
        wx.request({
            url: 'http://localhost/mini/getUserInfo',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {
                openId: openid
            },
            success: (res) => {
                console.log("获取数据库用户信息：", res);
                // 更新userInfo缓存
                wx.setStorageSync('userInfo', res.data.data);
                this.onLoad();
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },


    /**
     * 跳转用户信息修改页面
     */
    updata() {
        wx.navigateTo({
            url: '../updata/updata'
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        this.setData({
            userInfo: wx.getStorageSync('userInfo'),
        })
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {
        // 更新用户信息缓存
        this.getUser();
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {
       
    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    }
})