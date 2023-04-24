// pages/upkeep/upkeep.js
import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        show: false,
    },

    // Tab点击事件 
    onClick(e) {
        if (e.detail.name == 0) {
            this.getMyUToU();
        } else {
            this.getMyQUToU();
        }
    },

    /**
     * 获取我发布的悬赏信息
     */
    getMyUToU() {
        wx.request({
            url: 'http://localhost/uToU/getMyUToU',
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
                    UToU: arr
                })
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },

    /**
     * 获取我接取的悬赏信息
     */
    getMyQUToU() {
        wx.request({
            url: 'http://localhost/uToU/getMyQUToU',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {
                authorB: wx.getStorageSync('userInfo').nickName
            },
            success: (res) => {
                var arr = res.data.data;
                arr = arr.reverse();
                this.setData({
                    qUToU: arr
                })
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },

    /**
     * 完成悬赏
     */
    OkUToT(e) {
        const that = this;
        let qqid = e.currentTarget.dataset.id;
        Dialog.confirm({
                title: '警告',
                message: '确认快递已送达？',
            }).then(() => {
                // on confirm
                console.log("ok");
                wx.request({
                    url: 'http://localhost/uToU/OkMyUToU',
                    method: 'POST',
                    header: {
                        'token': wx.getStorageSync('token')
                    },
                    data: {
                        id: qqid
                    },
                    success: (res) => {
                        setTimeout(() => {
                            wx.showToast({
                                title: "悬赏完成",
                                icon: "success",
                            });
                            setTimeout(() => {
                                wx.hideToast();
                            }, 1500);
                            setTimeout(() => {
                                that.getMyUToU();
                            }, 1500);
                        }, 0);
                    },
                    fail: (err) => {
                        console.log(err);
                    }
                })
            })
            .catch(() => {
                // on cancel
            });
    },

    /**
     * 取消悬赏
     */
    qUToT(e) {
        const that = this;
        let qqid = e.currentTarget.dataset.id;
        Dialog.confirm({
                title: '警告',
                message: '确认取消悬赏？',
            }).then(() => {
                // on confirm
                wx.request({
                    url: 'http://localhost/uToU/qMyUToU',
                    method: 'POST',
                    header: {
                        'token': wx.getStorageSync('token')
                    },
                    data: {
                        id: qqid
                    },
                    success: (res) => {
                        setTimeout(() => {
                            wx.showToast({
                                title: "悬赏已取消",
                                icon: "success",
                            });
                            setTimeout(() => {
                                wx.hideToast();
                            }, 1500);
                            setTimeout(() => {
                                that.getMyUToU();
                            }, 1500);
                        }, 0);
                    },
                    fail: (err) => {
                        console.log(err);
                    }
                })
            })
            .catch(() => {
                // on cancel
            });
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        this.getMyUToU();
        this.getMyQUToU();
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