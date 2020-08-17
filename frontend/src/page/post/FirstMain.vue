<template>
    <body>
        <!-- sky -->
        <div class="sky"></div>

        <!-- background -->
        <div class="waves background"></div>
        <div class="whale"></div>
        <div class="boat-wrapper">
            <div class="boat"></div>
        </div>
        <div class="waves foreground"></div>


        <!-- logo -->
        <div class="center">
            <h1 style="padding: 10px;">
                <p class="bluecolor" style="margin:0; margin-top: 15px;">Hello,</p>
                <span class="words redcolor" style="margin-top:15px;" id="y1">C</span>
                <span class="words yellowcolor" style="margin-top:15px;" id="y2">o</span>
                <span class="words greencolor" style="margin-top:15px;" id="y3">d</span>
                <span class="words yellowcolor" style="margin-top:15px; margin-right: 20px;" id="y4">e</span>
                <span class="words bluecolor" style="margin-top:15px;">Sea</span>
            </h1>
        </div>
    </body>
</template>

<script scoped>
import '../../assets/css/FirstMain.scss'
export default {
    name: 'FirstMain',
    methods: {
        goAsk() {
            if (this.$store.state.islogin) {
                this.$router.push('/askquestion')
            }
            else{
                this.$router.push('/login')
            }
        }
    }
}
</script>

<style scoped>
            @font-face {
    font-family: 'yg-jalnan';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_four@1.2/JalnanOTF00.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
            


    html,
    body {
        height: 100%;
    }

    body {
        overflow: hidden;
    }

    .waves {
        animation: wave 2s ease-in-out 0s infinite alternate;
        background: repeat-x url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/59639/waves.png') center top;
        background-size: auto 80px;
        bottom: -10px;
        height: 80px;
        left: -13%;
        position: absolute;
        width: 120%;
    }
    .waves.background {
        animation-direction: alternate-reverse;
        bottom: 30px;
        left: -18%;
    }

    .boat-wrapper {
        animation: boat 20s linear 0s infinite;
        bottom: 25px;
        height: 200px;
        left: 0;
        position: absolute;
        width: 100%;
    }
    .boat {
        animation: bobble 2s ease-in-out 0s infinite alternate;
        background: no-repeat url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/59639/boat.png') center bottom;
        background-size: contain;
        height: 100%;
        margin: 0 auto;
        width: 200px;
    }

    .whale {
        animation: whale 18s linear 0s infinite;
        background: no-repeat url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/59639/whale.png') center center;
        background-size: contain;
        bottom: 0;
        height: 350px;
        left: 50%;
        margin-left: -200px;
        position: absolute;
        transform: rotate(40deg);
        width: 350px;
    }

    @keyframes wave {
        0% {
            transform: translateY(0);
        }
        100% {
            transform: translateY(6%);
        }
    }

    @keyframes boat {
        0% {
            transform: translateX(100%);
        }
        100% {
            transform: translateX(-100%);
        }
    }

    @keyframes bobble {
        0% {
            transform: translateY(0) rotate(-4deg);
        }
        100% {
            transform: translateY(3%) rotate(3deg);
        }
    }

    @keyframes whale {
        0% {
            transform: translateY(100%) rotate(40deg);
        }
        72% {
            transform: translateY(100%) rotate(40deg);
        }
        76% {
            transform: translateY(10%) rotate(80deg);
        }
        77% {
            transform: translateY(20%) rotate(90deg);
        }
        80% {
            transform: translateY(100%) rotate(120deg);
        }
        100% {
            transform: translateY(100%) rotate(120deg);
        }
    }


  /* 1번째 화면 */
#y1 { 
  animation-name: ym;
  animation-duration: 1s;
  animation-iteration-count: 1;
}
#y2 { 
  animation-name: ym;
  animation-duration: 2s;
  animation-iteration-count: 1;
}
#y3 { 
  animation-name: ym;
  animation-duration: 3s;
  animation-iteration-count: 1;
}
#y4 { 
  animation-name: ym;
  animation-duration: 4s;
  animation-iteration-count: 1;
}
#y5 {
  animation-name: ym;
  animation-duration: 5s;
  animation-iteration-count: 1;

}

@keyframes ym {0% {font-size: 0; opacity:0;} 30% {display: none; opacity:0;} 100% {display: block; font-size: 112px; opacity:1;}}
@-webkit-keyframes ym { 0% {font-size: 0; opacity:0;} 30% {display: none; opacity:0;} 100% {display: block; font-size: 112px; opacity:1;}}


html {
  background-color: #ffffff;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}

body {
  font-family: "Verdana", sans-serif;
  -webkit-font-smoothing: antialiased;
  font-smoothing: antialiased;
}

.input-form {
  margin : 0px auto;
  display: block;
  width: 400px;
  height: 30px;
  border-radius: 50px;
  border : 1px solid #bdbdbd;
  font-size: 20pt;
  text-align: right;
  padding-right: 20px;
}

.bottom {
  margin-top : 150px!important;
  clear : both;
}

.words {
  float: left;
}

.redcolor {
  color : #f44336;
  text-shadow: 4px 4px #bbb;
  font-family: 'yg-jalnan';
  /* font-size: 120px; */
}

.bluecolor {
  color : #002690;
  text-shadow: 4px 4px #bbb;
  font-family: 'yg-jalnan';
  margin-bottom: 10px;
  /* font-size: 120px; */
}

.greencolor {
  color : rgb(35, 173, 124);
  text-shadow: 4px 4px #bbb;
  font-family: 'yg-jalnan';
  /* font-size: 120px;} */
}

.yellowcolor {
  color : #ffeb3b;
  text-shadow: 4px 4px #bbb;
  font-family: 'yg-jalnan';
  /* font-size: 120px; */
  }

h1 {
  text-align:justify;
  width: 100%;
  margin: 0px;
  line-height: .95;
  font-weight: 600;
  font-size: 7rem;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  pointer-events: none;
  clear: both;
  display: block;
}

.center {
  position: absolute;
  margin: auto;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  width: 600px;
  height: 50%;
}





</style>

