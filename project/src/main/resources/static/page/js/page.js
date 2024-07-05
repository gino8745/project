const user = JSON.parse(sessionStorage.getItem('userdata'));
const newsData = JSON.parse(sessionStorage.getItem('newsData'));
const $ = (id) => document.getElementById(id);
const lable = async(data) => {
	if (user) {
		if (user.manager == 1) {
			const respones=await fetch("http://localhost:8082/user/follow/"+newsData.writer+"/"+user.userId);
            const a=await respones.json();
            
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

            if(!a){
				$('but2').innerHTML = ' <button type="button" id="follow" class="like  pure-button-primary">追隨</button> ';
			} else if (a){
				$('but2').innerHTML = ' <button type="button" id="follow" class="like  pure-button-primary">以追隨</button> ';
			}
            
            
            
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


const getnewsdata = async () => {
	
	$('title').textContent = newsData.title;
	$('writername').innerText = newsData.writer;
	$('image-preview').src = newsData.image_1;
	$('post1').textContent = newsData.post_1;
	$('post2').textContent = newsData.post_2;
	$('post3').textContent = newsData.post_3;

	if (newsData.image_2) {
		$('image-preview1').src = newsData.image_2;
		$('image-preview1').style.display = 'block';
	} else {
		$('image-preview1').style.display = 'none';
	}

	if (newsData.image_3) {
		$('image-preview2').src = newsData.image_3;
		$('image-preview2').style.display = 'block';
	} else {
		$('image-preview2').style.display = 'none';
	}

	page(newsData.categoryId);
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
const fetchAndRenderData = async (cdata) => {
	
	if (newsData == null) {
		window.location.href = 'http://localhost:8082/visitor/index.html';
	};
	formData = {
		categoryId: cdata,
		newsId: newsData.newsId
	}
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

	
	if (user != null) {
		const formData = {
			userId: user.userId,
			categoryId: newsData.categoryId,
			newsId: newsData.newsId
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

const follow=async()=>{
	const Url='http://localhost:8082/user/follow';
    
    const datafrom={
		userId:user.userId,
		categoryId:newsData.writer,
	}

	const respones=await fetch(Url,{
		method:'PUT',
		headers:{
			'Content-Type': 'application/json'
		},
		body:JSON.stringify(datafrom)
	});
	const data=await respones.json();
	sessionStorage.setItem("follow",data);
	if (data == true) {
		Swal.fire({
			title: "以追隨",
			icon: "success"
		}).then(async (result) => {
			location.reload();
		});

	} else {
		Swal.fire({
			title: "已取消追隨",
			icon: "success"
		}).then(async (result) => {
			location.reload();
		});

	}
	
}


document.addEventListener("DOMContentLoaded", () => {
	if (user != null) {
		likebut();
	} else {
		lable();
	}
	let data =exchange(newsData.categoryId);

	fetchAndRenderData(data);
	getnewsdata();

});



function exchange(data){
	switch (data) {
		case 1: return 'world_news';
			  
		case 2: return 'politics_news';
			
		case 3:return 'business_news';
			
		case 4: return 'health_news';
			
		case 5: return 'entertainment_news';
			
		case 6: return 'travel_news';
			
		case 7: return 'sport_news';
		
		case 8: return 'weather_news';
			
	}
};


