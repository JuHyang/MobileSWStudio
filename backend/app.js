var express = require ("express")
var bodyParser = require('body-parser')
var puppeteer = require ("puppeteer")
var mysql = require('mysql')

var userId = "20151250"
var password = ""


var conn = mysql.createConnection({
  host      : 'localhost',
  user      : 'root',
  password  : 'dlwngid1!',
  database  : 'mobileSW'
})

conn.connect()

var app = express()

app.use(express.static('public'))
app.use(bodyParser.urlencoded({ extended: true }))

var url_lms = "https://www.kau.ac.kr/page/login.jsp?ppage=&target_page=act_Portal_Check.jsp@chk1-1"
