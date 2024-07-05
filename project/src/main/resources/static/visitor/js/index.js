const $ = (id) => document.getElementById(id);
const formatDate = (isoString) => {
	const date = new Date(isoString);
	return date.toLocaleString();
};

const breakinghead = (data) => {
	const news = `<div class="first_content">
                    <a href="#" class="page" category-id="${data.categoryId}" data-id="${data.newsId}"> <img src=${data.image_1} class="world-img-size"></a>
				</div>
				<div class="first_content2">
					<div class="first_content2_title"><a href="#" class="page" category-id="${data.categoryId}" data-id="${data.newsId}">${data.title}</a></div>
					<div class="first_content2_text"><a href="#" class="page" category-id="${data.categoryId}" data-id="${data.newsId}">${data.post_1}</a></div>
				</div>
				`;

	$('first').innerHTML = news;


}



const breakingbody = (data) => {

	const news = data.map(data =>
		`
	    <div class="first2_content">
			<div class="first2_img">
                    <a href="#" class="page" category-id="${data.categoryId}" data-id="${data.newsId}"> <img src=${data.image_1} class="world-img-size"></a>
				</div>
				<div class="first2_ctext">
					<div class="first2_title"><a href="#" class="page" category-id="${data.categoryId}" data-id="${data.newsId}">${data.title}</a></div>
					<div class="first2_t"><a href="#" class="page" category-id="${data.categoryId}" data-id="${data.newsId}">${data.post_1}</a></div>
			    </div>
		</div>
	
	`).join('');

	$('first2').innerHTML = news;


}



const news = (data, pageclass) => {
	const news = data.map(data =>
		`<div class="titleworld">     
	           <div class="titleworldimg">
					<a href="#" class="page" data-id="${data.news_id}"> <img src=${data.image_1} class="world-img-size"></a>
                </div>
                
                <div class="titleworldword">
					<div class="title-news"><a href="#" class="page" data-id="${data.news_id}">${data.title}</a></div>
	            	<div class="text-news"><a href="#" class="page" data-id="${data.news_id}">${data.post_1}</a></div>

	            </div>
        </div>    
         
	 `).join('');
	$(pageclass).innerHTML = news;
};




const breakingnews = async () => {
	const fullUrl = 'http://localhost:8082/news/breakingnews';
	const response = await fetch(fullUrl); // 等待 fetch 請求完成
	const { state, message, data } = await response.json(); // 等待回應本文內容

	breakinghead(data[0]);
	data.shift();

	breakingbody(data);
};

const bnsbutton = async () => {
	let cid = event.target.closest('a.page').getAttribute('category-id');
	let id = event.target.closest('a.page').getAttribute('data-id');

	switch (cid) {
		case '1': await link(id, 'world');
			break;
		case '2': await link(id, 'politics');
			break;
		case '3': await link(id, 'business');
			break;
		case '4': await link(id, 'health');
			break;
		case '5': await link(id, 'entertainment');
			break;
		case '6': await link(id, 'travel');
			break;
		case '7': await link(id, 'sport');
			break;
		case '8': await link(id, 'weather');
			break;
	}
};



const Data = async (name, pageclass) => {
	const fullUrl = 'http://localhost:8082/news/' + name;
	const response = await fetch(fullUrl); // 等待 fetch 請求完成
	const { state, message, data } = await response.json(); // 等待回應本文內容
	let array = new Array(5);
	for (let i = 0; i < 5; i++) {
		if (data[i] != null) {
			array.push(data[i]);
		}
	}
	news(array, pageclass);
};


const link = async (id, name) => {
	const user = JSON.parse(sessionStorage.getItem('userdata'));
	const Url = 'http://localhost:8082/news/' + name + '/' + id + '/男' ;
	const response = await fetch(Url);
	const { state, message, data } = await response.json();
	sessionStorage.setItem('newsData', JSON.stringify(data));
	window.location.href = 'http://localhost:8082/page/page.html';
}

const page = (name) => {

	$(name).addEventListener("click", async (event) => {
		let id;
		if (event.target.closest('a.page')) {
			id = event.target.closest('a.page').getAttribute('data-id');
			switch (name) {
				case 'worldnews': await link(id, 'world');
					break;
				case 'politicsnews': await link(id, 'politics');
					break;
				case 'businessnews': await link(id, 'business');
					break;
				case 'healthnews': await link(id, 'health');
					break;
				case 'entertainmentnews': await link(id, 'entertainment');
					break;
				case 'travelnews': await link(id, 'travel');
					break;
				case 'sportnews': await link(id, 'sport');
					break;
				case 'weathernews': await link(id, 'weather');
					break;
			}
		}
	});
};

document.addEventListener("DOMContentLoaded", async () => {

	breakingnews();
	Data('world', 'worldnews');
	Data('politics', 'politicsnews');
	Data('business', 'businessnews');
	Data('health', 'healthnews');
	Data('entertainment', 'entertainmentnews');
	Data('travel', 'travelnews');
	Data('sport', 'sportsnews');
	Data('weather', 'weathernews');

	page('worldnews');
	page('politicsnews');
	page('businessnews');
	page('healthnews');
	page('entertainmentnews');
	page('travelnews');
	page('sportnews');
	page('weathernews');

});

