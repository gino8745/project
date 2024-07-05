const $ = (id) => document.getElementById(id);

const handForSubmit = async (event) => {
    event.preventDefault();
    const password = $('password').value;
    const confirmPassword = $('click').value;
  
    if (password !== confirmPassword) {
        $('password-error').innerText = '密碼不一致';
        return;
    }

    const passEmail = $('emailpasscode').value;
    const passcode = sessionStorage.getItem('passcode');
    if (passcode !== passEmail) {
        $('passcode-error').innerText = '驗證碼錯誤';
        return;
    }
    let gender='';
    if($('male').checked){
		gender=$('male').value;
	}else{
		gender=$('female').value;
	}
    const formData = {
        username: $('UserName').value,
        password: password,
        gender: gender,
        userEmail: $('email').value
    };

   // addUser(formData);
};

const addUser = async (formData) => {
    const response = await fetch('http://localhost:8082/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    });
    const { state, message, data } = await response.json();
 
    if (data === true) {
        window.location.href = 'http://localhost:8082/user/login.html';
    } else {
        $('error-message').innerText = '信箱已使用';
        return;
    }
};

const passcode = async () => {
    const formdata = $('email').value;
    time();
    if (formdata === '') {
        $('passcode-error').innerText = '請輸入信箱';
        return;
    } else if (!formdata.includes('@')) {
        $('passcode-error').innerText = '信箱錯誤';
        return;
    }
    const fullUrl = 'http://localhost:8082/user/email/' + formdata;
    const response = await fetch(fullUrl);
    const { state, message, data } = await response.json();
    console.log(data);
    sessionStorage.setItem('passcode', data);
        
};
let sec=60;
function time(){
	
	if(sec>0){
		$('passcode').textContent=`${sec}`;
		$('passcode').disabled=true;
		sec--;
		setTimeout(time,1000);
	}else{
		sec=60;
		$('passcode').textContent='發送驗證碼';
		$('passcode').disabled =false;
	}
	
	
};



document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('signup').addEventListener('click', handForSubmit);
    document.getElementById('passcode').addEventListener('click', passcode);
});
