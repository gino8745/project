const user=JSON.parse(sessionStorage.getItem('userdata'));
const $ = (id) => document.getElementById(id);
const formatDate = (isoString) => {
	const date = new Date(isoString);
	return date.toLocaleString();
};
const rendernews = (data) => {
	const rows = data.map(data => `
	<div class="line" id="line">
        <div class="container2-time">${formatDate(data.create_time)}</div>
        <div class="container2-img"><a href="#" class="page"  data-id="${data.news_id}" >
             <img src="${data.image_1}" class="img1">
            </a>
        </div>
        <div class="container2-context">
             <div class="container2-title"><a href="#" class="page" category-id="${data.category_id}"    data-id="${data.news_id}" id="container2-title">${data.title}</div>
             <div class="container2-text"><a href="#" class="page" category-id="${data.category_id}"    data-id="${data.news_id}" id="container2-title">${data.post_1}</div>
             </a>
        </div>
   </div>
	  `).join('');
	$('container2').innerHTML = rows;
};
//拿取資料
const fetchAndRenderData = async () => {
	const user = JSON.parse(sessionStorage.getItem('userdata'));
	const fullUrl = 'http://localhost:8082/user/like/'+user.userId;
	const response = await fetch(fullUrl); // 等待 fetch 請求完成
	const { state, message, data } = await response.json(); // 等待回應本文內容
	rendernews(data);
   
};
//換頁
const handletonewspage = async (cid,id) => {
	let tablename=null;
	switch (cid) {
				case '1': tablename ='world';
					break;
				case '2': tablename ='politics';
					break;
				case '3': tablename ='business';
					break;
				case '4': tablename = 'health';
					break;
				case '5': tablename = 'entertainment';
					break;
				case '6': tablename = 'travel';
					break;
				case '7': tablename = 'sport';
					break;
				case '8': tablename = 'weather';
					    break;
			}
			
	const Url = 'http://localhost:8082/news/'+tablename+'/' + id+"/"+user.gender;
	const response = await fetch(Url);
	const { state, message, data } = await response.json();
	sessionStorage.setItem('newsData', JSON.stringify(data));
	const formData = {
		categoryId: data.category_id,
		newsId: data.newsId,
		userId: user.userId,
		date: time(),
	}
	const recordresp = await fetch('http://localhost:8082/user/record', {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(formData)
	});
	const a = await recordresp.json();
	window.location.href = 'http://localhost:8082/page/page.html';
}
function time(){
    const date=new Date();	
    const month =(date.getMonth()+1).toString().padStart(2,'0');
	const day=date.getDate().toString().padStart(2,'0');;
	
	return `@${month}/${day},`
}
const pass = async () => {

	if (user == null || user.manager != 1) {
		window.location.href = "http://localhost:8082/user/login.html";
	}
}
document.addEventListener("DOMContentLoaded", async () => {
	pass();
	fetchAndRenderData();
	$('container2').addEventListener("click", async (event) => {
		const link = event.target.closest('a.page');
		let cid = link.getAttribute('category-id');
		let id = link.getAttribute('data-id');
		if (event.target.closest('a.page')) {
			await handletonewspage(cid,id);
		}
	});

});




