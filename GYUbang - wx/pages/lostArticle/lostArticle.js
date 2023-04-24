// pages/lostArticle/lostArticle.js
Page({
    /**
     * 页面的初始数据
     */
    data: {
        sortRule: false, // 公告倒序排序
        show: false,
        // qlostArticle: {
        //     author: "",
        //     content: "",
        //     image: "",
        //     status: "",
        //     statusx: "",
        //     tel: "",
        //     time: ""
        // }
    },

    /**
     * 排序方法
     */
    mySort: function (e) {
        //property 根据什么排序
        var property = e.currentTarget.dataset.property;
        // var property = 'id';
        console.log(property);
        var that = this;
        var qLostArticle = that.data.lostArticle;
        this.setData({
            sortRule: !this.data.sortRule // 正序倒序
        })

        that.setData({
            lostArticle: qLostArticle.sort(that.compare(property, this.data.sortRule))
        })
    },
    compare: function (property, flag) {
        return function (a, b) {
            var value1 = a[property];
            var value2 = b[property];
            if (flag) {
                return value1 - value2;
            } else {
                return value2 - value1;
            }
        }
    },

    /**
     * 获取公告信息
     */
    getLostArticle() {
        wx.request({
            url: 'http://localhost/lostarticle/getLostArticle',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {},
            success: (res) => {
                var arr = res.data.data;
                arr = arr.reverse();
                // console.log(arr);
                this.setData({
                    lostArticle: arr
                })
            },
            fail: (err) => {
                console.log(err);
            }
        })
    },

    /**
     * 跳转发起失物申请
     */
    lost() {
        wx.navigateTo({
            url: '/pages/lostArticleA1/lostArticleA1',
        })
    },
    /**
     * 跳转发起失物详情
     */
    lostDetails(e) {
        let qqid = e.currentTarget.id;
        wx.navigateTo({
            url: '/pages/lostArticleA1Details/lostArticleA1Details?qqid=' + qqid,
        })
    },
    /**
     * 跳转发起招领申请
     */
    lost0() {
        wx.navigateTo({
            url: '/pages/lostArticleA2/lostArticleA2',
        })
    },
    /**
     * 跳转发起招领详情
     */
    lost0Details(e) {
        let qqid = e.currentTarget.id;
        wx.navigateTo({
            url: '/pages/lostArticleA2Details/lostArticleA2Details?qqid=' + qqid,
        })
    },

    /**
     * 刷新数据
     */
    renovate() {
        const that = this;
        setTimeout(() => {
            wx.showToast({
                title: "刷新完成",
                icon: "success",
            });
            setTimeout(() => {
                wx.hideToast();
            }, 500);
            setTimeout(() => {
                that.onLoad();
            }, 500);
        }, 0);
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
        this.getLostArticle();
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {
        this.getLostArticle();
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