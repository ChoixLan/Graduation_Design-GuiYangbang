import Vue from "vue";
import VueRouter from "vue-router";
import Login from "@/views/Login.vue";
import Home from "@/views/Home.vue";
import store from "@/store";
import ajax from "@/utils/ajax";
import Test from "@/views/test.vue";
import userInfo from "@/views/system/user/userInfo.vue";
import Announcement from "@/views/system/announcement/Announcement.vue";
import Upkeep from "@/views/system/upkeep/Upkeep.vue";
import UToU from "@/views/system/uToU/uToU.vue";
import LostArticle from "@/views/system/LostArticle/LostArticle.vue";


Vue.use(VueRouter);

const routes = [
    {
        path: "/login",
        component: Login
    },
    {
        path: "/uerInfo",
        component: userInfo
    },
    {
        path: "/announcement",
        component: Announcement
    },
    {
        path: "/upkeep",
        component: Upkeep
    },
    {
        path: "/utou",
        component: UToU
    },
    {
        path: "/lostarticle",
        name: "LostArticle",
        component: LostArticle
    },
    {
        path: "/",
        component: Home,
        children: [
            {
                path: "/home/login",
                name: "Login",
                component: Login
            },
            {
                path: "/home/userInfo",
                name: "UserInfo",
                component: userInfo
            },
            {
                path: "/home/announcement",
                name: "Announcement",
                component: Announcement
            },
            {
                path: "/home/upkeep",
                name: "Upkeep",
                component: Upkeep
            },
            {
                path: "/home/utou",
                name: "UToU",
                component: UToU
            },
            {
                path: "/home/lostarticle",
                name: "LostArticle",
                component: LostArticle
            }
        ]
    },

];

const router = new VueRouter({
    routes
});

router.beforeEach((to, from, next) => {
    const token = sessionStorage.getItem("token");
    if (!token) {
        //用户未登录
        if (to.path === "/login") {
            next();
        } else {
            next("/login?redirect=${to.fullPath}");
        }
    } else {
        //用户已登录
        if (to.path === "/login") {
            next("/");
        } else {
            next();
        }

    }
})

export default router;
