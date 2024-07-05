const user=JSON.parse(sessionStorage.getItem('userdata'));
const $ = (id) => document.getElementById(id);
const formatDate = (isoString) => {
	const date = new Date(isoString);
	return date.toLocaleString();
};
const popularnews = (data) => {
	const rows = data.map(data => `
	<div class="content">
		<a href="#" class="page"  data-id="${data.news_id}">
					<img src="${data.image_1}" class="content-img">
				</a>
				<div class="content-text">
					<a href="#" class="page"  data-id="${data.news_id}" id="container2-title">${data.title}</a>
				</div>
	  </div>
	  `).join('');
	$('box').innerHTML = rows;
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
             <div class="container2-title"><a href="#" class="page"  data-id="${data.news_id}" id="container2-title">${data.title}</div>
             <div class="container2-text"><a href="#" class="page"  data-id="${data.news_id}" id="container2-title">${data.post_1}</div>
             </a>
        </div>
                <button class="button-update" value="${data.news_id}">修改</button>
        <button class="button-delete" value="${data.news_id}">刪除</button>
   </div>
	  `).join('');
	$('container2').innerHTML = rows;
};

const fetchAndRenderview = async (name) => {

	const fullUrl = 'http://localhost:8082/news/view/' + name;
	const response = await fetch(fullUrl); // 等待 fetch 請求完成
	const { state, message, data } = await response.json(); // 等待回應本文內容
	popularnews(data);

};
//取得資料
const fetchAndRenderData = async () => {
	const fullUrl = 'http://localhost:8082/news/health';
	const response = await fetch(fullUrl); // 等待 fetch 請求完成
	const { state, message, data } = await response.json(); // 等待回應本文內容
	rendernews(data);

};
//修改
const handleUpdatenews = async (id) => {
	const Url = 'http://localhost:8082/news/health/' + id+"/"+user.gender;
	const response = await fetch(Url);
	const { state, message, data } = await response.json();
	
	console.log(data);
	console.log(user);
	if (user.userName != data.writer) {
		alert("權限不足");
		return;
	}
	sessionStorage.setItem('newsData', JSON.stringify(data));


	window.location.href = 'http://localhost:8082/manager/page/updepage.html';
};
const handleDeletenews = async (id) => {
	const Url = 'http://localhost:8082/news/health/' + id+"/"+user.gender;
	const response = await fetch(Url);
	const { state, message, data } = await response.json();

	if (user.userName != data.writer) {
		alert("權限不足");
		return;
	}

	Swal.fire({
		title: "確定要刪除嗎?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#3085d6",
		cancelButtonColor: "#d33",
		confirmButtonText: "確定"
	}).then(async (result) => {
		if (result.isConfirmed) {

			const fullUrl = 'http://localhost:8082/news/post/delete/4/' + id;
			const res = await fetch(fullUrl, { method: 'DELETE' });
			const { state2, message2, data2 } = await res.json();
			window.location.reload();

			Swal.fire({
				title: "已刪除",
				icon: "success"
			});
		}
	});



};

const handletonewspage = async (id) => {
	const Url = 'http://localhost:8082/news/health/' + id+"/"+user.gender;
	const response = await fetch(Url);
	const { state, message, data } = await response.json();
	sessionStorage.setItem('newsData', JSON.stringify(data));
	window.location.href = 'http://localhost:8082/page/page.html';
}
const pass = async () => {

	if (user == null || user.manager != 2 ) {
		window.location.href = "http://localhost:8082/user/login.html";
	}
}

document.addEventListener("DOMContentLoaded", async () => {
	pass();

	fetchAndRenderData();
	fetchAndRenderview('health');
	$('container').addEventListener("click", async (event) => {
		let id;
		if (event.target.closest('a.page')) {
			id = event.target.closest('a.page').getAttribute('data-id');
			await handletonewspage(id);
		}
	});




	$('container2').addEventListener("click", async (event) => {
		let id;
		if (event.target.classList.contains('button-update') || event.target.classList.contains('button-delete')) {
			id = event.target.getAttribute('value');
		} else if (event.target.closest('a.page')) {
			id = event.target.closest('a.page').getAttribute('data-id');
		}

		if (id) {
			if (event.target.classList.contains('button-update')) {
				await handleUpdatenews(id);
			} else if (event.target.classList.contains('button-delete')) {
				await handleDeletenews(id);
			} else if (event.target.closest('a.page')) {
				await handletonewspage(id);
			}
		}
	});

});
