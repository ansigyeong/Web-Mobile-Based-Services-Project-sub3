<template>
    <div class="seaContainer">
        <div >
            <img class="footermsg" src="../../assets/img/footermsg.png" alt="">
        </div>
        <div>
            <img class="ceo" src="../../assets/img/ceo.png" alt="">
        </div>

        <div class="submarine__container">
            <div class="light"></div>
            <div class="submarine__periscope"></div>
            <div class="submarine__periscope-glass"></div>
            <div class="submarine__sail">
            <div class="submarine__sail-shadow dark1">
            </div>
            <div class="submarine__sail-shadow light1"></div>
            <div class="submarine__sail-shadow dark2"></div>
            </div>
            <div class="submarine__body">
            <div class="submarine__window one">

            </div>
            <div class="submarine__window two">

            </div>
            <div class="submarine__shadow-dark"></div>
            <div class="submarine__shadow-light"></div>
            <div class="submarine__shadow-arcLight"></div>
            </div>
            <div class="submarine__propeller">
            <div class="propeller__perspective">
            <div class="submarine__propeller-parts darkOne"></div>
            <div class="submarine__propeller-parts lightOne"></div>
            </div>        
            </div>
        </div>
        <div class="bubbles__container">
            <span class="bubbles bubble-1"></span>
            <span class="bubbles bubble-2"></span>
            <span class="bubbles bubble-3"></span>
            <span class="bubbles bubble-4"></span>
        </div>
        <div class="ground__container">
            <div class="ground ground1">
            <span class="up-1"></span>
            <span class="up-2"></span>
            <span class="up-3"></span>
            <span class="up-4"></span>
            <span class="up-5"></span>
            <span class="up-6"></span>
            <span class="up-7"></span>
            <span class="up-8"></span>
            <span class="up-9"></span>
            <span class="up-10"></span>
            </div>
            <div class="ground ground2">
            <span class="up-1"></span>
            <span class="up-2"></span>
            <span class="up-3"></span>
            <span class="up-4"></span>
            <span class="up-5"></span>
            <span class="up-6"></span>
            <span class="up-7"></span>
            <span class="up-8"></span>
            <span class="up-9"></span>
            <span class="up-10"></span>
            <span class="up-11"></span>
            <span class="up-12"></span>
            <span class="up-13"></span>
            <span class="up-14"></span>
            <span class="up-15"></span>
            <span class="up-16"></span>
            <span class="up-17"></span>
            <span class="up-18"></span>
            <span class="up-19"></span>
            <span class="up-20"></span>
            </div>
        </div>
    </div>

</template>

<script>
// import '../../assets/css/mainfooter.scss'
export default {
    name: 'MainFooter'
}

</script>



<style scoped>


            @font-face {
    font-family: 'CookieRun-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

  * {
      font-family: 'CookieRun-Regular';
  }

html,body {
  background:#130560;
  overflow-x:hidden;
  overflow-y: hidden;
}
.seaContainer {
  position: absolute;
  top: 0%;
  left: 0%;
  width: 100%;
  height: 100%;
  background: #130560;
  overflow-x:hidden;
  overflow-y: hidden;
}

.ceo {

}
            
</style>