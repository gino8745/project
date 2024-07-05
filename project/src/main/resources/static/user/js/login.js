const $ = (id) => document.getElementById(id);

const handForSubmit = async (event) => {
	event.preventDefault();

	const captchaInput = $('captchaInput').value;
	const respones = await fetch('http://localhost:8082/user/validateCaptcha', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: captchaInput
	});
	const d = await respones.json();
	if (d) {

		const formdata = {
			userEmail: $('useremail').value,
			password: $('password').value
		}
		checkuser(formdata);
	}else{
		$('error-message').innerText = "驗證碼錯誤";
	}
   
}
const checkuser = async (formdata) => {
	const response = await fetch('http://localhost:8082/user/pass', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(formdata)
	})
	const { state, message, data } = await response.json();
	if (state) {
		sessionStorage.setItem("userdata", JSON.stringify(data));
		if (data.manager == 1) {
			window.location.href = "http://localhost:8082/member/index.html";
		} else if (data.manager == 2) {
			window.location.href = "http://localhost:8082/manager/index.html";
		}
		else {
			window.location.href = "http://localhost:8082/manager/index.html";
		}
	}
	else {
		$('error-message').innerText = "帳號密碼錯誤";
		return;
	}
};

function refreshCaptcha() {
	document.getElementById("captchaImage").src = "http://localhost:8082/user/captcha?" + new Date().getTime();
}





