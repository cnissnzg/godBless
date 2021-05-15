const Axios = require("axios");
const HttpsProxyAgent = require("https-proxy-agent");

// 
const httpsAgent = new HttpsProxyAgent(`http://127.0.0.1:12306`);
const axios = Axios.create({
    proxy:false,
    httpsAgent
});

axios.interceptors.request.use(function (config) {
    // Do something before request is sent
    let token = localStorage.getItem('hojxtoken') === null ? '' : localStorage.getItem('hojxtoken');
    if (token) {
        config.headers.accessToken = token;    //将token放到请求头发送给服务器
        return config;
        //这里经常搭配token使用，将token值配置到tokenkey中，将tokenkey放在请求头中
        // config.headers['accessToken'] = Token;
    }
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});

export default axios;