Component({
    data: {
        selected: 0,
        list: [{
                pagePath: "/pages/index/index",
                text: "公告",
                icon: "home-o",

            },
            {
                pagePath: "/pages/test/test",
                text: "服务",
                icon: "chat",

            },
            {
                pagePath: "/pages/user/user",
                text: "我的",
                icon: "https://b.yzcdn.cn/vant/icon-demo-1126.png",

            },
        ]
    },
    methods: {
        onChange(e) {
            // console.log(e, 'e')
            this.setData({
                active: e.detail
            });
            wx.switchTab({
                url: this.data.list[e.detail].pagePath
            });
        },
        init() {
            const page = getCurrentPages().pop();
            this.setData({
                active: this.data.list.findIndex(item => item.pagePath === `/${page.route}`)
            });
        }
    }
})