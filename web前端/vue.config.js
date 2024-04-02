module.exports = {
    devServer: {
        open: true,
        port: 3000,
        overlay: false,
        proxy: {
            "/api": {
                target: "http://localhost:9527",
                changeOrigin: true,
                pathRewrite: {
                    "^/api": '',
                },
            },
        },
    }
}