<template>
    <div class="user" id="join"> 
        <div class="wrapC table">
            <div class="middle">
                <h1>회원가입</h1>
                <div class="form-wrap">
                    <div class="input-wrap">
                        <input v-model="name"
                            id="name"
                            placeholder="이름을 입력해주세요" type="text"/>
                    </div>

                    <div class="input-wrap">
                        <input v-model="email" 
                            id="email"
                            placeholder="이메일을 입력해주세요"
                            type="text"/>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input v-model="pw"
                            id="pw" 
                            :type="passwordType"
                            placeholder="비밀번호를 입력해주세요"/>
                        <span :class="{active : passwordType==='text'}">
                                <i class="fas fa-eye"></i>
                            </span>
                    </div>

                    <div class="input-wrap password-wrap">
                        <input v-model="passwordConfirm" 
                            id="password-confirm"
                            :type="passwordConfirmType"
                            placeholder="비밀번호를 한번 더 입력해주세요"/>
                        <span :class="{active : passwordConfirmType==='text'}">
                                <i class="fas fa-eye"></i> 
                            </span>
                    </div>

                    <div class="input-wrap">
                        <input v-model="Language" 
                            id="lang"
                            placeholder="언어를 입력해주세요"
                            type="text"/>
                    </div>
                </div>

                <button class="btn" @click="signup">
                    <span>
                        회원가입
                    </span>
                </button>
            </div>
        </div> 
    </div>
</template>

<script>
    import '../../assets/css/user.scss'
    import Language from '../../components/Language.vue'
    import axios from 'axios'
    export default {
        components: {
            Language,
        },
        created(){
        },
        methods: {
            signup() {
                axios.post(this.$store.state.base_url +'/account/signup',
                {
                    email: this.email,
                    pw: this.pw,
                    name: this.name,
                    lang: this.lang
                })
                .then((response) => {
                    console.log(response)
                    alert('회원가입 되었습니다.\n가입하신 이메일의 인증 메일을 확인해주세요.')
                    this.$router.push('/login')
                })
                .catch((error) => {
                    alert('가입된 이메일 입니다.\n다른 이메일을 사용해 주세요.')
                })
            }
        },
        watch: {
        },
        data: () => {
            return {
                email: '',
                name: '',
                pw: '',
                passwordConfirm: '',
                lang:'',
                passwordType:"password",
                passwordConfirmType:"password",
            }
        }

    }

</script>


