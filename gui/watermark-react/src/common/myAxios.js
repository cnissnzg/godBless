const Axios = require("axios");
const HttpsProxyAgent = require("https-proxy-agent");

// 
const httpsAgent = new HttpsProxyAgent(`http://127.0.0.1:12306`);
const axios = Axios.create({
    proxy:false,
    httpsAgent
});

export default axios;