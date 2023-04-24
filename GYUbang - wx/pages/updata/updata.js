// pages/updata/updata.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        nickName: "",
        sex: "",
        college: "",
        buildingNum: "",
    },

    /**
     * 更新数据库用户信息
     */
    updata() {
        const openId = wx.getStorageSync('openid');
        wx.request({
            url: 'http://localhost/mini/update/info',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {
                nickName: this.data.nickName ? this.data.nickName : null,
                sex: this.data.sex != null ? this.data.sex : null,
                college: this.data.college ? this.data.college : null,
                buildingNum: this.data.buildingNum ? this.data.buildingNum : null,
                openId: openId,
            },
            success: (res) => {
                console.log("用户更新信息写入数据库成功-->", res.data);
                wx.navigateBack({})

                const pages = getCurrentPages()
                const perpage = pages[pages.length - 1]
                perpage.onLoad()
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {

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