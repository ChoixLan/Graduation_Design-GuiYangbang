import Vue from "vue";
import axios from "axios";

const ajax = axios.create({
    baseURL: "http://localhost:80",
});

/**
 * 请求拦截器
 */
ajax.interceptors.request.use((config) => {
    const token = sessionStorage.getItem('token');
    if (token) {
        //给请求头加上token
        config.headers['token'] = token;
    }
    return config;
}, (err) => {
    console.log('请求异常', err);
});

Vue.prototype.$ajax = ajax;

//暴露ajax
export default ajax;
