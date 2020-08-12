<template>
    <div class="contanier" style="margin-top: 150px; margin" >
		<div class="module form-module">
			<div class="login-header">
				<!-- <div class="tooltip">Click Me</div> -->
			</div>
			<div class="form">
				<h2>로그인</h2>
				<form>
					<input v-model="email" id="email" type="text"  placeholder="이메일을 입력해주세요"/>
					<input v-model="pw" id="pw" type="password" placeholder="영문, 숫자 혼용 8자 이상"/>
				</form>
					<button @click="login" >로그인</button>
			</div>
			<!-- <div class="cta"><a href="http://andytran.me">Forgot your password?</a></div> -->

			<!-- <button class="social-signin facebook">페이스북으로 회원가입</button> -->
			<!-- <hr class="gubun">
            <h6 class="hidetext">SNS 간편로그인</h6> -->
            <button class="social-signin kakao" @click="kakaoLogin">카카오톡으로 로그인</button>
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
                // console.log("경로:::")
                // console.log(this.$store.state.base_url)
                axios.get(this.$store.state.base_url +'/account/login',{
                params: {
                        email: this.email,
                        pw: this.pw,
                    }
                })
                .then((response) => {
                    
                    if (response.data.status) {
                        // console.log(response)
                        // console.log(response.data.data["ACCESS-TOKEN"])
                        this.$cookies.set('auth-token', response.data.data["ACCESS-TOKEN"])
                        alert('로그인 되었습니다.')
                        this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                        this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                        // console.log(this.$store.state.token)
                        // console.log(this.$store.state.islogin)
                        this.$router.push('/')
                    }
                    else {
                        alert(response.data.data.msg)
                    }
                })
                .catch((error) => {
                    alert(error)
                    // console.log('error')
                    // console.log(error)
                }) 
            },
            kakaoLogin(){
                var vm = this;
                 Kakao.Auth.login({
                     success: response => {
                        // console.log(response)
                        // console.log('token: '+response.access_token)
                        Kakao.API.request({
                            url: '/v2/user/me',
                            success: function(res) {
                                console.log(res)
                                // console.log('경로:::::');
                                // console.log(vm.$store.state.base_url)
                                // console.log('닉네임 : ' + res.kakao_account.profile.nickname)
                                // console.log('email : ' + res.kakao_account.email)
                                 axios
                                .get( 'http://localhost/kakao', {
                                    params: {
                                        name: res.kakao_account.profile.nickname,
                                        email: res.kakao_account.email
                                    }
                                })
                                .then(function (response) {
                                    // console.log(response)
                                    // console.log(response.data.data["ACCESS-TOKEN"])
                                    // console.log('success')
                                    alert('로그인 되었습니다.')
                                    vm.$cookies.set('auth-token', response.data.data["ACCESS-TOKEN"])
                                    vm.$store.commit('checkToken', vm.$cookies.get('auth-token'))
                                    vm.$store.commit('checklogin', vm.$cookies.isKey('auth-token'))
                                    // console.log(vm.$store.state.token)
                                    // console.log(vm.$store.state.islogin)
                                    vm.$router.push('/')
                                })
                                .catch((error) => {
                                    alert('error')
                                    console.log('error')
                                    console.log(error)
                                }) 

                            }
                        })
                    },
                    fail : err => {
                        console.log(err)
                    }

                 })
            },
            kakaoLogout(){
                Kakao.API.request({
                url: '/v1/user/unlink',
                success: function(response) {
                    console.log(response);
 
                },
                fail: function(error) {
                    console.log(error);
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
	body {
		background: #e9e9e9;
		color: #666666;
		font-family: 'RobotoDraft', 'Roboto', sans-serif;
		font-size: 14px;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
	}

	/* Pen Title */
	.pen-title {
		padding: 50px 0;
		text-align: center;
		letter-spacing: 2px;
	}
	.pen-title h1 {
		margin: 0 0 20px;
		font-size: 48px;
		font-weight: 300;
	}
	.pen-title span {
		font-size: 12px;
	}
	.pen-title span .fa {
		color: #33b5e5;
	}
	.pen-title span a {
		color: #33b5e5;
		font-weight: 600;
		text-decoration: none;
	}

	/* Form Module */
	.form-module {
		position: relative;
		background: #ffffff;
		width: 400px;
        height: 400px;
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
	/* .form-module .login-header .tooltip {
		position: absolute;
		top: 5px;
		right: -65px;
		display: block;
		background: rgba(0, 0, 0, 0.6);
		width: auto;
		padding: 5px;
		font-size: 10px;
		line-height: 1;
		text-transform: uppercase;
	}  */
	/* .form-module .login-header .tooltip:before {
		content: '';
		position: absolute;
		top: 5px;
		left: -5px;
		display: block;
		border-top: 5px solid transparent;
		border-bottom: 5px solid transparent;
		border-right: 5px solid rgba(0, 0, 0, 0.6);
	} */
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
		background: #178ab4;
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
		font-family: "Roboto", sans-serif;
		font-weight: 500;
		transition: 0.2s ease;
		cursor: pointer;
	}

	button.social-signin:hover,
	button.social-signin:focus {
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
		transition: 0.2s ease;
	}

	button.social-signin:active {
		box-shadow: 0 1px 2px rgba(0, 0, 0, 0.4);
		transition: 0.2s ease;
	}

	button.social-signin.facebook {
		background: #32508e;
	}

	button.social-signin.kakao {
		background: #ffd700;
        color: #603030;
	}

</style>