// pages/lostArticleA1Details/lostArticleA1Details.js
Page({
    /**
     * 页面的初始数据
     */
    data: {
        qqid: "",
        content: '',

    },

    /**
     * 根据id获取失物信息
     */
    getOneLostArticle() {
        wx.request({
            url: 'http://localhost/lostarticle/getOneLostArticle',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {
                id: this.data.qqid
            },
            success: (res) => {
                this.setData({
                    lostArticle: res.data.data
                })
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },

    /**
     * 发表失物评论
     */
    addComment() {
        const that = this;
        wx.request({
            url: 'http://localhost/comment/addComment',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {
                lostid: this.data.qqid,
                content: this.data.content,
                image: wx.getStorageSync('userInfo').avatar,
                nickname: wx.getStorageSync('userInfo').nickName,
            },
            success: (res) => {
                setTimeout(() => {
                    wx.showToast({
                        title: "评论成功",
                        icon: "success",
                    });
                    setTimeout(() => {
                        wx.hideToast();
                    }, 1500)
                    setTimeout(() => {
                        that.onLoad();
                    }, 1500)
                }, 0);
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },

    /**
     * 获取评论
     */
    getComment() {
        wx.request({
            url: 'http://localhost/comment/getComment',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {
                lostid: this.data.qqid,
            },
            success: (res) => {
                this.setData({
                    Comment: res.data.data
                })
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },

    /**
     * 图片放大预览
     */
    previewImage: function (e) {
        const imgUrl = e.currentTarget.dataset.url;
        wx.previewImage({
            urls: [imgUrl], // 需要预览的图片http链接列表
            current: '', // 当前显示图片的http链接
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        if (this.data.qqid == "") {
            this.setData({
                qqid: options.qqid
            })
        }
        this.getOneLostArticle();
        this.getComment();
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