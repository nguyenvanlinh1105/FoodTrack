var express = require("express");
var app = express();
var server = require("http").createServer(app);

// var io = require("socket.io").listen(server);
var server = require("http").createServer(app);
var { Server } = require("socket.io");
var io = new Server(server);

var fs = require("fs");
const { Console } = require("console");
server.listen(process.env.PORT || 3000);

// app.get("/", function(req, res){
// 	res.sendFile(__dirname + "/index.html");	
// });

console.log("Server is running");

let arrayUser = []
let flag = true;
let idChat = 0;

let arrayChat = [];

//khi socket on, nó sẽ nhận vào 2 tham số
//thứ nhất là tên sự kiện, mặc định là connection
//thứ 2 là cái hàm để nó thực hiện
//-> và thế là có dòng code như bên dưới
io.sockets.on('connection', function (socket) {
	console.log("Co nguoi connect server ne ba conn");
	// io.sockets.emit("serverguitinnhan", {noidung: "oke nha em"});
	socket.on("client-register-user", function (data) {

		//kiểm tra xem tên ngừi dùng on được đã tồn tại hay chưa
		if (arrayUser.indexOf(data) == -1) {
			arrayUser.push(data)
			flag = false
			console.log("Dang ky thanh cong: " + data);
			socket.un = data;
			io.sockets.emit("server-send-user", { danhsach: arrayUser });
		}
		else {
			flag = true
			console.log("User " + data + " da ton tai");
		}

		socket.emit("server-send-reply", { ketqua: flag });

		//emit tới tất cả mọi người 
		// io.sockets.emit("server-send-data", {noidung: data});

		//emit tới mấy người vừa gửi
		// socket.emit("serverguitinnhan", {noidung: data});
	})

	socket.on("client-send-chat", function (data) {
		console.log(socket.un + ": " + data);
		// io.sockets.emit("server-send-chat", {noiDungChat: socket.un + ": " + data});	
		arrayChat.push(
			{
				idChat: idChat,
				username: socket.un,
				noiDungChat: data,
			}
		)
		io.sockets.emit("server-send-chat", { chat: arrayChat });
		idChat++
		// io.sockets.emit("server-send-current-user", { username: socket.un })
		
	})

});
