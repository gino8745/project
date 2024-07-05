const user=JSON.parse(sessionStorage.getItem('userdata'));
const $ = (id) => document.getElementById(id);
const handForSubmit = async (event) => {
	event.preventDefault();
	const user=JSON.parse(sessionStorage.getItem('userdata'));
	
	const formData = {
		categoryId: parseInt($('categoryId').value),
		title: $('title').value,
		writer: $('writer').value,
		writerId:user.userId,
		post_1: $('post1').value,
		post_2: $('post2').value,
		post_3: $('post3').value,
	};

	// 轉換圖片 1
	const image1Base64 = await convertImageToBase64($('image1').files[0]);
	formData.image_1 = image1Base64;

	// 轉換圖片 2
	if($('image2')!==null && $('image2').files.length>0){
	const image2Base64 = await convertImageToBase64($('image2').files[0]);
	formData.image_2 = image2Base64;
    }
	// 轉換圖片 3
	if($('image3')!==null && $('image3').files.length>0){
	const image3Base64 = await convertImageToBase64($('image3').files[0]);
	formData.image_3 = image3Base64;
	}
	addUser(formData);

}

const convertImageToBase64 = (file) => {
	return new Promise((resolve, reject) => {
		const reader = new FileReader();
		reader.onload = () => {
			resolve(reader.result);
		};
		reader.onerror = reject;
		reader.readAsDataURL(file);
	});
};


const addUser = async (formData) => {
	const response = await fetch('http://localhost:8082/news/post', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(formData)
	});
	const { state, message, data } = await response.json();
	switch (formData.categoryId) {
		case 1: name = "world.html";
			break;
		case 2: name = "Politics.html";
			break;
		case 3: name = "Business.html";
			break;
		case 4: name = "Health.html";
			break;
		case 5: name = "Entertainment.html";
			break;
		case 6: name = "Travel.html";
			break;
		case 7: name = "Sports.html";
			break;
		case 8: name = "Weather.html";
			break;
	}
	window.location.href = 'http://localhost:8082/manager/' + name;

};


const loadFormOptions = async () => {
	const basedataOptions = await fetch('http://localhost:8082/news/basedata');
	var { state, message, data } = await basedataOptions.json();
	// 將 data 逐筆放到下單選項(option)中 (動態建立下拉選單選項)
	data.forEach(basedata => {
		const opt = document.createElement('option');
		opt.value = basedata.category_id;
		opt.textContent = basedata.category_name;
		$('categoryId').appendChild(opt);
	});
};


const pass = async () => {

	if (user == null || user.manager != 2 ) {
		window.location.href = "http://localhost:8082/user/login.html";
	}
}



document.addEventListener("DOMContentLoaded", async () => {
	pass();

	$('writer').value = user.userName;
	loadFormOptions();

});


