// pages/login/login.js
import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';
Page({

    /**
     * 页面的初始数据
     */
    data: {},

    /**
     * 登录系统
     */
    login() {
        // 登录,获取code
        wx.login({
            success: res => {
                // 发送 res.code 到后台换取 openId, sessionKey, unionId
                wx.request({
                    url: 'http://localhost/mini/login', //仅为示例，并非真实的接口地址
                    data: {
                        code: res.code,
                    },
                    header: {
                        'content-type': 'application/json' // 默认值
                    },
                    success(res) {
                        const {
                            code,
                            data,
                            msg
                        } = res.data;
                        // 判断请求是否成功
                        if (code != 200) {
                            return wx.showToast({
                                title: 'msg',
                                icon: 'error',
                                duration: 2000
                            })
                        }
                        console.log('登录系统请求成功：', res.data);
                        // 缓存token、数据库用户信息、openid
                        wx.setStorageSync('token', res.data.data.token);
                        wx.setStorageSync('userInfo', res.data.data.userInfo.user);
                        wx.setStorageSync('openid', res.data.data.openid);
                    },
                    fail(err) {
                        console.log('请求失败: -->', err);
                    }
                })
            }
        })
    },

    /**
     * 获取微笑服务器用户头像昵称
     */
    getWXInfo() {
        if (wx.getStorageSync('userInfo').avatar) {
            console.log("getWXInfo(): 缓存userInfo有用户头像，跳转index-->");
            wx.setStorageSync('avatar', wx.getStorageSync('userInfo').avatar)
            Dialog.alert({
                title: '登录成功',
                message: "欢迎回来 " + wx.getStorageSync('userInfo').nickName,
            }).then(() => {
                // on close
                wx.switchTab({
                    url: "../index/index"
                })
            });

        } else {
            console.log("getWXInfo(): 缓存userInfo没有用户头像,获取微信服务器返回的用户信息更新数据库-->");
            Dialog.alert({
                title: '欢迎来到贵院帮',
                message: "微信用户未注册，贵院帮需要获取微信用户信息用于注册",
            }).then(() => {
                // on close
                wx.getUserProfile({
                    desc: '用于完善用户信息',
                    success: (res) => {
                        console.log(res.userInfo);
                        wx.setStorageSync('avatar', res.userInfo.avatarUrl);
                        let {
                            avatarUrl,
                            gender,
                            nickName
                        } = res.userInfo;
                        const openid = wx.getStorageSync('openid');
                        wx.request({
                            url: 'http://localhost/mini/update/info',
                            method: 'POST',
                            header: {
                                'token': wx.getStorageSync('token')
                            },
                            data: {
                                nickName: nickName,
                                avatar: avatarUrl,
                                sex: gender,
                                openId: openid
                            },
                            success: (res) => {
                                console.log("用微信服务器信息更新数据库信息", res.data);
                                this.getUser();
                                wx.switchTab({
                                    url: "../index/index"
                                })
                            },
                            fail: (err) => {
                                console.log(err);
                            }
                        })
                    },
                    fail(err) {
                        console.log(err);
                    }
                })
            });

        }
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
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {

    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        this.login();
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