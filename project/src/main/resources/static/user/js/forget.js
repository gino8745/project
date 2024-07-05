const $ = (id) => document.getElementById(id);

const handForSubmit = async (event) => {
	event.preventDefault();
	const email = $('useremail').value;
	checkuser(email);
}
const checkuser = async (email) => {
	time();
	const url = 'http://localhost:8082/user/forget/' + email;
	const response = await fetch(url);
	const { state, message, data } =await  response.json();
	if (!data) {
	    $('error-message').innerText = "沒有此信箱";
		return;
	};
	console.log(data);
	Swal.fire("已送出至信箱").then((result)=>{	
		 window.location.href='http://localhost:8082/user/login.html';		
	});
};

let sec = 10;
function time() {

	if (sec > 0) {
		$('setemail').textContent = `${sec}`;
		$('setemail').disabled = true;
		sec--;
		setTimeout(time, 1000);
	} else {
		sec = 10;
		$('error-message').innerText = " ";
		$('setemail').textContent = '傳送';
		$('setemail').disabled = false;
	}
};



