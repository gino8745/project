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

const rendernews = async(data) => {
    const respones=await fetch("http://localhost:8082/user/follow/sport_news/"+user.userId);
    const a=await respones.json();

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
       </div>
	  `).join('');
	 if(!a){
		$('afollow').innerHTML='<button class="follow  pure-button-primary" id="follow">追隨</button>';
	 }else if(a){
		$('afollow').innerHTML='<button class="follow  pure-button-primary" id="follow">以追隨</button>';
	 }
	  
	  
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
	const fullUrl = 'http://localhost:8082/news/sport';
	const response = await fetch(fullUrl); // 等待 fetch 請求完成
	const { state, message, data } = await response.json(); // 等待回應本文內容
	rendernews(data);

};

const handletonewspage = async (id) => {
	const Url = 'http://localhost:8082/news/sport/' + id+"/"+user.gender;
	const response = await fetch(Url);
	const { state, message, data } = await response.json();
	sessionStorage.setItem('newsData', JSON.stringify(data));
	const formData={	
      categoryId:data.categoryId,     
      newsId:data.newsId,
      userId:user.userId,
      date:time(),
	}
	const recordresp = await fetch('http://localhost:8082/user/record', {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(formData)
	});	
	const a=await recordresp.json();
	
	
	window.location.href = 'http://localhost:8082/page/page.html';
}
const time=()=>{
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
	fetchAndRenderview('sport');
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

const follow =async()=>{
	const dataform={
		userId:user.userId,
		categoryId:'sport_news',
	};

	const Url='http://localhost:8082/user/follow';
	const respones=await fetch(Url,{
		method:'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body:JSON.stringify(dataform)
	});
	const a=await respones.json();
	if (a == false) {
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


	