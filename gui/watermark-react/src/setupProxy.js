const { createProxyMiddleware } = require('http-proxy-middleware');
 
module.exports = function(app) {
  app.use(createProxyMiddleware('/api/v1/watermark', { target: 'http://39.105.21.114:12306' ,ws : true}))
}