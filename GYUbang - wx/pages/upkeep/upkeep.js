// pages/upkeep/upkeep.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        show: false,
        qUpkeep: {
            id: "",
            cause: "",
            author: "",
            applicationTime: "",
            address: "",
            statusIf: "",
            status: "",
            statusFeedback: "",
            statusTime: ""
        }
    },

    /**
     * 获取我的维修申请信息
     */
    getUpkeep() {
        wx.request({
            url: 'http://localhost/upkeep/getUpkeepInfo',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {
                openId: wx.getStorageSync('openid')
            },
            success: (res) => {
                var arr = res.data.data;
                arr = arr.reverse();
                this.setData({
                    Upkeep: arr
                })
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },

    // 公告详情弹出触发
    info(e) {
        let qqid = e.currentTarget.id;
        this.setData({
            qUpkeep: this.data.Upkeep[qqid],
            show: true
        })
    },
    // 公告详情关闭
    onClose() {
        this.setData({
            show: false
        });
    },

    gotoInsertUpkeep() {
        wx.navigateTo({
            url: "../insertUpkeep/insertUpkeep"
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        this.getUpkeep();
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
        this.onLoad();
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