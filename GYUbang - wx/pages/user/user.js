// pages/test/test.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        avatar: '',
        username: '',
    },

    userInfo() {
        wx.navigateTo({
            url: '../userInfo/userInfo',
        })
    },

    upkeep() {
        wx.navigateTo({
            url: '../upkeep/upkeep',
        })
    },

    MyUToU() {
        wx.navigateTo({
            url: '../MyUToU/MyUToU',
        })
    },

    lostArticle() {
        wx.navigateTo({
            url: '../lostArticle/lostArticle',
        })
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
        })
    },

    /**
	 * 退出登录
	 */
	logout() {
		wx.showModal({
			title: '提示',
			content: '您确定要退出登录吗',
			success: (res) => {
                // 确认
				if (res.confirm) { 
                    // 清空缓存
                    wx.clearStorageSync();

                    // 延时退出提示
                    setTimeout(() => {
                        wx.showToast({
                          title: "退出成功",
                          icon: "success",
                        });
                        setTimeout(() => {
                          wx.hideToast();
                        }, 1000);
                        setTimeout(() => {
                            wx.redirectTo({
                                url: '/pages/login/login', 
                            });
                          }, 1000);
                      }, 0);
				} else { 
					console.log('用户点击取消退出')
				}
			}
		})
	},

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad() {
        // 更新用户信息缓存
        this.getUser();
        this.setData({
            avatar: wx.getStorageSync('avatar'),
            username: wx.getStorageSync('userInfo').nickName,
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
    onShow: function () {
        this.getTabBar().init();
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