<template>
    <div class="user" id="login">
        <div class="wrapC table">
            <div class="middle">
                <h1>로그인</h1>
                <div class="input-wrap">
                    <input v-model="email"
                        id="email" 
                        placeholder="이메일을 입력해주세요"
                        type="text"/>
                </div>
                <div class="input-wrap">
                    <input v-model="pw" type="password"
                        id="pw"
                        placeholder="영문, 숫자 혼용 8자 이상"/>
                </div>
                <button class="btn btn--back btn--login" @click="login">
                    로그인 하기
                </button>
                <div class="add-option">
                    <div class="wrap">
                        <p>아직 회원이 아니신가요?</p>
                        <router-link to="/join" class="btn--text">
                            회원가입
                        </router-link>
                    </div>

                </div>
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
                        this.$cookies.set('auth-token', response.data.data.token)
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


