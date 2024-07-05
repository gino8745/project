const user = JSON.parse(sessionStorage.getItem('userdata'));
const $ = (id) => document.getElementById(id);
let fetchEnabled = true;
//時間
const formatDate = (isoString) => {
    const date = new Date(isoString);
	return date.toLocaleString();
};
//沒有資料
const nonews = (data) => {
	const rows = `
     <div class="line" id="line">
	     
	  </div>`
	$('container2').innerHTML = rows;


}
//多個資料
const newsList = (data) => {

	const rows = data.map(data => `
	<div class="line" id="line">
        <div class="container2-time">${formatDate(data.create_time)}</div>
        <div class="container2-img"><a href="#" class="page" category_id="${data.category_id}" data-id="${data.news_id}" >
             <img src="${data.image_1}" class="img1">
            </a>
        </div>
        <div class="container2-title"><a href="#" class="page" category_id="${data.category_id}" data-id="${data.news_id}" id="container2-title">${data.title}</a></div>
        <button class="button-update" category_id="${data.category_id}" value="${data.news_id}">修改</button>
        <button class="button-delete" category_id="${data.category_id}" value="${data.news_id}">刪除</button>
   </div>
	  `).join('');
	$('container2').innerHTML = rows;
}

//單個資料
const news = (data) => {

	const rows = `
	<div class="line" id="line">
        <div class="container2-time">${formatDate(data.create_time)}</div>
        <div class="container2-img"><a href="#" class="page" category_id="${data.category_id}" data-id="${data.news_id}" >
             <img src="${data.image_1}" class="img1">
            </a>
        </div>
        <div class="container2-title"><a href="#" class="page" category_id="${data.category_id}" data-id="${data.news_id}" id="container2-title">${data.title}</a></div>
        <button class="button-update" category_id="${data.category_id}" value="${data.news_id}">修改</button>
        <button class="button-delete" category_id="${data.category_id}" value="${data.news_id}">刪除</button>
   </div>
	  `;
	$('container2').innerHTML = rows;

};

//尋找資料
const fetchAndRenderData = async () => {

	const fullUrl = 'http://localhost:8082/news/writer/' + user.userId;

	const response = await fetch(fullUrl);
	const { state, message, data } = await response.json();
	const chanage = sessionStorage.getItem('boolean');
	sessionStorage.setItem('postview', data);
	if (data.length <= 0 && chanage) {
		nonews();
	}
	else if (data.length == 1) {
		news(data[0]);
	}
	else {
		newsList(data);

	}

};
//更新
const handleUpdatenews = async (id, cid) => {
	const Url = 'http://localhost:8082/news/' + fransform(cid) + '/' + id + '/' + user.gender;
	const response = await fetch(Url);
	const { state, message, data } = await response.json();

	if (user.userName != data.writer) {
		alert("權限不足");
		return;
	}
	sessionStorage.setItem('newsData', JSON.stringify(data));


	window.location.href = 'http://localhost:8082/manager/page/updepage.html';
};
//刪除
const handleDeletenews = async (id, cid) => {
	Swal.fire({
		title: "確定要刪除嗎?",
		icon: "warning",
		showCancelButton: true,
		confirmButtonColor: "#3085d6",
		cancelButtonColor: "#d33",
		confirmButtonText: "確定"
	}).then(async (result) => {
		if (result.isConfirmed) {

			const fullUrl = 'http://localhost:8082/news/post/delete/1/' + id;
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
//換業
const handletonewspage = async (id, cid) => {

	const Url = 'http://localhost:8082/news/' + fransform(cid) + '/' + id + '/' + user.gender;
	const response = await fetch(Url);
	const { state, message, data } = await response.json();
	sessionStorage.setItem('newsData', JSON.stringify(data));
	window.location.href = 'http://localhost:8082/page/page.html';
}
//檢查有無密碼
const pass = async () => {

	if (user == null) {
		window.location.href = "http://localhost:8082/user/login.html";
	}
};
function fransform(cid) {
	let name = '';
	switch (cid) {
		case '1': name = 'world';
			break;
		case '2': name = 'politics';
			break;
		case '3': name = 'business';
			break;
		case '4': name = 'health';
			break;
		case '5': name = 'entertainment';
			break;
		case '6': name = 'travel';
			break;
		case '7': name = 'sport';
			break;
		case '8': name = 'weather';
			break;
	}
	return name;

};

document.addEventListener("DOMContentLoaded", async () => {

	pass();
	$('controller').addEventListener("click", () => {
		fetchAndRenderData();
		let boolen = true;
		sessionStorage.setItem('boolean', boolen);
	});
	$('postdata').addEventListener("click", () => {

		dataAnaiysis();
		let boolen = false;
		sessionStorage.setItem('boolean', boolen);
	});

	const chanage = sessionStorage.getItem('boolean');
	if (chanage) {
		fetchAndRenderData();
	}

	//檢查點擊
	$('container2').addEventListener("click", async (event) => {
		let id;
		let cid;
		const link = event.target.closest('a.page');
		if (event.target.classList.contains('button-update') || event.target.classList.contains('button-delete')) {
			id = event.target.getAttribute('value');
			cid = event.target.getAttribute('category_id');
		} else if (event.target.closest('a.page')) {
			id = link.getAttribute('data-id');
			cid = link.getAttribute('category_id');
		}

		if (id) {
			if (event.target.classList.contains('button-update')) {
				await handleUpdatenews(id, cid);
			} else if (event.target.classList.contains('button-delete')) {
				await handleDeletenews(id, cid);
			} else if (event.target.closest('a.page')) {
				await handletonewspage(id, cid);
			}
		}
	});

});
//男性:${data.viewMale} 女性:${data.viewfeMale}
//drawCharttable
const dataAnaiysis = async () => {
	const fullUrl = 'http://localhost:8082/news/postview/' + user.userId;
	const response = await fetch(fullUrl);
	const { state, message, data } = await response.json();
	
	const rows = `
      <div class="drawCharttable">
      	 <div class="data">
      	 <ul >
      	      <li>國際: 男性 ${data[0].viewMale}-${data[0].viewFemale}女性</li>
              <li>政治: 男性 ${data[1].viewMale}-${data[1].viewFemale} 女性</li>
              <li>經濟: 男性 ${data[2].viewMale}-${data[2].viewFemale} 女性</li>
              <li>醫療: 男性 ${data[3].viewMale}-${data[3].viewFemale} 女性</li>
              <li>娛樂: 男性 ${data[4].viewMale}-${data[4].viewFemale} 女性</li>
              <li>旅遊: 男性 ${data[5].viewMale}-${data[5].viewFemale} 女性</li>
              <li>體育: 男性 ${data[6].viewMale}-${data[6].viewFemale} 女性</li>
              <li>天氣: 男性 ${data[7].viewMale}-${data[7].viewFemale} 女性</li>
      	 </ul>
      	 </div>
	     <div id="piechart" class="piechart" style="width: 500px; height: 500px;"></div>
	  </div>`
	  
	  
	$('container2').innerHTML = rows;
	drawChart();
}
google.charts.load('current', { 'packages': ['corechart'] });
const drawChart = async () => {
	const fullUrl = 'http://localhost:8082/news/postview/' + user.userId;
	const response = await fetch(fullUrl);
	const { state, message, data } = await response.json();
	const viewArray = [0, 0, 0, 0, 0, 0, 0, 0];
	for (let i = 0; i < data.length; i++) {
		viewArray[data[i].category_id - 1] = data[i].totalView;

	};

	var postdata = google.visualization.arrayToDataTable([
		['Task', 'Hours per Day'],
		['國際', viewArray[0]],
		['政治', viewArray[1]],
		['經濟', viewArray[2]],
		['醫療', viewArray[3]],
		['娛樂', viewArray[4]],
		['旅遊', viewArray[5]],
		['體育', viewArray[6]],
		['天氣', viewArray[7]],
	]);

	var options = {
		title: '文章觀看比例'
	};

	var chart = new google.visualization.PieChart(document.getElementById('piechart'));
	chart.draw(postdata, options);
};