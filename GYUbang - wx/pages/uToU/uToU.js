// pages/upkeep/upkeep.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        nickName: "",
        show: false,
        qUToU: {
            id: "",
            authorA: "",
            addressQ: "",
            password: "",
            addressS: "",
            money: "",
            statusAx: "",
            statusA: "",
            statusB: "",
            authorB: "",
            statusC: "",
            statusFeedback: ""
        }
    },

    /**
     * 获取我的维修申请信息
     */
    getUToU() {
        wx.request({
            url: 'http://localhost/uToU/getUToU',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {},
            success: (res) => {
                var arr = res.data.data;
                arr = arr.reverse();
                this.setData({
                    UToU: arr
                })
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },

    // 接取悬赏
    Receiving(e) {
        const that = this;
        let qqid = e.currentTarget.dataset.id;
        wx.showModal({
            title: '提示',
            content: '您确定要接取这个悬赏吗？',
            success: (res) => {
                // 确认
                if (res.confirm) {

                    wx.request({
                        url: 'http://localhost/uToU/ReceivingUToU',
                        method: 'POST',
                        header: {
                            'token': wx.getStorageSync('token')
                        },
                        data: {
                            id: qqid,
                            authorB: wx.getStorageSync('userInfo').nickName
                        },
                        success: (res) => {
                            setTimeout(() => {
                                wx.showToast({
                                    title: "悬赏接取成功",
                                    icon: "success",
                                });
                                setTimeout(() => {
                                    wx.hideToast();
                                }, 1500);
                                setTimeout(() => {
                                    that.getUToU();
                                }, 1500);
                            }, 0);
                        },
                        fail: (err) => {
                            console.log(err);
                        }
                    })
                } else {
                    console.log('用户点击取消退出')
                }
            }
        })





    },

    /**
     * 跳转我的悬赏
     */
    MyUToU() {
        wx.navigateTo({
            url: "../MyUToU/MyUToU"
        })
    },

    /**
     * 跳转发布悬赏
     */
    insertMyUToU() {
        wx.navigateTo({
            url: "../insertUToU/insertUToU"
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        this.getUToU();
        this.setData({
            nickName : wx.getStorageSync('userInfo').nickName
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