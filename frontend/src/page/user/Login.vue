<template>
    <div class="contanier" style="margin-top: 150px; margin" >
		<div class="module form-module">
			<div class="login-header">
				<!-- <div class="tooltip">Click Me</div> -->
			</div>
			<div class="form" style="padding-bottom: 10px; padding-top: 40px;">
				<img src="../../assets/img/hello_logo.png" style="width: 200px; height: 110px; padding-bottom: 30px;" alt="">
				<!-- <h2 style="font-size: 21pt;">로그인</h2> -->
				<form>
					<input v-model="email" id="email" type="text" @keyup.enter="login" style="font-family: 'NEXON Lv2 Gothic Light';" placeholder="이메일을 입력해주세요"/>
					<input v-model="pw" id="pw" type="password" @keyup.enter="login" style="font-family: 'NEXON Lv2 Gothic Light';" placeholder="영문, 숫자 혼용 8자 이상"/>
				</form>
				<button @click="login" style="font-family: 'NEXON Lv2 Gothic Bold';">로그인</button>
			</div>
			<hr style="margin-left: 15px; margin-right: 15px; margin-top: 5px;">
			
			<!-- <div class="cta"><a href="http://andytran.me">Forgot your password?</a></div> -->

			<!-- <button class="social-signin facebook">페이스북으로 회원가입</button> -->
			<!-- <hr class="gubun">
            <h6 class="hidetext">SNS 간편로그인</h6> -->
			<!-- <p style="margin-bottom: 10px;"> SNS 간편로그인</p> -->
			<!-- <div style="float: right; background-color: yello; margin-left: 10px;">
				<img class="kakao" @click="kakaoLogin" src="../../assets/img/kakao.png" alt="">
				<span style="margin-right: 10px;">카카오톡 간편 로그인</span>
			</div> -->
            <button class="social-signin" @click="kakaoLogin"><img class="kakao" @click="kakaoLogin" src="../../assets/img/kakao.png" alt="">카카오계정으로 로그인</button>
		</div>
	</div>


</template>

<script>
    import '../../assets/css/user.scss'
    import constants from '../../lib/constants'
    import axios from 'axios'
    
    export default {
        components: {
        },
        created(){
        },
        watch: {
        },
        methods: {
            login() {
                axios.get(this.$store.state.base_url +'/account/login',{
                params: {
                        email: this.email,
                        pw: this.pw,
                    }
                })
                .then((response) => {
                    if (response.data.status) {
                        this.$cookies.set('auth-token', response.data.data["ACCESS-TOKEN"])
                        swal('', '로그인 되었습니다.', 'success')
                        this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                        this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                        this.$router.push('/')
                    }
                    else {
                        swal('', response.data.data.msg, 'warning')
                    }
                })
                .catch((error) => {
                    swal('', error, 'error')
                }) 
            },
            kakaoLogin(){
                var vm = this;
                 Kakao.Auth.login({
                     success: response => {
                        Kakao.API.request({
                            url: '/v2/user/me',
                            success: function(res) {
                                 axios
                                .get( 'http://3.34.42.229:8080/kakao', {
                                    params: {
                                        name: res.kakao_account.profile.nickname,
                                        email: res.kakao_account.email
                                    }
                                })
                                .then(function (response) {
                                    swal('', '로그인 되었습니다.', 'success')
                                    vm.$cookies.set('auth-token', response.data.data["ACCESS-TOKEN"])
                                    vm.$store.commit('checkToken', vm.$cookies.get('auth-token'))
                                    vm.$store.commit('checklogin', vm.$cookies.isKey('auth-token'))
                                    vm.$router.push('/')
                                })
                                .catch((error) => {
                                    swal('', 'error', 'error')
                                }) 

                            }
                        })
                    },
                    fail : err => {
                    }

				})
            },
            kakaoLogout(){
                Kakao.API.request({
                url: '/v1/user/unlink',
                success: function(response) {
 
                },
                fail: function(error) {
                },
                });
            },
        },
        data: () => {
            return {
                constants,
                email: '',
                pw: '',
            }
        }

    }
</script>

<style scoped>

	@font-face {
		font-family: 'NEXON Lv2 Gothic Light';
		src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv2 Gothic Light.woff') format('woff');
		font-weight: normal;
		font-style: normal;
	}
            

	@font-face {
        font-family: 'NEXON Lv2 Gothic Bold';
        src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv2 Gothic Bold.woff') format('woff');
        font-weight: normal;
        font-style: normal;
    }
            
	body {
		background: #e9e9e9;
		color: #666666;
		font-family: 'RobotoDraft', 'Roboto', sans-serif;
		font-size: 14px;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
	}

	/* Form Module */
	.form-module {
		position: relative;
		background: #ffffff;
		width: 400px;
        height: 450px;
		border-top: 5px solid #33b5e5;
		box-shadow: 0 0 3px rgba(0, 0, 0, 0.1);
		margin: 0 auto;
	}

	.form-module .login-header {
		cursor: pointer;
		position: absolute;
		top: -0;
		right: -0;
		background: #33b5e5;
		/* width: 30px;
		height: 30px; */
		margin: -5px 0 0;
		color: #ffffff;
		font-size: 12px;
		line-height: 30px;
		text-align: center;
	}

	.form-module .form {
		display: none;
		padding: 40px;
	}

	.form-module .form:nth-child(2) {
		display: block;
	}

	.form-module h2 {
		margin: 0 0 20px;
		color: #33b5e5;
		font-size: 18px;
		font-weight: 400;
		line-height: 1;
	}
	.form-module input {
		outline: none;
		display: block;
		width: 100%;
		border: 1px solid #d9d9d9;
		margin: 0 0 20px;
		padding: 10px 15px;
		box-sizing: border-box;
		font-weight: 400;
		-webkit-transition: 0.3s ease;
		transition: 0.3s ease;
	}

	.form-module input:focus {
		border: 1px solid #33b5e5;
		color: #333333;
	}

	.form-module button {
		cursor: pointer;
		background: #33b5e5;
		width: 100%;
		border: 0;
		padding: 10px 15px;
		color: #ffffff;
		-webkit-transition: 0.3s ease;
		transition: 0.3s ease;
	}

	.form-module button:hover {
		opacity: 0.8;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
		transition: 0.1s ease;
	}

	.form-module .cta {
		background: #f2f2f2;
		width: 100%;
		padding: 15px 40px;
		box-sizing: border-box;
		color: #666666;
		font-size: 12px;
		text-align: center;
	}

	.form-module .cta a {
		color: #333333;
		text-decoration: none;
	}

	button.social-signin {
		margin-bottom: 20px;
		width: 320px;
		height: 45px;
		border: none;
		border-radius: 2px;
		color: #fff;
		font-family: 'NEXON Lv2 Gothic Bold';
		font-weight: 500;
		transition: 0.2s ease;
		cursor: pointer;
	}

	/* .kakao:hover {
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
		transition: 0.2s ease;
	} */

	.kakao {
		font-family: 'NEXON Lv2 Gothic Bold';
		cursor: pointer;
		margin-right: 10px;
		height: 30px;
		width: 30px;
	}

	button.social-signin:hover,
	button.social-signin:focus {
		opacity: 0.8;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
		transition: 0.1s ease;
	}

	button.social-signin:active {
		opacity: 1;
		box-shadow: 0 1px 2px rgba(0, 0, 0, 0.4);
		transition: 0.1s ease;
	}

	button.social-signin {
		background: #f9e000;
        color: #603030;
	}

</style>