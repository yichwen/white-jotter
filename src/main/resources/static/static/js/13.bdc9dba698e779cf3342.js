webpackJsonp([13],{"3DH6":function(e,t){},xJsL:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var o={name:"Login",data:function(){return{rules:{username:[{required:!0,message:"用户名不能为空",trigger:"blur"}],password:[{required:!0,message:"密码不能为空",trigger:"blur"}]},loginForm:{username:"admin",password:"123456"},checked:!0,loading:!1}},methods:{login:function(){var e=this;this.$axios.post("/login",{username:this.loginForm.username,password:this.loginForm.password}).then(function(t){if(200===t.data.code){var r={username:e.loginForm.username,token:t.data.result};e.$store.commit("login",r);var o=e.$route.query.redirect;e.$router.replace({path:"/"===o||void 0===o?"/admin/dashboard":o})}}).catch(function(e){})}}},a={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("body",{attrs:{id:"paper"}},[r("el-form",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],staticClass:"login-container",attrs:{model:e.loginForm,rules:e.rules,"label-position":"left","label-width":"0px"}},[r("h3",{staticClass:"login-title"},[e._v("系统登录")]),e._v(" "),r("el-form-item",{attrs:{prop:"username"}},[r("el-input",{attrs:{type:"text","auto-complete":"off",placeholder:"账号"},model:{value:e.loginForm.username,callback:function(t){e.$set(e.loginForm,"username",t)},expression:"loginForm.username"}})],1),e._v(" "),r("el-form-item",{attrs:{prop:"password"}},[r("el-input",{attrs:{type:"password","auto-complete":"off",placeholder:"密码"},model:{value:e.loginForm.password,callback:function(t){e.$set(e.loginForm,"password",t)},expression:"loginForm.password"}})],1),e._v(" "),r("el-checkbox",{staticClass:"login_remember",attrs:{"label-position":"left"},model:{value:e.checked,callback:function(t){e.checked=t},expression:"checked"}},[r("span",[e._v("记住密码")])]),e._v(" "),r("el-form-item",{staticStyle:{width:"100%"}},[r("el-button",{staticStyle:{width:"40%",background:"#505458",border:"none"},attrs:{type:"primary"},on:{click:e.login}},[e._v("登录")]),e._v(" "),r("router-link",{attrs:{to:"register"}},[r("el-button",{staticStyle:{width:"40%",background:"#505458",border:"none"},attrs:{type:"primary"}},[e._v("注册")])],1)],1)],1)],1)},staticRenderFns:[]};var n=r("VU/8")(o,a,!1,function(e){r("3DH6")},null,null);t.default=n.exports}});
//# sourceMappingURL=13.bdc9dba698e779cf3342.js.map