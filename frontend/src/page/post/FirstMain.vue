<template>
    <body>

        <div class="waves background"></div>
        <div class="whale"></div>
        <div class="boat-wrapper">
            <div class="boat"></div>
        </div>
        <div class="waves foreground"></div>


        <div class="center sign">
            <h1>
                <p class="bluecolor sign__word" style="margin:0">Hello</p>
                <span class="words greencolor sign__word" id="y1">C</span>
                <span class="words greencolor sign__word" id="y2">o</span>
                <span class="words greencolor sign__word" id="y3">d</span>
                <span class="words greencolor sign__word" style="margin-right: 20px;" id="y4">e</span>
                <span class="words redcolor sign__word" >Sea</span>
            </h1>
        </div>
    </body>
</template>

<script>
import '../../assets/css/FirstMain.scss'

export default {
    name: 'FirstMain'
}
</script>

<style>
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
        animation: whale 20s linear 0s infinite;
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
}

.bluecolor {
  color : #2962ff;
}

.greencolor {
  color : #4Caf50;
}

.yellowcolor {
  color : #ffeb3b;
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


/* neon */

html,
body {
    position: relative;
  margin: 0;
  height: 100%;
}

html {
  font-size: 16px;
}

/* body {
  font-family: "Vibur", cursive;
  font-size: 1rem;
  background-image: url("https://www.dropbox.com/s/2ct0i6kc61vp0bh/wall.jpg?raw=1");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-color: #aaf7ff;
} */


/*-- Sign Styles --*/

.sign {
  min-height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  &__word {
    font-size: 5.6rem;
    text-align: center;
    line-height: 1;
    color: #c6e2ff;
    animation: neon .08s ease-in-out infinite alternate;
  }

}

/*-- Animation Keyframes --*/

/* // animation */

@keyframes neon {
  from {
    text-shadow:
    0 0 6px rgba(202,228,225,0.92),
    0 0 30px rgba(202,228,225,0.34),
    0 0 12px rgba(30,132,242,0.52),
    0 0 21px rgba(30,132,242,0.92),
    0 0 34px rgba(30,132,242,0.78),
    0 0 54px rgba(30,132,242,0.92);
  }
  to {
    text-shadow:
    0 0 6px rgba(202,228,225,0.98),
    0 0 30px rgba(202,228,225,0.42),
    0 0 12px rgba(30,132,242,0.58),
    0 0 22px rgba(30,132,242,0.84),
    0 0 38px rgba(30,132,242,0.88),
    0 0 60px rgba(30,132,242,1);
  }
}


/* 
// Link */
.link {
    position: absolute;
    bottom: 10px; left: 10px;
    color: #828282;
    text-decoration: none;
    
    &:focus,
    &:hover {
    color: #c6e2ff;
        text-shadow:
    0 0 2px rgba(202,228,225,0.92),
    0 0 10px rgba(202,228,225,0.34),
    0 0 4px rgba(30,132,242,0.52),
    0 0 7px rgba(30,132,242,0.92),
    0 0 11px rgba(30,132,242,0.78),
    0 0 16px rgba(30,132,242,0.92);
    }
}

</style>

