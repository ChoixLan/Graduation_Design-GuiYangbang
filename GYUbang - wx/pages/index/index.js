// index.js
// 获取应用实例
const app = getApp()

Page({
    data: {
        topNum: 0, //默认为0
        cangotop: false, //回到顶部控件默认隐藏
        container: "", // 公告搜索中间容器
        show: false,
        swiperCurrent: "", // 指示点
        sortRule: false, // 公告倒序排序
        turnImg: [
            "/img/bg_01.jpg",
            "/img/bg_02.jpg",
            "/img/bg_03.jpg",
            "/img/bg_04.jpg",
        ],
        qAnnouncement: {
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
     * 跳转搜索页
     */
    gotoQuerySearch() {
        wx.navigateTo({
            url: '/pages/querySearch/querySearch',
        });
    },

    /**
     * 跳转宿舍管理条例
     */
    goto1() {
        wx.navigateTo({
            url: '/pages/regulations/regulations',
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

    // 公告详情弹出触发
    info(e) {
        let qqid = e.currentTarget.id;
        this.setData({
            qAnnouncement: this.data.Announcement[qqid],
            show: true
        })
    },
    // 公告详情关闭
    onClose() {
        this.setData({
            show: false
        });
    },

    /**
     * 排序方法
     */
    mySort: function (e) {
        //property 根据什么排序
        var property = e.currentTarget.dataset.property;
        var self = this;
        var arr = self.data.Announcement;
        this.setData({
            sortRule: !this.data.sortRule // 正序倒序
        })

        self.setData({
            Announcement: arr.sort(self.compare(property, this.data.sortRule))
        })
    },
    compare: function (property, bol) {
        return function (a, b) {
            var value1 = a[property];
            var value2 = b[property];
            if (bol) {
                return value1 - value2;
            } else {
                return value2 - value1;
            }
        }
    },

    //swiper滑动事件
    swiperChange: function (e) { //指示图标
        this.setData({
            swiperCurrent: e.detail.current
        })
    },

    /**
     * 获取公告信息
     */
    getAnnouncement() {
        wx.request({
            url: 'http://localhost/findAnnouncement/getAnnouncement',
            method: 'POST',
            header: {
                'token': wx.getStorageSync('token')
            },
            data: {},
            success: (res) => {
                var arr = res.data.data;
                arr = arr.reverse();
                this.setData({
                    Announcement: arr
                })
            },
            fail: (err) => {
                console.log(err);
            }
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
     * 生命周期函数--监听页面加载
     */
    onLoad() {
        this.getAnnouncement();
        if (!wx.getStorageSync('avatar')) {
            console.log("index.js: 缓存没有用户头像，用户未登录跳转login界面-->");
            wx.redirectTo({
                url: '/pages/login/login',
            })
        } else {
            console.log("index.js: 缓存有用户头像，用户已登录!!!");
        }
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {
        this.getTabBar().init();
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {},
})