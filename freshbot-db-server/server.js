import http from 'http';
import express from 'express'; //載入express框架模組  //需要先npm install express --save

let app = express(); //Creates an Express application.
let server = http.createServer(app);

import {
    Server
} from 'socket.io';
let io = new Server(server); //透過pass已建好的server，初始化socket.io物件

const chatRecord = new Map(); //userId: 'userId'=> msg:對話紀錄

const store = new Map();
// userId:'店家userId'=>
// {
//  storeName:'LOVELAND 專情島',
//  photoURI:'/static/img/LOVELAND.jpg', 
//  star:4, 
//  address:'台東縣台東市新生路105號',
//  phoneNumber:'089335105',
//  workTime:'周一到周日 12:00 ~ 21:30',
//  hotspot:'https://spot.line.me/detail/738502408874761666',
//  sms1922:'0000+0000+0000+001'
//}

//===========店家資訊====================
let loveland = { storeName: 'LOVELAND 專情島', photoURI: '/static/img/LOVELAND.jpg', star: 5, address: '台東縣台東市新生路105號', phoneNumber: '089335105', workTime: '周一到周日 12:00 ~ 21:30', hotspot: 'https://spot.line.me/detail/738502408874761666', sms1922: '0000+0000+0000+001' };
let eat2 = { storeName: '吃吃看海苔飯捲', photoURI: '/static/img/eat2.jpg', star: 4, address: '台東市新生里傳廣路497號', phoneNumber: '089225458', workTime: '周一到周六 10:00 ~ 20:00', hotspot: 'https://spot.line.me/detail/715333150238053390', sms1922: '0000+0000+0000+002' };


//===========店家管理帳號者===============
store.set("店家userId", loveland);
store.set('wewewqeqwe', eat2);

//=========美食清單==================
const foodStoreList = [loveland, eat2]

//const chatRecord = new Map(); //chatRecord => {store: 'userId',customer:'userId',msg:對話紀錄}


//當連線成功上該網頁的host，每開一個該網頁就會呼叫一次
io.on('connection', (socket) => {
    console.log('linebot connected'); //連上線後cmd console 顯示 'linebot connected'


    //當該網頁關閉，socket關閉，中斷連線
    socket.on('disconnect', () => {
        console.log('linebot disconnected');
    });

    socket.on('request food store', (userId, replyToken) => {
        console.log(userId + ' :  request food store')
        socket.emit("receive food store", replyToken, JSON.stringify(foodStoreList));
    })


    // //所有socket都會觸發chat message
    // socket.on('chat message', (msg) => {
    //     io.emit('chat message', msg);
    // });

    socket.on('record message', (userId, username, text) => {
        if (!chatRecord.has(userId)) {
            const msg = username + " say: " + text;
            chatRecord.set(userId, msg);
        } else {
            const msg = username + " say: " + text;
            let tmp = chatRecord.get(userId);
            tmp += "\n" + msg;
            chatRecord.set(userId, tmp);
        }
        console.log("show record ===================\n" + chatRecord.get(userId));
    });


    socket.on("request message", (replyToken, userId) => {
        socket.emit("receive message", replyToken, chatRecord.get(userId));
    })

    //顯示userId
    socket.on('hi', (userId) => {
        console.log('hi,' + userId);
    });

});


//make the http server listen on port 4000.
server.listen(4000, () => {
    console.log('listening on *:4000');
});