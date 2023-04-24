// pages/insertUpkeep/insertUpkeep.js
import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        cause: "",
        address: "",
    },

    /**
     * 更新数据库用户信息
     */
    insertUpkeep() {
        const that = this;
        if (this.data.cause == "") {
            Dialog.alert({
                title: '错误',
                message: '请输入维修原因',
            }).then(() => {
                // on close
            });
        } else if (this.data.address == "") {
            Dialog.alert({
                title: '错误',
                message: '请输入维修地址',
            }).then(() => {
                // on close
            });
        } else {
            const openId = wx.getStorageSync('openid');
            wx.request({
                url: 'http://localhost/upkeep/insertUpkeep',
                method: 'POST',
                header: {
                    'token': wx.getStorageSync('token')
                },
                data: {
                    cause: this.data.cause,
                    address: this.data.address,
                    author: wx.getStorageSync('userInfo').nickName,
                    openId: openId,
                },
                success: (res) => {
                    console.log("新的维修申请写入数据库成功-->", res.data);

                    setTimeout(() => {
                        wx.showToast({
                          title: "维修申请发送成功",
                          icon: "success",
                        });
                        setTimeout(() => {
                          wx.hideToast();
                        }, 1500)
                        setTimeout(() => {
                            wx.navigateBack({});
                          }, 1500)
                      }, 0);
                    // 更新上一页数据
                    const pages = getCurrentPages();
                    const perpage = pages[pages.length - 1];
                    perpage.onLoad();
                },
                fail: (err) => {
                    console.log(err);
                }
            })
        }
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