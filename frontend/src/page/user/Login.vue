<template>
    <div class="user container" id="login">
        <div class="wrapC table">
            <div class="middle">
                <h1 class="usertitle">로그인</h1>
                <div class="input-wrap">
                    <label class="label" for="name">이메일</label>
                    <input v-model="email"
                        id="email" style="margin-bottom:10px;"
                        placeholder="이메일을 입력해주세요"
                        type="text"/>
                </div>
                <div class="input-wrap">
                    <label class="label" for="name">비밀번호</label>
                    <input v-model="pw" type="password"
                        id="pw" style="margin-bottom:10px;"
                        placeholder="영문, 숫자 혼용 8자 이상"/>
                </div>
                <button class="btn btn--login col-6 signin-btn" @click="login">
                    로그인
                </button>
                <button class="btn col-6 signup-btn"><router-link to="/join">회원가입</router-link></button>
                <h6 class="hidetext">SNS 간편로그인하기</h6>
                <hr class="gubun">
                <h6 class="appeartext">SNS 간편 로그인하기</h6>
                <h6 class="hidetext">SNS 간편로그인</h6>
                <h6 class="hidetext">SNS 간편로그인</h6>
                <img class="login_with" src="@/assets/img/kacircle.png" alt="Log In with Kakao ">
                <img class="login_with" src="@/assets/img/fbcircle.png" alt="Log In with facebook">
                <hr class="gubun2">
                <!-- <img class="badafriends" src="@/assets/img/badafriends2.png" alt=""> -->
            
                </div>
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
                console.log(this.$store.state.base_url)
                axios.get(this.$store.state.base_url +'/account/login',{
                params: {
                        email: this.email,
                        pw: this.pw,
                    }
                })
                .then((response) => {
                    if (response.data.status) {
                        console.log(response)
                        console.log(response.data.data["ACCESS-TOKEN"])
                        this.$cookies.set('auth-token', response.data.data["ACCESS-TOKEN"])
                        alert('로그인 되었습니다.')
                        this.$store.commit('checkToken',this.$cookies.get('auth-token'))
                        this.$store.commit('checklogin',this.$cookies.isKey('auth-token'))
                        console.log(this.$store.state.token)
                        console.log(this.$store.state.islogin)
                        this.$router.push('/list')
                    }
                    else {
                        alert(response.data.data.msg)
                    }
                })
                .catch((error) => {
                    console.log('error')
                    console.log(error)
                })
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