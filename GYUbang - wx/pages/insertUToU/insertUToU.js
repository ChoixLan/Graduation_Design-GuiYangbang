// pages/insertUpkeep/insertUpkeep.js
import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';
Page({

    /**
     * 页面的初始数据
     */
    data: {
        addressQ: "",
        password: "",
        addressS: "",
        money: "",
    },

    /**
     * 插入新的快递悬赏
     */
    insertUToU() {
        const that = this;
        if (this.data.addressQ == "" || this.data.password == "" || this.data.addressS == "" || this.data.money == "") {
            Dialog.alert({
                title: '错误',
                message: '请完善悬赏信息，每项必填',
            }).then(() => {
                // on close
            });
        } else {
            const openId = wx.getStorageSync('openid');
            wx.request({
                url: 'http://localhost/uToU/insertUToU',
                method: 'POST',
                header: {
                    'token': wx.getStorageSync('token')
                },
                data: {
                    addressQ: this.data.addressQ,
                    password: this.data.password,
                    addressS: this.data.addressS,
                    money: this.data.money,
                    authorA: wx.getStorageSync('userInfo').nickName,
                    openId: wx.getStorageSync('openid')
                },
                success: (res) => {
                    setTimeout(() => {
                        wx.showToast({
                          title: "悬赏申请发送成功",
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