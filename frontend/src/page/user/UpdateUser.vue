<template>
   	<div calss="container" style="margin-top: 150px;">
		<div id="signup-box">
			<div class="left" style="border-top: solid 5px #33b5e5; ">
				<h1>회원 정보 수정</h1>

            <template v-if="isname" >
				<input v-model="name" class="input_vaild" @input="isName(name)" type="text" name="username" style="font-family: 'NEXON Lv2 Gothic Light';" placeholder="이름을 입력해주세요" />
            </template>
            <template v-else>
                <input v-model="name" @input="isName(name)" type="text" name="name" style="font-family: 'NEXON Lv2 Gothic Light';" placeholder="이름을 입력해주세요" />
                <p class="warn"><small>이름을 입력해 주세요.</small></p>
            </template>
            <span v-if="this.$route.params.kakao == null ">
                <template>
                    <input v-model="priorpw" class="input_vaild" type="password" name="email" style="font-family: 'NEXON Lv2 Gothic Light';" placeholder="이전 비밀번호를 입력해주세요" />
                </template>

                <template v-if="ispassword">
                    <input v-model="pw" class="input_vaild" @input="isPassword(pw)" type="password" name="password" style="font-family: 'NEXON Lv2 Gothic Light';" placeholder="새로운 비밀번호를 입력해주세요" />
                </template>
                <template v-else>
                    <input v-model="pw"  @input="isPassword(pw)" type="password" name="password" style="font-family: 'NEXON Lv2 Gothic Light';" placeholder="새로운 비밀번호를 입력해주세요" />
                    <p class="warn"><small>영문자와 숫자의 8~10자리 조합만 유효합니다.</small></p>
                </template>

                <template v-if="isconfirm">
                    <input v-model="passwordConfirm" class="input_vaild" @input="isConfirm(passwordConfirm)" type="password" name="password2" placeholder="비밀번호를 한번 더 입력해주세요" />
                </template>
                <template v-else>
                    <input v-model="passwordConfirm" @input="isConfirm(passwordConfirm)" type="password" name="password2" placeholder="비밀번호를 한번 더 입력해주세요" />
                    <p class="warn" ><small>비밀번호와 같지 않습니다.</small></p>
                </template>
            </span>

            <!-- <label class="label" for="language">주사용 언어</label> -->
            <b-form-select v-model="lang" :options="options" style="font-family: 'NEXON Lv2 Gothic Light';"></b-form-select>
				
            <input style="font-family: 'CookieRun-Regular';" @click="submit" type="submit" name="signup_submit" value="회원 정보 수정" />

            </div>
		

		</div>
    </div>

</template>

<script>
    import '../../assets/css/user.scss'
    import axios from 'axios'
    
    export default {
            data: () => {
            return {
                priorpw: null,
                name: null,
                pw: null,
                passwordConfirm: null,
                lang: null,
                passwordType:"password",
                passwordConfirmType:"password",
                options: [
                { value: null, text: '언어를 입력해 주세요' },
                { value: 'c', text: 'C' },
                { value: 'cpp', text: 'C++' },
                { value: 'java', text: 'Java' },
                { value: 'python', text: 'Python' }],
                isname: false,
                ispassword: false,
                isconfirm: false,
            }
        },
        methods: {
            update() {
                let config = {
                    headers: {
                        "ACCESS-TOKEN": this.$store.state.token
                    }
                }
                let body = {
                    pw: this.priorpw,
                    newPw: this.pw,
                    name: this.name,
                    lang: this.lang,
                    role: null,
                }
                if (this.$route.params.kakao == null){
                    body.role = 'ROLE_USER'
                }
                else {
                    body.role = 'ROLE_KAKAO'
                }
                axios.put(this.$store.state.base_url +'/account/update',body, config)
                .then((response) => {
                    swal('', '회원정보가 성공적으로 수정되었습니다.', 'success')
                    this.$router.push('/')
                })
                .catch((error) => {
                    swal('', '이전 비밀번호가 일치하지 않습니다.', 'warning')
                    this.priorpw = ''
                })
            },
            isPassword(asValue) {
	            var regExp = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,10}$/; //  8 ~ 10자 영문, 숫자 조합
	            this.ispassword = regExp.test(asValue);
            },
            isConfirm(asValue) {
                var flag = false;
                if (this.pw == asValue){
                    flag = true;
                }
                this.isconfirm = flag
            },
            isName(asValue) {
                var flag = true;
                if (asValue == null || this.noblank(asValue) == ''){
                    flag = false
                }
                this.isname = flag
            },
            submit() {

                if (this.$route.params.kakao == 'kakao')
                {
                    if (!this.isname || this.name == null || this.name == ''){
                        this.name = null;
                        swal('', '이름을 입력하세요.', 'warning');
                    }
                    else if (this.lang == null) {
                        swal('','언어를 선택 하세요.', 'warning')
                    }
                    else {
                        this.update()
                    }
                }
                else
                {
                    if (!this.isname || this.name == null || this.name == ''){
                        this.name = null;
                        swal('', '이름을 입력하세요.', 'warning');
                    }
                    else if (!this.ispassword || this.pw == null || this.pw == '') {
                        this.pw = null;
                        swal('','비밀번호는 8~10자리 영문자만 유효합니다.', 'warning')
                    }
                    else if (!this.isconfirm || this.passwordConfirm == null || this.passwordConfirm == '') {
                        this.passwordConfirm = null;
                        swal('','비밀번호 확인이 비밀번호와 같지 않습니다.', 'warning')
                    }
                    else if (this.lang == null) {
                        swal('','언어를 선택 하세요.', 'warning')
                    }
                    else {
                        this.update()
                    }
                }

            },
            noblank(contents){
                return contents.replace(/(\s*)/g, "").replace(/&nbsp;/g, "")
            },
        }
    }

</script>


<style  scoped>
    @font-face {
		font-family: 'NEXON Lv2 Gothic Light';
		src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv2 Gothic Light.woff') format('woff');
		font-weight: normal;
		font-style: normal;
	}

    @font-face {
        font-family: 'CookieRun-Regular';
        src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
        font-weight: normal;
        font-style: normal;
    }

  /* * {
      font-family: 'CookieRun-Regular';
    } */
            


	/* @import url(https://fonts.googleapis.com/css?family=Roboto:400, 300, 500);
	*:focus {
		outline: none;
	} */

	body {
		margin: 0;
		padding: 0;
		background: #ddd;
		font-size: 16px;
		color: #222;
		font-family: "Roboto", sans-serif;
		font-weight: 300;
	}

	#signup-box {
		position: relative;
		margin: 5% auto;
		width: 400px;
		height: 500px;
		background: #fff;
		border-radius: 2px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
	}

	.left {
		position: absolute;
		top: 0;
		left: 0;
		box-sizing: border-box;
		padding: 40px;
		width: 400px;
		height: 400px;
	}

	h1 {
		margin: 0 0 20px 0;
		font-weight: 400;
		font-size: 23px;
		line-height: 1;
        color: #33b5e5;
	}

	input[type="text"],
	input[type="password"] {
		display: block;
		box-sizing: border-box;
		/* margin-bottom: 20px; */
		padding: 4px;
		width: 320px;
		height: 32px;
		border: none;
		border-bottom: 1px solid #aaa;
		font-family: "Roboto", sans-serif;
		font-weight: 400;
		font-size: 15px;
		transition: 0.2s ease;
	}

	input[type="text"]:focus,
	input[type="password"]:focus {
		/* border-bottom: 2px solid #16a085; */
		color: #061416;
		transition: 0.2s ease;
	}

	input[type="submit"] {
		margin-top: 28px;
		width: 120px;
		height: 32px;
		background: #33b5e5;
		border: none;
		border-radius: 2px;
		color: #fff;
		font-family: "Roboto", sans-serif;
		font-weight: 500;
		text-transform: uppercase;
		transition: 0.1s ease;
		cursor: pointer;
	}

	input[type="submit"]:hover,
	input[type="submit"]:focus {
		opacity: 0.8;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
		transition: 0.1s ease;
	}

	input[type="submit"]:active {
		opacity: 1;
		box-shadow: 0 1px 2px rgba(0, 0, 0, 0.4);
		transition: 0.1s ease;
	}

    .input_vaild{
        margin-bottom: 20px;
    }

    .warn{
        text-align: left;
        margin-bottom : 10px;
        color: rgb(206, 70, 70);
    }

</style>
