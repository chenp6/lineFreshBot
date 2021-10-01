let PORT = process.env.PORT || 3000;


import express from 'express'; //載入express框架模組
import http from 'http'; //載入http框架模組
import path from 'path';
const __dirname = path.resolve();

let app = express(); //Creates an Express application.
let server = http.Server(app);

app.use('/', express.static(__dirname));

app.get('/', (req, res) => {
    res.sendFile("/index.html");
});


server.listen(PORT, () => {
    console.log("正在聽" + PORT);
});