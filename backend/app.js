var express = require ("express")
var bodyParser = require('body-parser')
var puppeteer = require ("puppeteer")
var cheerio = require ("cheerio")
var mysql = require('mysql')

var fs = require("fs")

var userId = "2015125061"
var password = ""


var conn = mysql.createConnection({
  host      : 'localhost',
  user      : 'root',
  password  : 'dlwngid1!',
  database  : 'mobileSW'
})

// conn.connect()

var app = express()

app.use(express.static('public'))
app.use(bodyParser.urlencoded({ extended: true }))

var url = "https://www.kau.ac.kr/page/login.jsp?ppage=&target_page=act_Portal_Check.jsp@chk1-1"

var url_class = "https://portal.kau.ac.kr/sugang/LectDeptSchTop.jsp?year=2018&hakgi=20&hakgwa_name=%C7%D0%BA%CE&hakgwa_code=A0000&gwamok_name=&selhaknyun=%&selyoil=%&jojik_code=A0000&nowPage=1"

function delay(time) {
   return new Promise(function(resolve) {
       setTimeout(resolve, time)
   });
}
// body > form > table:nth-child(2) > tbody > tr > td > center > table:nth-child(2) > tbody > tr:nth-child(2) > td > center > table.table1 > tbody > tr:nth-child(2)

app.get("/get_class/:year/:hakgi/:password", function(req, res){
  console.log("@" + req.method + " " + req.url);
  var str_arr = new Array ()
  password = req.params.password;
  (async () => {
    const browser = await puppeteer.launch({headless : false,args: ['--no-sandbox', '--disable-setuid-sandbox']});
    browser.newPage({ context: 'another-context' })
    const page = await browser.newPage();
    // page.on('dialog', async dialog => {
    //   await dialog.dismiss();
    // });
    await page.goto(url); // lms 로그인창으로 이동
    // await page.goto('http://127.0.0.1:3000/lms_before_arr.html') // 테스트용
    await page.type("[name=p_id]", userId) // id찾아서 넣기
    await page.type("[name=p_pwd]", password) // 비밀번호 찾아서 넣기
    await page.click("body > div.aside > div.articel > table:nth-child(2) > tbody > tr:nth-child(3) > td > form > table > tbody > tr:nth-child(3) > td > table > tbody > tr > td:nth-child(2) > table > tbody > tr > td:nth-child(2) > a > img") // 로그인 버튼 클릭
    await delay (2000)

    var list_class = Array()
    var list_class_temp = Array()
    var class_temp = ""

    var k = 0
    var o = 0

    var temptepm = ""

    for (var p = 1; p < 45 ; p++) {
      var url_class = "https://portal.kau.ac.kr/sugang/LectDeptSchTop.jsp?year=" + String(req.params.year) + "&hakgi=" + String(req.params.hakgi) + "&hakgwa_name=%C7%D0%BA%CE&hakgwa_code=A0000&gwamok_name=&selhaknyun=%&selyoil=%&jojik_code=A0000&nowPage=" + String(p)
      await page.goto(url_class)
      // await delay (1000)

      var html = await page.content()
      var $ = cheerio.load(html);


      for (var i = 2; i < 17; i++){
        $('body > form > table:nth-child(2) > tbody > tr > td > center > table:nth-child(2) > tbody > tr:nth-child(2) > td > center > table.table1 > tbody > tr:nth-child(' + String(i) + ')').each(function(){
            var str_temp = $(this).text()
            var list_temp = str_temp.split("\n")
            for (var j = 0; j < list_temp.length; j++) {
              list_class_temp[o] = list_temp[j].replace(/^\s*/, "")
              o ++
            }
            var result = list_class_temp[5] + "//" + list_class_temp[11] + "//" + list_class_temp[12] + "//" + list_class_temp[14] + "//" + list_class_temp[15]
            list_class[k] = result
            temptepm += result + "\n"
            k ++
            o = 0
            list_class_temp = Array()
            class_temp = ""
        })
      }
    }
    console.log(list_class)
    fs.writeFile('file01_async.txt', temptepm, 'utf-8', function(e){
      if(e){
          // 3. 파일생성 중 오류가 발생하면 오류출력
          console.log(e);
      }else{
          // 4. 파일생성 중 오류가 없으면 완료 문자열 출력
          console.log('01 WRITE DONE!');
    }
  });

     res.send(list_class);
  })();
})


app.listen(8001, function (){
  console.log ('Connected 8001 port!!!');
});
