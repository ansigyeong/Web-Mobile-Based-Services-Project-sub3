<template>
    <div class="user" id="login">
        <div class="wrapC table">
            <div class="middle">
                <h1>SS_log</h1>
                <div class="input-wrap">
                    <input v-model="email"
                        id="email" 
                        placeholder="이메일을 입력해주세요"
                        type="text"/>
                </div>
                <div class="input-wrap">
                    <input v-model="password" type="password"
                        id="password"
                        placeholder="영문, 숫자 혼용 8자 이상"/>
                </div>
                <button class="btn btn--back btn--login"  @click="login">
                    로그인 하기
                </button>
                <div class="add-option">
                    <div class="wrap">
                        <p>아직 회원이 아니신가요?</p>
                        <router-link v-bind:to="{name:constants.URL_TYPE.USER.JOIN}" class="btn--text">
                            회원가입
                        </router-link>
                    </div>

                </div>
                    <a href="https://kauth.kakao.com/oauth/authorize?client_id=a02e0d55e3c3e558db7f944f5d746ac1&redirect_uri=http://localhost/kakao&response_type=code">
                    <img src="src/img/kakao.png">
                    </a>
            </div>
            
        </div>

    </div>
</template>

<script>
    import '../../assets/css/user.scss'
    import constants from '../../lib/constants'
    import axios from 'axios'

    export default {
        data: () => {
            return {
                constants,
                email: '',
                password: '',

            }
        },
        components: {
        },
        created(){
        },
        watch: {
        },
        methods: {
            login(){
            console.log("login");
                //백앤드와 로그인 통신하는 부분
            axios.get('http://localhost/account/login', {
                params:{
                    email:this.email, pw:this.password}
            })
            .then(res => {
                console.log("login success");
                alert("로그인 되었습니다.");
                this.moveHome();
            })
            .catch(res => {
                console.log("login fail");
                alert("다시 로그인 해주세요");
            });

            },
            moveHome(){
                this.$router.push('/');
            }
        }

    }

</script>


