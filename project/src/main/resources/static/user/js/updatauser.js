const $=(id)=>document.getElementById(id);

const handForSubmit = async (event) => {
	event.preventDefault();
	const password = $('password').value;
    const confirmPassword = $('check').value;

    if (password !== confirmPassword) {
        $('error-message').innerText = '密碼不一致';
        return;
    }
   checkuser(password);
}
const checkuser=async(password)=>{
	const response = await fetch('http://localhost:8082/user/updata', {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(password)
	})
	const {state, message, data } = await response.json();
	window.location.href='http://localhost:8082/user/login.html';
};





