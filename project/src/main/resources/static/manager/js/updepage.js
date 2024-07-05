const $ = (id) => document.getElementById(id);

const handForSubmit = async (event) => {
	event.preventDefault();
	const formData = {
		categoryId: parseInt($('categoryId').value),
		title: $('title').value,
		writer: $('writer').value,
		post_1: $('post1').value,
		post_2: $('post2').value,
		post_3: $('post3').value,
	};

	const newsData = JSON.parse(sessionStorage.getItem('newsData'));

	// 處理圖片 1
	if ($('image1').files[0] != null) {
		const image1Base64 = await convertImageToBase64($('image1').files[0]);
		formData.image_1 = image1Base64;
	} else {
		formData.image_1 = newsData.image_1;
	}
	// 處理圖片 2
	if ($('image2').files[0] != null) {
		const image2Base64 = await convertImageToBase64($('image2').files[0]);
		formData.image_2 = image2Base64;
	} else {
		formData.image_2 = newsData.image_2;
	}

	// 處理圖片 3
	if ($('image3').files[0] != null) {
		const image3Base64 = await convertImageToBase64($('image3').files[0]);
		formData.image_3 = image3Base64;
	} else {
		formData.image_3 = newsData.image_3;
	}


	addUser(formData, newsData.newsId);



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

document.addEventListener("DOMContentLoaded", () => {
	getnewsData();
	setupImageUpload('image1', 'image-preview');
	setupImageUpload('image2', 'image-preview1');
	setupImageUpload('image3', 'image-preview2');
});

const getnewsData = async () => {
	try {
		const newsData = JSON.parse(sessionStorage.getItem('newsData'));
		loadFormOptions(newsData.categoryId);
		
		$('title').value = newsData.title;
		$('image-preview').src = newsData.image_1;
		$('writer').value = newsData.writer;
		$('post1').value = newsData.post_1;
		if(newsData.image_2){
		$('image-preview1').src = newsData.image_2;
		}
		$('post2').value = newsData.post_2;
		if(newsData.image_3){
		$('image-preview2').src = newsData.image_3;
		}
		$('post3').value = newsData.post_3;
	} catch (e) {
		console.error(e);
	}
};

const addUser = async (formData, id) => {
	const response = await fetch('http://localhost:8082/news/post/update/' + id, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(formData)
	});
	const { state, message, data } = await response.json();
	var name = null;
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

const clearForm = () => {
	$('categoryId').value = '';
	$('title').value = '';
	$('post1').value = '';
	$('post2').value = '';
	$('post3').value = '';
};
function setupImageUpload(imageUploadId, imagePreviewId) {
	document.getElementById(imageUploadId).addEventListener('change', function(event) {
		const file = event.target.files[0];
		if (file) {
			const reader = new FileReader();
			reader.onload = function(e) {
				const img = document.getElementById(imagePreviewId);
				img.src = e.target.result;
				img.style.display = 'block'; // Show the image
			}
			reader.readAsDataURL(file);
		}
	});
}


const loadFormOptions = async (id) => {
	const basedataOptions = await fetch('http://localhost:8082/news/basedata');
	const { state, message, data } = await basedataOptions.json();
	data.forEach(basedata => {
		const opt = document.createElement('option');
		opt.value = basedata.category_id;
		opt.textContent = basedata.category_name;
		$('categoryId').appendChild(opt);
	});
	$('categoryId').value = id;
};

const pass = async () => {

	if (user == null || user.manager != 2 ) {
		window.location.href = "http://localhost:8082/user/login.html";
	}
}

document.addEventListener('DOMContentLoaded', () => {
	pass();
})

