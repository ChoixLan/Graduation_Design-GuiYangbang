// pages/lostArticleA1/lostArticleA1.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        content: "",
        tel: "",
        image: "",
        img_arr: [],
        formdata: '',
        fileList: [],
        
    },

    /**
     * 更新数据库失物招领信息
     */
    insertLost() {
        const that = this;
        if (this.data.content == "" || this.data.tel == "") {
            Dialog.alert({
                title: '错误',
                message: '请完善悬赏信息，每项必填',
            }).then(() => {
                // on close
            });
        } else {
            const openId = wx.getStorageSync('openid');
            wx.request({
                url: 'http://localhost/lostarticle/insertLostA1',
                method: 'POST',
                header: {
                    'token': wx.getStorageSync('token')
                },
                data: {
                    content: this.data.content,
                    tel: this.data.tel,
                    image: this.data.fileList[0].url,
                    author: wx.getStorageSync('userInfo').nickName
                },
                success: (res) => {
                    setTimeout(() => {
                        wx.showToast({
                          title: "申请发送成功",
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
     * 图片上传
     */
    afterRead(e) {
        const that = this;
        const {
            file
        } = e.detail;
        // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
        wx.uploadFile({
            url: 'http://localhost/test/upload', // 仅为示例，非真实的接口地址
            filePath: file.url,
            name: 'file',
            formData: {
                description: 'test'
            },
            success(res) {
                // 上传完成需要更新 fileList
                const {
                    fileList1 = []
                } = that.data.fileList;
                fileList1.push({
                    ...file,
                    url: res.data
                });
                that.setData({
                    fileList: fileList1
                });
            },
        });
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