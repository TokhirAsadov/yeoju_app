/*! For license information please see 8486.d1b7388e.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["8486"],{17171:function(e,n,t){"use strict";var o=t("5751");Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var l=o(t("64938")),i=t("85893"),a=(0,l.default)((0,i.jsx)("path",{d:"M19 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.11 0 2-.9 2-2V5c0-1.1-.89-2-2-2zm-9 14-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"}),"CheckBox");n.default=a},39628:function(e,n,t){"use strict";var o=t("5751");Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var l=o(t("64938")),i=t("85893"),a=(0,l.default)((0,i.jsx)("path",{d:"M19 5v14H5V5h14m0-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2z"}),"CheckBoxOutlineBlank");n.default=a},93162:function(e,n,t){var o,l;e=t.nmd(e),o=this,l=function(){"use strict";function n(e,n,t){var o=new XMLHttpRequest;o.open("GET",e),o.responseType="blob",o.onload=function(){r(o.response,n,t)},o.onerror=function(){console.error("could not download file")},o.send()}function o(e){var n=new XMLHttpRequest;n.open("HEAD",e,!1);try{n.send()}catch(e){}return 200<=n.status&&299>=n.status}function l(e){try{e.dispatchEvent(new MouseEvent("click"))}catch(t){var n=document.createEvent("MouseEvents");n.initMouseEvent("click",!0,!0,window,0,0,0,80,20,!1,!1,!1,!1,0,null),e.dispatchEvent(n)}}var i="object"==typeof window&&window.window===window?window:"object"==typeof self&&self.self===self?self:"object"==typeof t.g&&t.g.global===t.g?t.g:void 0,a=i.navigator&&/Macintosh/.test(navigator.userAgent)&&/AppleWebKit/.test(navigator.userAgent)&&!/Safari/.test(navigator.userAgent),r=i.saveAs||("object"!=typeof window||window!==i?function(){}:"download"in HTMLAnchorElement.prototype&&!a?function(e,t,a){var r=i.URL||i.webkitURL,d=document.createElement("a");t=t||e.name||"download",d.download=t,d.rel="noopener","string"==typeof e?(d.href=e,d.origin===location.origin?l(d):o(d.href)?n(e,t,a):l(d,d.target="_blank")):(d.href=r.createObjectURL(e),setTimeout(function(){r.revokeObjectURL(d.href)},4e4),setTimeout(function(){l(d)},0))}:"msSaveOrOpenBlob"in navigator?function(e,t,i){if(t=t||e.name||"download","string"!=typeof e){var a,r;navigator.msSaveOrOpenBlob((a=e,void 0===(r=i)?r={autoBom:!1}:"object"!=typeof r&&(console.warn("Deprecated: Expected third argument to be a object"),r={autoBom:!r}),r.autoBom&&/^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i.test(a.type)?new Blob(["\uFEFF",a],{type:a.type}):a),t)}else if(o(e))n(e,t,i);else{var d=document.createElement("a");d.href=e,d.target="_blank",setTimeout(function(){l(d)})}}:function(e,t,o,l){if((l=l||open("","_blank"))&&(l.document.title=l.document.body.innerText="downloading..."),"string"==typeof e)return n(e,t,o);var r="application/octet-stream"===e.type,d=/constructor/i.test(i.HTMLElement)||i.safari,s=/CriOS\/[\d]+/.test(navigator.userAgent);if((s||r&&d||a)&&"undefined"!=typeof FileReader){var u=new FileReader;u.onloadend=function(){var e=u.result;e=s?e:e.replace(/^data:[^;]*;/,"data:attachment/file;"),l?l.location.href=e:location=e,l=null},u.readAsDataURL(e)}else{var c=i.URL||i.webkitURL,p=c.createObjectURL(e);l?l.location=p:location.href=p,l=null,setTimeout(function(){c.revokeObjectURL(p)},4e4)}});i.saveAs=r.saveAs=r,void 0!==e&&(e.exports=r)},"function"==typeof define&&define.amd?define([],l):void 0!==n?l():(l(),o.FileSaver={})},71368:function(e,n,t){"use strict";t.r(n),t.d(n,{default:function(){return J}});var o=t("7409"),l=t("99282"),i=t("38956"),a=t("58865");t("69826"),t("31672"),t("59461"),t("2490"),t("41539"),t("92222"),t("75505"),t("87714"),t("82801"),t("1174"),t("82472"),t("48675"),t("92990"),t("18927"),t("33105"),t("35035"),t("74345"),t("7174"),t("63408"),t("14590"),t("32846"),t("44731"),t("77209"),t("96319"),t("58867"),t("37789"),t("33739"),t("29368"),t("14483"),t("12056"),t("3462"),t("30678"),t("27462"),t("33824"),t("12974"),t("1439"),t("87585"),t("15016"),t("55315"),t("66992"),t("23252"),t("6461"),t("32372"),t("8922"),t("5835"),t("8970"),t("84444"),t("26833"),t("68216"),t("79433");var r=t("85893"),d=t("67294"),s=t.n(d),u=t("71893"),c=t("55693"),p=t("52861"),f=t("28685"),v=t("27233"),m=t("72132"),h=t("8193"),g=t("15729"),x=t("7701"),b=t("60134"),w=t("93162"),y=t.n(w),j=t("71632"),_=t("97367"),N=t("79352"),C=t("90650"),S=t("16356"),k=t("63457"),A=t("50533"),E=t("94718"),R=t("47516"),L=t("89583");function O(){var e=(0,a._)(["\n    width: 100%;\n    display: flex;\n    gap: 10px;\n    margin: 5px !important;\n"]);return O=function(){return e},e}function U(){var e=(0,a._)(["\n    margin-top: 5px;\n    float: ",";\n    width: ",";\n    display: flex;\n    align-items: center;\n    justify-content: flex-start;\n    color: #ffffff;\n    gap: 10px;\n    background-color: ",";\n    padding: ",";\n    font-size: 16px;\n    border: none;\n    border-radius: 8px;\n    cursor: pointer;\n    transition: all 0.2s ease;\n    letter-spacing: 1.2px;\n\n    &:focus {\n        transform: scale(0.95);\n    }\n"]);return U=function(){return e},e}function T(){var e=(0,a._)(["\n    width: 30px;\n    height: 30px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    background-color: ",";\n    border-radius: 50%;\n    color: white;\n    font-size: 26px;\n    border: none;\n    position: absolute;\n    top: 10px;\n    right: 10px;\n"]);return T=function(){return e},e}function B(){var e=(0,a._)(["\n    ","\n    ","\n    ",""]);return B=function(){return e},e}function F(){var e=(0,a._)(["\n    width: 250px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    font-size: 14px;\n    cursor: pointer;\n    background-color: ",";\n    color: white;\n    padding: 20px 10px !important;\n    border-radius: 8px;\n\n\n    & > input:disabled {\n        cursor: not-allowed;\n    }\n"]);return F=function(){return e},e}function I(){var e=(0,a._)(["\n    width: 250px;\n    align-self: start;\n    border: 1px solid ",";\n    background-color: #fff;\n    color: ",";\n    border-radius: 0.5rem;\n    padding: 20px 10px !important;\n    letter-spacing: 1.1px;\n\n    &:hover {\n        background-color: ",";\n        color: white;\n    }\n\n    &:disabled {\n        cursor: not-allowed;\n    }\n"]);return I=function(){return e},e}function D(){var e=(0,a._)(["\n    display: flex;\n"]);return D=function(){return e},e}function M(){var e=(0,a._)(["\n    display: flex;\n    gap: 20px;\n    flex-wrap: wrap;\n    margin-bottom: 20px;\n    ","\n"]);return M=function(){return e},e}function P(){var e=(0,a._)(["\n    padding: 10px !important;\n"]);return P=function(){return e},e}var z=u.default.div(O()),H=u.default.button(U(),function(e){return e.float},function(e){return e.width?e.width:"100%"},function(e){return e.bg},function(e){return e.padding?e.padding:"5px 10px"}),G=(0,u.default)(C.motion.button)(T(),f.mainColor),V={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:1100,padding:"20px",bgcolor:"background.paper",boxShadow:24,borderRadius:3,p:4},W=u.default.div(B(),(0,j.medium)({width:"97vw !important",height:"97vh !important"}),(0,j.small)({width:"97vw !important",height:"97vh !important"}),(0,j.extrasmall)({width:"97vw !important",height:"97vh !important"}));u.default.label(F(),f.mainColor),u.default.button(I(),f.mainColor,f.mainColor,f.mainColor);var q=u.default.div(D()),K=u.default.div(M(),(0,j.extrasmall)({justifyContent:"center"})),X=u.default.div(P()),J=function(){var e=(0,i._)((0,d.useState)(!1),2),n=e[0],t=e[1],a=(0,i._)(s().useState(!1),2),u=a[0],w=a[1],j=(0,i._)((0,d.useState)(!0),2),C=j[0],O=j[1],U=(0,i._)((0,d.useState)(!1),2),T=(U[0],U[1]),B=(0,f.getHeaders)().headers,F=[{field:"id",headerName:"ID",width:40,editable:!1},{field:"fullName",headerName:"Full Name",type:"string",width:300,editable:!1},{field:"lastName",headerName:"Last Name",type:"string",width:200,editable:!1},{field:"firstName",headerName:"First Name",type:"string",width:200,editable:!1},{field:"middleName",headerName:"Middle Name",type:"string",width:200,editable:!1},{field:"login",headerName:"Login",type:"string",width:150,editable:!1},{field:"rfid",headerName:"RFID",type:"string",width:150,editable:!1},{field:"passport",headerName:"Passport",type:"string",width:120,editable:!1},{field:"password",headerName:"Password",type:"string",width:100,editable:!1},{field:"gander",headerName:"Gander",type:"string",width:100,editable:!1},{field:"state",headerName:"State",type:"string",width:240,editable:!1,renderCell:function(e){return(0,r.jsxs)(z,{children:[(0,r.jsxs)(H,{bg:"blue",variant:"contained",onClick:function(n){n.preventDefault(),en(e.id)},children:[(0,r.jsx)(L.FaEdit,{}),"Update"]}),(0,r.jsxs)(H,{bg:"red",variant:"contained",color:"danger",onClick:function(n){var t,o;n.preventDefault(),et(e.id,null==e?void 0:null===(t=e.row)||void 0===t?void 0:t.firstName,null==e?void 0:null===(o=e.row)||void 0===o?void 0:o.lastName)},children:[(0,r.jsx)(L.FaTrash,{}),"Delete"]})]})}}],I=(0,i._)((0,d.useState)([{id:1,name:"Tohir Asadov",state:"123456"},{id:2,name:"Tohir Asadov",state:"123456"}]),2),D=I[0],M=I[1],P=(0,i._)((0,d.useState)({}),2),J=P[0],Q=P[1],Y=(0,i._)((0,d.useState)(!1),2),Z=Y[0],$=Y[1],ee=(0,A.useSelector)(function(e){var n;return null==e?void 0:null===(n=e.user)||void 0===n?void 0:n.user}),en=function(e){var n=null==D?void 0:D.find(function(n){return(null==n?void 0:n.id)===e});Q(function(e){var t,i,a;return(0,l._)((0,o._)({},e),{id:null==n?void 0:n.id,firstName:null==n?void 0:n.firstName,lastName:null==n?void 0:n.lastName,middleName:null==n?void 0:n.middleName,login:null==n?void 0:n.login,rfid:null==n?void 0:n.rfid,passport:null==n?void 0:n.passport,password:null==n?void 0:n.password,gander:(null==n?void 0:n.ganderId)===1?"MALE":"FEMALE",roles:null==n?void 0:n.roles,positions:null==n?void 0:n.positions,address:null==n?void 0:null===(t=n.address)||void 0===t?void 0:t.address,region:null==n?void 0:null===(i=n.address)||void 0===i?void 0:i.region,district:null==n?void 0:null===(a=n.address)||void 0===a?void 0:a.district})}),$(!0),console.log(n,"get item--------------------------------------")},et=function(e,n,t){window.confirm("Do you want to delete ".concat(t," ").concat(n,"?"))&&(v.default.delete("".concat(f.BASE_URL,"/user/deleteUser/").concat(e),{headers:B}).then(function(e){var n;m.toast.success(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.message,{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"})}).catch(function(e){console.log(e)}),m.toast.success("Deleted successfully",{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"}),ep(""),M([]))},eo=function(){for(var e=b.USERINFO_BASE64,n=atob(e),t=n.length,o=Math.ceil(t/1024),l=Array(o),i=0;i<o;++i){for(var a=1024*i,r=Math.min(a+1024,t),d=Array(r-a),s=a,u=0;s<r;u++,s++)d[u]=n[s].charCodeAt(0);l[i]=new Uint8Array(d)}var c=new Blob(l,{type:"application/vnd.ms-excel"});y().saveAs(new Blob([c],{}),"USERINFO.xlsx")},el=function(){t(!1),$(!1)},ei=function(e){Object.values(e).every(function(e){return null!==e&&e})?v.default.post(f.BASE_URL+"/admin/saveUser",(0,o._)({},e),{headers:B}).then(function(e){var n;$(!1),m.toast.success(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.message)}).catch(function(e){var n,t;m.toast.error(null==e?void 0:null===(t=e.response)||void 0===t?void 0:null===(n=t.data)||void 0===n?void 0:n.message)}):m.toast.warning("Empty eny field..")},ea=function(e){delete e.id,console.log(Object.values(e).every(function(e){return null!==e&&e}),"valed"),console.log((0,l._)((0,o._)({},e),{id:null}),"submit form"),Object.values(e).every(function(e){return null!==e&&e})?v.default.post(f.BASE_URL+"/admin/saveUser",(0,o._)({},e),{headers:B}).then(function(e){var n;t(!1),m.toast.success(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.message)}).catch(function(e){var n,t;m.toast.error(null==e?void 0:null===(t=e.response)||void 0===t?void 0:null===(n=t.data)||void 0===n?void 0:n.message)}):m.toast.warning("Empty eny field..")},er=(0,i._)((0,d.useState)(null),2),ed=er[0],es=er[1],eu=(0,i._)((0,d.useState)(""),2),ec=eu[0],ep=eu[1];return(0,d.useEffect)(function(){console.log(J,"edit user")},[J]),(0,d.useEffect)(function(){(null==ec?void 0:ec.length)>4&&v.default.get("".concat(f.BASE_URL,"/admin/param?param=").concat(ec)).then(function(e){var n;M(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.obj),O(!1)}).catch(function(e){console.log(e)})},[ec]),(0,d.useEffect)(function(){v.default.get(f.BASE_URL+"/admin/userAdd?id="+(null==ee?void 0:ee.id)).then(function(e){es(null==e?void 0:e.data)}).catch(function(e){console.log(e)})},[]),(0,r.jsxs)(X,{children:[(0,r.jsxs)(K,{children:[(0,r.jsxs)(E.default,{variant:"contained",component:"label",endIcon:(0,r.jsx)(h.AiOutlineCloudUpload,{}),children:["Import USERS from file",(0,r.jsx)("input",{onChange:function(e){var n=f.BASE_URL+"/userinfo/uploadUserInfoAndUser",t=new FormData;t.append("file",e.target.files[0]);var o=localStorage.getItem(f.TOKEN),l={headers:{"Content-Type":"multipart/form-data",Authorization:f.TokenType+o,"Access-Control-Allow-Origin":"*"}};T(!0),w(!0),v.default.post(n,t,l).then(function(e){var n;m.toast.success(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.message,{position:"top-right",autoClose:5e3,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"}),w(!1),T(!1)}).catch(function(e){var n,t;w(!1),T(!1),m.toast.error(null==e?void 0:null===(t=e.response)||void 0===t?void 0:null===(n=t.data)||void 0===n?void 0:n.message,{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"})})},type:"file",hidden:!0,accept:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"})]}),(0,r.jsx)(E.default,{endIcon:(0,r.jsx)(h.AiOutlineCloudDownload,{}),variant:"contained",onClick:function(){return eo()},children:"Export userinfo from file example"}),(0,r.jsx)(E.default,{variant:"contained",color:"success",endIcon:(0,r.jsx)(R.BiSolidUserPlus,{}),onClick:function(){return t(!n)},children:"Create USER"})]}),(0,r.jsx)(q,{children:(0,r.jsx)(p.default,{id:(null==ec?void 0:ec.length)>4?"outlined-basic":"standard-error-helper-text",error:(null==ec?void 0:ec.length)>0&&(null==ec?void 0:ec.length)<5,label:"Search user..",variant:"outlined",value:ec,sx:{width:"250px",marginBottom:"10px"," @media only screen and  (max-width: 576px)":{width:300}},helperText:(null==ec?void 0:ec.length)>0&&(null==ec?void 0:ec.length)<5&&"Enter a word longer than 4",onChange:function(e){var n;e.preventDefault(),ep(null==e?void 0:null===(n=e.target)||void 0===n?void 0:n.value)}})}),(0,r.jsx)(g.default,{sx:{color:"#fff",zIndex:function(e){return e.zIndex.drawer+1}},open:u,children:(0,r.jsx)(x.default,{color:"inherit"})}),C?(0,r.jsx)("div",{}):(0,r.jsx)(S.DataGrid,{checkboxSelection:!0,style:{width:"500px!important",minHeight:"300px!important"},columns:F,rows:D,components:{Toolbar:S.GridToolbar},autoHeight:!0,pageSize:50,initialState:(0,l._)((0,o._)({},D.initialState),{columns:{columnVisibilityModel:{id:!1,fullName:!1,password:!1,gander:!1}}})}),(0,r.jsx)(_.default,{open:n,onClose:el,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,r.jsxs)(c.default,{sx:V,component:W,children:[(0,r.jsx)(G,{onClick:el,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,r.jsx)(N.RiCloseLine,{})}),(0,r.jsx)(k.default,{title:(null==J?void 0:J.id)!==null?"Edit Teacher":"Add Teacher",roles:null==ed?void 0:ed.roles,positions:null==ed?void 0:ed.positions,regions:null==ed?void 0:ed.regions,districts:null==ed?void 0:ed.districts,formArr:[{label:"First Name",name:"firstName",placeholder:"First Name",type:"text"},{label:"Last Name",name:"lastName",placeholder:"Last Name",type:"text"},{label:"Middle Name",name:"middleName",placeholder:"Middle Name",type:"text"},{label:"RFID",name:"rfid",placeholder:"RFID",type:"text"},{label:"Login",name:"login",placeholder:"Login",type:"text"},{label:"Passport",name:"passport",placeholder:"Passport",type:"text"},{label:"Password",name:"password",placeholder:"Password",type:"text"},{label:"Gander",name:"gander",placeholder:"Gander",type:"select"},{label:"Region",name:"region",placeholder:"Region",type:"select"},{label:"District",name:"district",placeholder:"District",type:"select"},{label:"Address",name:"address",placeholder:"Address",type:"text"},{label:"Roles",name:"roles",placeholder:"Roles",type:"multi"},{label:"Positions",name:"positions",placeholder:"Positions",type:"multi"}],submitBtn:"Save",onSubmit:function(e){return ea(e)}})]})}),(0,r.jsx)(_.default,{open:Z,onClose:el,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,r.jsxs)(c.default,{sx:V,component:W,children:[(0,r.jsx)(G,{onClick:el,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,r.jsx)(N.RiCloseLine,{})}),(0,r.jsx)(k.default,{title:(null==J?void 0:J.id)!==null?"Edit Teacher":"Add Teacher",roles:null==ed?void 0:ed.roles,positions:null==ed?void 0:ed.positions,regions:null==ed?void 0:ed.regions,districts:null==ed?void 0:ed.districts,formArr:[{label:"First Name",name:"firstName",placeholder:"First Name",type:"text"},{label:"Last Name",name:"lastName",placeholder:"Last Name",type:"text"},{label:"Middle Name",name:"middleName",placeholder:"Middle Name",type:"text"},{label:"RFID",name:"rfid",placeholder:"RFID",type:"text"},{label:"Login",name:"login",placeholder:"Login",type:"text"},{label:"Passport",name:"passport",placeholder:"Passport",type:"text"},{label:"Password",name:"password",placeholder:"Password",type:"text"},{label:"Gander",name:"gander",placeholder:"Gander",type:"select"},{label:"Region",name:"region",placeholder:"Region",type:"select"},{label:"District",name:"district",placeholder:"District",type:"select"},{label:"Address",name:"address",placeholder:"Address",type:"text"},{label:"Roles",name:"roles",placeholder:"Roles",type:"multi"},{label:"Positions",name:"positions",placeholder:"Positions",type:"multi"}],submitBtn:(null==J?void 0:J.id)!==null&&(null==J?void 0:J.id)?"Update":"Save",onSubmit:function(e){return(null==J?void 0:J.id)!==null&&(null==J?void 0:J.id)?ei(e):ea(e)},updateItem:J})]})})]})}},63457:function(e,n,t){"use strict";t.r(n),t.d(n,{default:function(){return P}});var o=t("27412"),l=t("7409"),i=t("99282"),a=t("38956"),r=t("58865");t("85827"),t("25387"),t("72608"),t("2490"),t("41539"),t("21249"),t("57640"),t("9924"),t("57327"),t("88449"),t("19894"),t("23157");var d=t("85893"),s=t("67294"),u=t("71893"),c=t("39711"),p=t("28685"),f=t("52861"),v=t("32392"),m=t("55693"),h=t("42154"),g=t("7104"),x=t("61261"),b=t("15352"),w=t("44118"),y=t("39628"),j=t("17171"),_=t("71632"),N=t("94718"),C=t("89583");function S(){var e=(0,r._)(["\n    height: 380px;\n    padding-top: 10px;\n    display: grid;\n    overflow-y: scroll;\n    grid-template-columns: repeat(4, 1fr);\n    gap: 30px;\n    ","\n    ","\n    ","\n"]);return S=function(){return e},e}function k(){var e=(0,r._)(["\n    width: 100%;\n    color: ",";\n"]);return k=function(){return e},e}function A(){var e=(0,r._)(["\n    font-size: 12px;\n    width: 100%;\n    display: flex;\n    justify-content: center;\n    margin-top: 10px !important;\n"]);return A=function(){return e},e}function E(){var e=(0,r._)(["\n\n"]);return E=function(){return e},e}function R(){var e=(0,r._)(["\n    color: ",";\n    margin-bottom: 20px !important;\n"]);return R=function(){return e},e}function L(){var e=(0,r._)(["\n    text-decoration: none;\n    color: blue;\n"]);return L=function(){return e},e}var O=(0,d.jsx)(y.default,{fontSize:"small"}),U=(0,d.jsx)(j.default,{fontSize:"small"}),T=u.default.div(S(),(0,_.medium)({height:"75vh",gridTemplateColumns:"1fr",gap:"15px"}),(0,_.small)({height:"75vh",gridTemplateColumns:"1fr",gap:"15px"}),(0,_.extrasmall)({height:"75vh",gridTemplateColumns:"1fr",gap:"15px"})),B=u.default.form(k(),p.mainColor),F=u.default.div(A()),I=u.default.span(E()),D=u.default.h3(R(),p.mainColor),M=(0,u.default)(c.Link)(L()),P=function(e){var n=e.title,t=e.formArr,r=e.submitBtn,u=e.onSubmit,c=e.updateItem,p=e.roles,y=e.positions,j=e.regions,_=e.districts,S=e.redirect,k=(0,a._)((0,s.useState)(t.reduce(function(e,n){return"roles"!==n.name&&"positions"!==n.name?(0,i._)((0,l._)({},e),(0,o._)({},n.name,"")):(0,i._)((0,l._)({},e),(0,o._)({},n.name,[]))},{})),2),A=k[0],E=k[1],R=function(){u(L(A))};(0,s.useEffect)(function(){E((0,i._)((0,l._)({},A),{id:null==c?void 0:c.id,firstName:null==c?void 0:c.firstName,lastName:null==c?void 0:c.lastName,middleName:null==c?void 0:c.middleName,login:null==c?void 0:c.login,rfid:null==c?void 0:c.rfid,passport:null==c?void 0:c.passport,password:null==c?void 0:c.password,gander:null==c?void 0:c.gander,roles:null==c?void 0:c.roles,positions:null==c?void 0:c.positions,address:null==c?void 0:c.address,region:null==c?void 0:c.region,district:null==c?void 0:c.district}))},[]),(0,s.useEffect)(function(){console.log(A,"form change")},[A]);var L=function(e){return{id:null==e?void 0:e.id,firstName:null==e?void 0:e.firstName,lastName:null==e?void 0:e.lastName,middleName:null==e?void 0:e.middleName,rfid:null==e?void 0:e.rfid,login:null==e?void 0:e.login,passport:null==e?void 0:e.passport,password:null==e?void 0:e.password,gander:null==e?void 0:e.gander,region:null==e?void 0:e.region,district:null==e?void 0:e.district,address:null==e?void 0:e.address,roles:null==e?void 0:e.roles,positions:null==e?void 0:e.positions}};return L(A),(0,d.jsxs)(B,{children:[(0,d.jsx)(D,{children:n}),(0,d.jsx)(T,{children:null==t?void 0:t.map(function(e,n){var t,a,r=e.label,s=e.name,u=e.placeholder,m=e.type;return(e.value,"text"===m)?(0,d.jsx)(d.Fragment,{children:(0,d.jsx)(f.default,{label:r,variant:"outlined",disabled:"id"===s,id:s,name:s,type:m,value:A[s],onChange:function(e){return E(function(n){var t;return(0,i._)((0,l._)({},n),(0,o._)({},s,null==e?void 0:null===(t=e.target)||void 0===t?void 0:t.value))})},placeholder:u},n)}):"select"===m?(0,d.jsxs)(h.default,{fullWidth:!0,children:[(0,d.jsx)(v.default,{id:"demo-simple-select-label",children:r}),(0,d.jsx)(g.Select,{labelId:"demo-simple-select-label",id:"demo-simple-select",name:s,value:A[s],label:r,onChange:function(e){return E(function(n){var t;return(0,i._)((0,l._)({},n),(0,o._)({},s,null==e?void 0:null===(t=e.target)||void 0===t?void 0:t.value))})},children:"gander"===s?(t=["MALE","FEMALE"],t.map(function(e){return(0,d.jsx)(x.default,{value:e,children:e},e)})):"region"===s?null==j?void 0:j.map(function(e){return(0,d.jsx)(x.default,{value:null==e?void 0:e.nameUz,children:null==e?void 0:e.nameUz},null==e?void 0:e.id)}):null==_?void 0:null===(a=_.filter(function(e){var n;return(null==e?void 0:null===(n=e.region)||void 0===n?void 0:n.nameUz)===(null==A?void 0:A.region)}))||void 0===a?void 0:a.map(function(e){return(0,d.jsx)(x.default,{value:null==e?void 0:e.nameUz,children:null==e?void 0:e.nameUz},null==e?void 0:e.id)})})]}):"multi"===m?c?(0,d.jsx)(w.default,{multiple:!0,id:"checkboxes-tags-demo",options:"roles"===s?p:y,disableCloseOnSelect:!0,getOptionLabel:function(e){return e},value:"roles"===s?null==A?void 0:A.roles:null==A?void 0:A.positions,onChange:function(e,n){E(function(e){return(0,i._)((0,l._)({},e),(0,o._)({},s,n))})},renderOption:function(e,n,t){var o=t.selected;return(0,d.jsxs)("li",(0,i._)((0,l._)({},e),{children:[(0,d.jsx)(b.default,{icon:O,checkedIcon:U,style:{marginRight:8},checked:o}),n]}))},renderInput:function(e){return(0,d.jsx)(f.default,(0,i._)((0,l._)({},e),{label:r,placeholder:r}))}}):(0,d.jsx)(w.default,{multiple:!0,id:"checkboxes-tags-demo",options:"roles"===s?p:y,disableCloseOnSelect:!0,getOptionLabel:function(e){return e},onChange:function(e,n){E(function(e){return(0,i._)((0,l._)({},e),(0,o._)({},s,n))})},renderOption:function(e,n,t){var o=t.selected;return(0,d.jsxs)("li",(0,i._)((0,l._)({},e),{children:[(0,d.jsx)(b.default,{icon:O,checkedIcon:U,style:{marginRight:8},checked:o}),n]}))},renderInput:function(e){return(0,d.jsx)(f.default,(0,i._)((0,l._)({},e),{label:r,placeholder:r}))}}):void 0})}),(0,d.jsx)(m.default,{sx:{mt:2,display:"flex",justifyContent:"end"},children:(0,d.jsx)(N.default,{variant:"contained",endIcon:n.startsWith("UPDATE")?(0,d.jsx)(C.FaEdit,{}):(0,d.jsx)(C.FaSave,{}),onClick:function(e){e.preventDefault(),R()},children:r})}),!!S&&(0,d.jsxs)(F,{children:[(0,d.jsxs)(I,{children:[S.label,"\xa0"]}),(0,d.jsx)(M,{to:S.link.to,children:S.link.label})]})]})}}}]);