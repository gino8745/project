
const user = JSON.parse(sessionStorage.getItem('userdata'));
const $ = (id) => document.getElementById(id);
const lable = (data) => {
	if (user) {
		if (user.manager == 1) {
			const lable = `
		            <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="../member/index.html">首頁</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../member/world.html">國際</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../member/Politics.html">政治</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../member/Entertainment.html">經濟</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../member/Health.html">醫療</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../member/Entertainment.html">娛樂</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../member/Travel.html">旅遊</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../member/Sports.html">體育</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../member/Weather.html">天氣</a>
                    </li>
                   <li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../member/like.html">收藏</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../member/record.html">紀錄</a></li>
		`;


			if (!data) {
				$('but1').innerHTML = ' <button type="button" id="like" class="like  pure-button-primary">收藏</button> ';
			} else if (data) {
				$('but1').innerHTML = ' <button type="button" id="like" class="like  pure-button-primary">以收藏</button> ';
			}
			$('login').innerHTML = '<button class="btn btn-primary me-md-2" id="logout" type="button">登出</button>';
			$('lable').innerHTML = lable;
		} else if (user.manager == 2) {
			const lable = `
	        <ul class="navbar-nav">
					<li class="nav-item "><a class="nav-link active"
						aria-current="page" href="../manager/index.html">首頁</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../manager/world.html">國際</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../manager/Politics.html">政治</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../manager/Business.html">經濟</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../manager/Health.html">醫療</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../manager/Entertainment.html">娛樂</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../manager/Travel.html">旅遊</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../manager/Sports.html">體育</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../manager/Weather.html">天氣</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../manager/page/page.html">新增</a></li>
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="../user/personalPost.html">文章</a></li>
		`;
			$('login').innerHTML = '	<button class="btn btn-primary me-md-2" id="logout" type="button">登出</button>';
			$('lable').innerHTML = lable;
		}
	} else {
		const lable = `
		            <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="../visitor/index.html">首頁</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../visitor/world.html">國際</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../visitor/Politics.html">政治</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../visitor/Entertainment.html">經濟</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../visitor/Health.html">醫療</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../visitor//Entertainment.html">娛樂</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../visitor//Travel.html">旅遊</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../visitor//Sports.html">體育</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="../visitor//Weather.html">天氣</a>
                    </li>
		`;
		$('login').innerHTML = `<a class="pure-button pure-button-primary" href="../user/login.html">登入</a>`;
		$('lable').innerHTML = lable;
	}


};

let cid='';
let newsId='';
let apage=false;
const getnewsdata = async () => {
	const respones = await fetch("http://localhost:8082/page/testpage");
    const {state, message, data}=await respones.json();
	
	$('title').textContent = data.title;
	$('writername').innerText = data.writer;
	$('image-preview').src = data.image_1;
	$('post1').textContent = data.post_1;
	$('post2').textContent = data.post_2;
	$('post3').textContent = data.post_3;
    cid=data.categoryId;
    newsId=data.newsId;
	if (data.image_2) {
		$('image-preview1').src = data.image_2;
		$('image-preview1').style.display = 'block';
	} else {
		$('image-preview1').style.display = 'none';
	}

	if (data.image_3) {
		$('image-preview2').src = data.image_3;
		$('image-preview2').style.display = 'block';
	} else {
		$('image-preview2').style.display = 'none';
	}
		
	let cdata = '';
	switch (cid) {
		case 1: cdata = 'world_news';
			break;
		case 2: cdata = 'politics_news';
			break;
		case 3: cdata = 'business_news';
			break;
		case 4: cdata = 'health_news';
			break;
		case 5: cdata = 'entertainment_news';
			break;
		case 6: cdata = 'travel_news';
			break;
		case 7: cdata = 'sport_news';
			break;
		case 8: cdata = 'weather_news';
			break;
	}
	fetchAndRenderData(cdata,newsId);
	page(data.categoryId);
	
	
};
const rendernews = (data) => {
	const rows = data.map(data => `
       <div class="content" id="content">
            <div class="content-img" id="page">
              <a href="#" class="page"  data-id="${data.news_id}"  >
                 <img  src="${data.image_1}" class="content-img-size">
              </a>
            </div>
            <div  class="content-text"><a  href="#" class="page"  data-id="${data.news_id}" id="page">${data.title}</a></div>
        </div>   
	  `).join('');

	$('contenter').innerHTML = rows;
};
const fetchAndRenderData = async (cdata,newsid) => {
	//const newsData = JSON.parse(sessionStorage.getItem('newsData'));
	/*
	if (apage==false) {
		window.location.href = 'http://localhost:8082/visitor/index.html';
	};*/
	formData = {
		categoryId: cdata,
		newsId: newsid
	}
	console.log(formData);
	const fullUrl = 'http://localhost:8082/news/random';
	const response = await fetch(fullUrl, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(formData)
	}); // 等待 fetch 請求完成
	const { state, message, data } = await response.json(); // 等待回應本文內容


	rendernews(data);
};
const like = async () => {

	const newsData = JSON.parse(sessionStorage.getItem('newsData'));

	const formData = {
		userId: user.userId,
		categoryId: newsData.categoryId,
		newsId: newsData.newsId
	};
	const response = await fetch('http://localhost:8082/user/like/' + user.gender, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(formData)
	});
	const { state, message, data } = await response.json();
	if (data == true) {
		Swal.fire({
			title: "文章以收藏",
			icon: "success"
		}).then(async (result) => {
			location.reload();
		});

	} else {
		Swal.fire({
			title: "文章已取消收藏",
			icon: "success"
		}).then(async (result) => {
			location.reload();
		});

	}
};
const likebut = async () => {

	const newsData = JSON.parse(sessionStorage.getItem('newsData'));
	if (user != null) {
		const formData = {
			userId: user.userId,
			categoryId: cid,
			newsId: newsid
		};
		const response = await fetch('http://localhost:8082/user/likebut', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(formData)
		});
		const { state, message, data } = await response.json();
		lable(data);
	}
};
const link = async (id, name) => {
	if (user != null) {
		const Url = 'http://localhost:8082/news/' + name + '/' + id + "/" + user.gender;
		const response = await fetch(Url);
		const { state, message, data } = await response.json();
		sessionStorage.setItem('newsData', JSON.stringify(data));
	} else {
		const Url = 'http://localhost:8082/news/' + name + '/' + id + "/男";
		const response = await fetch(Url);
		const { state, message, data } = await response.json();
		sessionStorage.setItem('newsData', JSON.stringify(data));
	}

	window.location.href = 'http://localhost:8082/page/page.html';
}
const page = (cId) => {
	$('contenter').addEventListener("click", async (event) => {
		let id;
		if (event.target.closest('a.page')) {
			id = event.target.closest('a.page').getAttribute('data-id');
			switch (cId) {
				case 1: await link(id, 'world');
					break;
				case 2: await link(id, 'politics');
					break;
				case 3: await link(id, 'business');
					break;
				case 4: await link(id, 'health');
					break;
				case 5: await link(id, 'entertainment');
					break;
				case 6: await link(id, 'travel');
					break;
				case 7: await link(id, 'sport');
					break;
				case 8: await link(id, 'weather');
					break;
			}
		}
	});
};
document.addEventListener("DOMContentLoaded", () => {
	getnewsdata();
	if (user != null) {
		likebut();
	} else {
		lable();
	}



});













