// pages/querySearch/querySearch.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        show: false,
        qAnnouncement: {
            id: "",
            author: "",
            buildingNum: "",
            content: "",
            releaseTime: "",
            status: "",
            title: ""
        },
        qAnnouncement0: {
            id: "",
            author: "",
            buildingNum: "",
            content: "",
            releaseTime: "",
            status: "",
            title: ""
        }
    },

    /**
     * 公告搜索
     */
    onSearch() {
        // 空搜索
        if (this.data.container == "") {
            this.setData({
                qAnnouncement: ""
            })
        }
        // 非空搜索
        else {
            wx.request({
                url: 'http://localhost/findAnnouncement/querySearchMini',
                method: 'POST',
                header: {
                    'token': wx.getStorageSync('token')
                },
                data: {
                    queryString: this.data.container
                },
                success: (res) => {
                    console.log(res.data);
                    this.setData({
                        qAnnouncement: res.data.data
                    })
                },
                fail: (err) => {
                    console.log(err);
                }
            })
        }

    },

    // 公告详情弹出触发
    info(e) {
        let qqid = e.currentTarget.id;
        wx.request({
            url: 'http://localhost/findAnnouncement/getAnnouncementById',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {
                id: qqid
            },
            success: (res) => {
                console.log(res.data);
                this.setData({
                    qAnnouncement0: res.data.data,
                    show: true
                })
            },
            fail: (err) => {
                console.log(err);
            }
        })
        // this.setData({
        //     qAnnouncement: this.data.Announcement[qqid],
            
        // })
    },
    // 公告详情关闭
    onClose() {
        this.setData({
            show: false
        });
    },

    // 获取滑动位置
    onPageScroll: function (e) {
        // console.log("打印当前页面滚动的距离：", e.scrollTop)
        // 当页面滑动距离大于一屏的高度时显示回到顶部控件
        this.setData({
            cangotop: e.scrollTop > wx.getSystemInfoSync().windowHeight ? true : false
        });
    },
    //回到顶部，内部调用系统API
    goTopOn: function (e) {
        if (wx.pageScrollTo) {
            wx.pageScrollTo({
                scrollTop: 0
            })
        } else {
            wx.showModal({
                title: '提示',
                content: '当前微信版本过低，暂无法使用该功能，请升级后重试。'
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