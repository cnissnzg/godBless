const { createProxyMiddleware } = require('http-proxy-middleware');
 
module.exports = function(app) {
  app.use(createProxyMiddleware('/watermark', { target: 'http://localhost:12306' ,ws : true}))
}