<template>
    <div class="user container" id="join"> 
        <div class="wrapC table">
            <h1 style="padding-top: 30px;">회원가입</h1>
                    <div class="middle">
                        <div class="form-wrap">
                            <span v-if="isname">  
                                <div class="input-wrap">
                                <label class="label" for="name">이름</label>
                                <input v-model="name" id="name" class="true"
                                    placeholder="이름을 입력해주세요" type="text"
                                    style="border: 1px solid #035AA6;" @input="isName(name)"/>
                                </div>
                            </span>
                            <span v-else>
                                <div class="input-wrap">
                                <label class="label" for="name">이름</label>
                                <input v-model="name" id="name" class="false"
                                    placeholder="이름을 입력해주세요" type="text"
                                    style="border: 1px solid red;" @input="isName(name)"/>
                                <p class="warn" style="margin-bottom:0;"><small>이름을 입력해 주세요.</small></p>
                                </div>
                            </span>

                            <span v-if="isemail">  
                                <div class="input-wrap">
                                <label class="label" for="email">E-mail</label>
                                <input v-model="email" id="email" class="true"
                                    placeholder="이메일을 입력해주세요" type="text"
                                    style="border: 1px solid #035AA6;" @input="isEmail(email)"/>
                                </div>
                            </span>
                            <span v-else>
                                <div class="input-wrap">
                                <label class="label" for="email">E-mail</label>
                                <input v-model="email" id="email" class="false"
                                    placeholder="이메일을 입력해주세요" type="text"
                                    style="border: 1px solid red;" @input="isEmail(email)"/>
                                <p class="warn" style="margin-bottom:0;"><small>이메일 형식이 아닙니다.</small></p>
                                </div>       
                            </span>

                            <span v-if="ispassword">  
                                <div class="input-wrap password-wrap">
                                <label class="label" for="password">비밀번호</label>
                                <input v-model="pw" id="pw" class="true"
                                    placeholder="비밀번호를 입력해주세요" :type="passwordType"
                                    style="border: 1px solid #035AA6;" @input="isPassword(pw)"/>
                                </div>
                            </span>
                            <span v-else>
                                <div class="input-wrap password-wrap">
                                <label class="label" for="password">비밀번호</label>
                                <input v-model="pw" id="pw" class="false"
                                    placeholder="비밀번호를 입력해주세요" :type="passwordType"
                                    style="border: 1px solid red;" @input="isPassword(pw)"/>
                                <p class="warn" style="margin-bottom:0;"><small>영문자와 숫자의 8~10자리 조합만 유효합니다.</small></p>
                                </div>  
                            </span>  

                            <span v-if="isconfirm">  
                                <div class="input-wrap password-wrap">
                                <label class="label" for="confirm_password">비밀번호 확인</label>
                                <input v-model="passwordConfirm" id="password-confirm" class="true"
                                    placeholder="비밀번호를 한번 더 입력해주세요" :type="passwordConfirmType"
                                    style="border: 1px solid #035AA6;" @input="isConfirm(passwordConfirm)"/>
                                </div>
                            </span>
                            <span v-else>
                                <div class="input-wrap password-wrap">
                                <label class="label" for="confirm_password">비밀번호 확인</label>
                                <input v-model="passwordConfirm" id="password-confirm" class="false"
                                    placeholder="비밀번호를 한번 더 입력해주세요" :type="passwordConfirmType"
                                    style="border: 1px solid red;"
                                @input="isConfirm(passwordConfirm)"/>
                                <p class="warn" style="margin-bottom:0;"><small>비밀번호와 같지 않습니다.</small></p>
                                </div> 
                            </span> 

                            <label class="label" for="language">주사용 언어</label>
                            <b-form-select v-model="lang" :options="options"></b-form-select>

                            </div>
                        </div>

                        <button class="btn" @click="signup">
                            <span>
                                회원가입
                            </span>
                        </button>
                     
                    </div>
                <div>
                </div>
            </div>
</template>

<script>
    import '../../assets/css/user.scss'
    import axios from 'axios'
    
    export default {
            data: () => {
            return {
                email: null,
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
                isemail :false,
                isname: false,
                ispassword: false,
                isconfirm: false,
            }
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
            },
            isEmail(asValue) {
                var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
                this.isemail = regExp.test(asValue);

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
                console.log(this.isconfirm)
            },
            isName(asValue) {
                var flag = true;
                if (asValue == null){
                    flag = false
                }
                this.isname = flag
            },
            submit() {
                if (!this.isname){
                    this.name = null;
                    alert('이름을 입력하세요.');
                }
                else if (!this.isemail){
                    this.email = null;
                    alert('유효한 이메일 형식을 입력해 주세요.');
                }
                else if (!this.ispassword) {
                    this.pw = null;
                    alert('비밀번호는 8~10자리 영문자만 유효합니다.')
                }
                else if (!this.isconfirm) {
                    this.passwordConfirm = null;
                    alert('비밀번호 확인이 비밀번호와 같지 않습니다.')
                }
                else if (this.lang == null) {
                    alert('언어를 선택 하세요.')
                }
                else {
                    this.signup()
                }
            }

        }
    }

</script>


<style>
    @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gamja+Flower&family=Jua&display=swap');
    * {
        font-family: 'Jua', sans-serif;
    }

    input.false:focus {outline:1px solid #d50000;}
    input.true:focus {outline: 1px solid #035AA6}

    .warn{
        margin: 0;
        padding: 0;
        float: left;
        color: #d50000;
    }
    .user{
        background-color: rgb(236, 241, 243);
    }
</style>