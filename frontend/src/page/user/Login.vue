<template>
    <div class="user" id="login">
        <div class="wrapC table">
            <div class="middle">
                <h1>Hello, Code_Sea!</h1>
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
                <button class="btn btn--back btn--login">
                    Sign In
                </button>
                <div class="add-option">
                    <div class="wrap">
                        <p>아직 회원이 아니신가요?</p>
                        <router-link v-bind:to="{name:constants.URL_TYPE.USER.JOIN}" class="btn--text">
                            Sign Up
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
    
    export default {
        components: {
        },
        created(){
        },
        watch: {
        },
        methods: {
        },
        data: () => {
            return {
                constants,
                email: '',
                password: '',

            }
        }

    }

</script>


