/*! For license information please see 1961.832b07e6.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["1961"],{17171:function(e,n,t){"use strict";var l=t("5751");Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var i=l(t("64938")),o=t("85893"),a=(0,i.default)((0,o.jsx)("path",{d:"M19 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.11 0 2-.9 2-2V5c0-1.1-.89-2-2-2zm-9 14-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"}),"CheckBox");n.default=a},39628:function(e,n,t){"use strict";var l=t("5751");Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var i=l(t("64938")),o=t("85893"),a=(0,i.default)((0,o.jsx)("path",{d:"M19 5v14H5V5h14m0-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2z"}),"CheckBoxOutlineBlank");n.default=a},55923:function(e,n,t){"use strict";t.r(n);var l=t("58865");t("25387"),t("72608"),t("2490"),t("33948"),t("57640"),t("9924"),t("10072"),t("23042"),t("99137"),t("71957"),t("96306"),t("103"),t("74592"),t("58276"),t("35082"),t("12813"),t("18222"),t("38563"),t("50336"),t("7512");var i=t("85893"),o=t("67294"),a=t("71893"),r=t("39711"),d=t("16393"),u=t("23157"),s=t("41931"),c=t("27233"),f=t("50533"),p=t("89583");function h(){let e=(0,l._)(["\n  width: 100%;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  flex-direction: column;\n  background-color: #fff;\n  height: 400px;\n  border-radius: 10px;\n  color: ",";\n"]);return h=function(){return e},e}function v(){let e=(0,l._)(["\n  display: flex;\n  flex-direction: column;\n  gap: 20px;\n  width: 90%;\n   \n"]);return v=function(){return e},e}function m(){let e=(0,l._)(["\n  display: flex;\n  justify-content: space-between;\n  & > div{\n    width: 250px!important;\n  }\n"]);return m=function(){return e},e}function x(){let e=(0,l._)(["\n  border: 1px solid ",";\n  height: 40px;\n  width: 200px;\n  padding-left: 10px!important;\n  border-radius: 5px;\n  display: ",";\n  \n  &::placeholder{\n    font-size: 14px;\n    letter-spacing: 1.1px;\n    color: ",";\n  }\n  &:focus{\n    outline: none;\n  }\n"]);return x=function(){return e},e}function g(){let e=(0,l._)(["\n  padding: 8px 55px;\n  margin-top: 25px!important;\n  background-color: ",";\n  color: white;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  gap: 10px;\n  font-size: 16px;\n  letter-spacing: 0.7px;\n  font-weight: 400;\n  cursor: pointer;\n  transition: all ease 0.4s;\n  border: none;\n  border-radius: 5px;\n  \n  &:focus{\n    transform: scale(0.9);\n  }\n"]);return g=function(){return e},e}function b(){let e=(0,l._)(["\n  font-size: 20px;\n"]);return b=function(){return e},e}function j(){let e=(0,l._)(["\n  font-size: 12px;\n  width: 100%;\n  display: flex;\n  justify-content: center;\n  margin-top: 10px!important;\n"]);return j=function(){return e},e}function w(){let e=(0,l._)(["\n\n"]);return w=function(){return e},e}function S(){let e=(0,l._)(["\n  color: ",";\n  margin-bottom: 20px!important;\n"]);return S=function(){return e},e}function y(){let e=(0,l._)(["\n  text-decoration: none;\n  color: blue;\n"]);return y=function(){return e},e}let k=(0,s.default)(),C=e=>e.reduce((e,n)=>"lessonDtos"!==n.name?{...e,[n.name]:""}:{...e,[n.name]:[]},{}),_=a.default.form(h(),d.mainColor),I=a.default.div(v()),N=a.default.div(m()),D=a.default.input(x(),d.mainColor,e=>e.display?"none":"block",d.mainColor),T=a.default.button(g(),d.mainColor),F=a.default.label(b()),R=a.default.div(j()),z=a.default.span(w()),A=a.default.h3(S(),d.mainColor),E=(0,a.default)(r.Link)(y());n.default=e=>{let{title:n,formArr:t,submitBtn:l,onSubmit:a,updateItem:r,redirect:s}=e,[h,v]=(0,o.useState)([]),[m,x]=(0,o.useState)([]),[g,b]=(0,o.useState)([]),[j,w]=(0,o.useState)([]),[S,y]=(0,o.useState)([]),[B,L]=(0,o.useState)(C(t)),M=(0,f.useSelector)(e=>{var n;return null==e?void 0:null===(n=e.section)||void 0===n?void 0:n.section}),U=(e,n)=>{L(t=>({...t,[n]:e}))},H=(e,n)=>{L(t=>({...t,[n]:e.target.value}))},O=()=>{a(W(B))},{headers:P}=(0,d.getHeaders)();(0,o.useEffect)(()=>{L({...B,id:null==r?void 0:r.id,userId:null==r?void 0:r.userId,kafedraId:null==M?void 0:M.id,lessonDtos:null==r?void 0:r.lessonDtos}),c.default.get(d.BASE_URL+"/user/getUserForTeacherSaving",{headers:P}).then(e=>{var n,t,l,i,o,a,r,d,u,s,c;v(null==e?void 0:null===(t=e.data)||void 0===t?void 0:null===(n=t.obj)||void 0===n?void 0:n.users),x(null==e?void 0:null===(i=e.data)||void 0===i?void 0:null===(l=i.obj)||void 0===l?void 0:l.kafedras),b(null==e?void 0:null===(a=e.data)||void 0===a?void 0:null===(o=a.obj)||void 0===o?void 0:o.subjects),w(null==e?void 0:null===(d=e.data)||void 0===d?void 0:null===(r=d.obj)||void 0===r?void 0:r.positions),y(null==e?void 0:null===(c=e.data)||void 0===c?void 0:null===(s=c.obj)||void 0===s?void 0:null===(u=s.workerStatus)||void 0===u?void 0:u.map((e,n)=>({value:n,label:e})))}).catch(e=>{})},[]);let Y=(e,n)=>{e.length>4&&"userId"===n&&c.default.get(d.BASE_URL+"/user/getUserForTeacherSavingSearch?keyword="+e,{headers:P}).then(e=>{var n;v(Array.from(new Map(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.obj.map(e=>[null==e?void 0:e.login,e])).values()))}).catch(e=>{})},W=e=>{var n,t,l,i;let o=null==e?void 0:null===(n=e.lessonDtos)||void 0===n?void 0:n.map(e=>{let{value:n,label:t}=e;return{id:n,name:t}});return{id:null==e?void 0:e.id,userId:null==e?void 0:null===(t=e.userId)||void 0===t?void 0:t.value,kafedraId:null==M?void 0:M.id,lessonDtos:o,workerStatus:null==e?void 0:null===(l=e.workerStatus)||void 0===l?void 0:l.label,positionId:null==e?void 0:null===(i=e.positionId)||void 0===i?void 0:i.value}};return W(B),(0,i.jsxs)(_,{children:[(0,i.jsx)(A,{children:n}),(0,i.jsx)(I,{children:null==t?void 0:t.map((e,n)=>{let{label:t,name:l,placeholder:o,type:a,value:r}=e;return"select"!==a?(0,i.jsx)(i.Fragment,{children:(0,i.jsx)(D,{display:"id"===l,id:l,name:l,type:a,value:B[l],onChange:e=>H(e,l),placeholder:o},n)}):(0,i.jsxs)(N,{children:[(0,i.jsx)(F,{htmlFor:l,children:t}),(0,i.jsx)(u.default,{width:"300px",closeMenuOnSelect:!0,components:k,isMulti:"lessonDtos"===l,options:"lessonDtos"===l?g:"userId"===l?h:"kafedraId"===l?m:"workerStatus"===l?S:j,onChange:e=>U(e,l),value:B[l],name:l,isClearable:!0,onInputChange:e=>Y(e,l),onFocus:()=>v([]),isSearchable:!0})]})})}),(0,i.jsxs)(T,{onClick:e=>{e.preventDefault(),O()},children:[n.startsWith("UPDATE")?(0,i.jsx)(p.FaEdit,{}):(0,i.jsx)(p.FaSave,{}),l]}),!!s&&(0,i.jsxs)(R,{children:[(0,i.jsxs)(z,{children:[s.label,"\xa0"]}),(0,i.jsx)(E,{to:s.link.to,children:s.link.label})]})]})}},20007:function(e,n,t){"use strict";t.r(n);var l=t("58865");t("33948"),t("57640"),t("9924"),t("31672"),t("59461"),t("2490");var i=t("85893"),o=t("67294"),a=t("71893"),r=t("16393"),d=t("55693"),u=t("79352"),s=t("97367"),c=t("55923"),f=t("27233"),p=t("72132"),h=t("53432"),v=t("16356"),m=t("94718"),x=t("61006"),g=t("6115"),b=t("50533"),j=t("89583"),w=t("86893"),S=t("44025"),y=t("29258"),k=t("52861"),C=t("42154"),_=t("32392"),I=t("61261"),N=t("15352"),D=t("44118"),T=t("39628"),F=t("17171"),R=t("63750"),z=t("55605"),A=t("90650"),E=t("57024"),B=t("51649"),L=t("39711");function M(){let e=(0,l._)(["\n    width: 97vw !important;\n    padding: 10px !important;\n"]);return M=function(){return e},e}function U(){let e=(0,l._)(["\n    ","\n    ","\n    ","\n"]);return U=function(){return e},e}function H(){let e=(0,l._)(["\n    width: 200px;\n    align-self: center;\n    border: 1px solid ",";\n    background-color: #fff;\n    color: ",";\n    border-radius: 0.5rem;\n    padding: 10px 15px !important;\n\n    &:hover {\n        background-color: ",";\n        color: white;\n    }\n"]);return H=function(){return e},e}function O(){let e=(0,l._)(["\n    width: 30px;\n    height: 30px;\n    background-color: #fff;\n    font-size: 20px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    border: 1px solid red;\n    border-radius: 0.5rem;\n    color: red;\n    padding: 5px !important;\n\n    &:hover {\n        transform: scale(1.1);\n    }\n\n"]);return O=function(){return e},e}function P(){let e=(0,l._)(["\n    width: 30px;\n    height: 30px;\n    background-color: #fff;\n    font-size: 20px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    border: 1px solid ",";\n    border-radius: 0.5rem;\n    color: ",";\n    padding: 5px !important;\n\n    &:hover {\n        transform: scale(1.1);\n    }\n\n"]);return P=function(){return e},e}function Y(){let e=(0,l._)(["\n    width: 30px;\n    height: 30px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    background-color: ",";\n    border-radius: 50%;\n    color: white;\n    font-size: 26px;\n    border: none;\n    position: absolute;\n    top: 10px;\n    right: 10px;\n"]);return Y=function(){return e},e}function W(){let e=(0,l._)(["\n    width: 100%;\n    display: flex;\n    gap: 10px;\n    margin: 5px !important;\n"]);return W=function(){return e},e}function G(){let e=(0,l._)(["\n    height: 390px;\n    padding-top: 10px;\n    display: grid;\n    overflow-y: scroll;\n    grid-template-columns: repeat(3, 1fr);\n    gap: 30px;\n    ","\n    ","\n    ","\n"]);return G=function(){return e},e}function V(){let e=(0,l._)(["\n    width: 100%;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    gap: 10px;\n    margin: 5px !important;\n"]);return V=function(){return e},e}function X(){let e=(0,l._)(["\n    width: 100%;\n    background-color: #fff;\n    border-radius: 10px;\n    display: flex;\n    align-items: center;\n    justify-content: space-between;\n\n    .MuiDataGrid-columnHeaderTitleContainer {\n        justify-content: center;\n    }\n"]);return X=function(){return e},e}function q(){let e=(0,l._)(["\n    display: flex;\n    flex-wrap: wrap;\n    gap: 20px;\n    ","\n"]);return q=function(){return e},e}function J(){let e=(0,l._)(["\n    margin: 20px 0;\n    width: 100%;\n    display: flex;\n    flex-wrap: wrap;\n    justify-content: space-between;\n    color: ",";\n"]);return J=function(){return e},e}function K(){let e=(0,l._)(["\n    width: 100%;\n    padding: 10px !important;\n"]);return K=function(){return e},e}function Q(){let e=(0,l._)(["\n    ","\n    ","\n    ","\n"]);return Q=function(){return e},e}function Z(){let e=(0,l._)(["\n    ","\n"]);return Z=function(){return e},e}let $=(0,i.jsx)(T.default,{fontSize:"small"}),ee=(0,i.jsx)(F.default,{fontSize:"small"}),en={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"80vw",minHeight:"70vh",bgcolor:"background.paper",boxShadow:24,borderRadius:3,p:4},et=a.default.div(M()),el={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"80vw",display:"flex",flexDirection:"column",gap:"20px",bgcolor:"background.paper",boxShadow:24,borderRadius:3,p:4},ei=a.default.div(U(),(0,E.medium)({width:"97vw !important",height:"97vh !important"}),(0,E.small)({width:"97vw !important",height:"97vh !important"}),(0,E.extrasmall)({width:"97vw !important",height:"97vh !important"}));a.default.button(H(),r.mainColor,r.mainColor,r.mainColor);let eo=a.default.button(O()),ea=a.default.button(P(),r.mainColor,r.mainColor),er=(0,a.default)(A.motion.button)(Y(),r.mainColor),ed=a.default.div(W()),eu=a.default.div(G(),(0,E.medium)({height:"100%",gridTemplateColumns:"1fr",gap:"15px"}),(0,E.small)({height:"100%",gridTemplateColumns:"1fr",gap:"15px"}),(0,E.extrasmall)({height:"100%",gridTemplateColumns:"1fr",gap:"15px"})),es=a.default.div(V()),ec=a.default.div(X()),ef=a.default.div(q(),(0,E.extrasmall)({margin:"20px 0",justifyContent:"center"})),ep=a.default.h3(J(),r.mainColor),eh=a.default.div(K()),ev={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:800,bgcolor:"background.paper",boxShadow:24,borderRadius:1,outline:"none",p:2},em=a.default.div(Q(),(0,E.medium)({width:"600px !important",padding:"20px 15px !important"}),(0,E.small)({width:"580px !important",padding:"20px 15px !important"}),(0,E.extrasmall)({width:"98vw !important",padding:"80px 10px !important"})),ex={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"90vw",minHeight:400,bgcolor:"background.paper",boxShadow:24,borderRadius:3,p:4},eg=a.default.div(Z(),(0,E.extrasmall)({width:"97vw !important",padding:"10px !important"})),eb={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"98vw",height:"98vh",overflowY:"scroll",bgcolor:"background.paper",borderRadius:3,boxShadow:24,positions:"relative"};n.default=()=>{let[e,n]=(0,o.useState)(!0),[t,l]=(0,o.useState)(!1),a=(0,L.useNavigate)(),T=()=>l(!0),F=()=>{l(!1),eB(!1)},[A,E]=(0,o.useState)(!1),M=(e,n,t)=>(X(e),Q(t),J(n),E(!0)),U=()=>{E(!1)},[H,O]=(0,o.useState)(!1),P=e=>{J(e),O(!0)},{headers:Y}=(0,r.getHeaders)(),[W,G]=(0,o.useState)({}),[V,X]=(0,o.useState)(""),[q,J]=(0,o.useState)(""),[K,Q]=(0,o.useState)(""),Z=[{field:"count",headerName:"\u2116",width:40,align:"center",editable:!1},{field:"fullName",headerName:"Full name",minWidth:300,flex:1,align:"left",editable:!1,renderCell:e=>{var n,t,l,o;return(0,i.jsxs)(ed,{children:[(null==e?void 0:null===(n=e.row)||void 0===n?void 0:n.photo)?(0,i.jsx)("img",{src:(null==e?void 0:null===(t=e.row)||void 0===t?void 0:t.photo)?r.BASE_URL+"/attachment/download/"+(null==e?void 0:null===(o=e.row)||void 0===o?void 0:null===(l=o.photo)||void 0===l?void 0:l.id):"",width:"40px",height:"40px",alt:null==e?void 0:e.value,style:{borderRadius:"50%"}}):(0,i.jsx)("span",{style:{display:"flex",alignItems:"center",justifyContent:"center",fontSize:"20px"},children:(0,i.jsx)(j.FaUserAlt,{})}),(0,i.jsx)("span",{style:{display:"flex",alignItems:"center",justifyContent:"center",fontSize:"14px"},children:(0,i.jsx)(m.default,{onClick:()=>{var n;return P(null==e?void 0:null===(n=e.row)||void 0===n?void 0:n.id)},children:null==e?void 0:e.value})})]})}},{field:"login",headerName:"Login",minWidth:100,flex:.4,align:"center",editable:!1},{field:"email",headerName:"Email",minWidth:200,flex:.7,align:"center",editable:!1},{field:"rfid",headerName:"RFID",minWidth:120,flex:.5,align:"center",editable:!1},{field:"passport",headerName:"Passport",minWidth:130,flex:.5,align:"center",editable:!1},{field:"articles",headerName:"Scientific articles",minWidth:140,flex:1,align:"center",editable:!1},{field:"countTouch",headerName:"Statistics",minWidth:100,flex:.5,editable:!1,renderCell:e=>(0,i.jsx)(es,{children:(0,i.jsxs)(m.default,{onClick:()=>{var n,t,l,i;return M(null==e?void 0:null===(n=e.row)||void 0===n?void 0:n.fullName,null==e?void 0:null===(t=e.row)||void 0===t?void 0:t.id,null==e?void 0:null===(i=e.row)||void 0===i?void 0:null===(l=i.photo)||void 0===l?void 0:l.id)},children:[null==e?void 0:e.value,(0,i.jsx)(R.BsCalendar2Date,{size:20})]})})},{field:"positions",headerName:"Positions",minWidth:150,flex:.6,align:"center",editable:!1},{field:"actions",headerName:"Actions",minWidth:100,flex:.5,align:"center",editable:!1,renderCell:e=>(0,i.jsxs)(es,{children:[(0,i.jsx)(ea,{onClick:()=>{var n;return eL(null==e?void 0:null===(n=e.row)||void 0===n?void 0:n.id)},children:(0,i.jsx)(w.FiEdit,{})}),(0,i.jsx)(eo,{onClick:()=>{var n,t;return e_(null==e?void 0:null===(n=e.row)||void 0===n?void 0:n.id,null==e?void 0:null===(t=e.row)||void 0===t?void 0:t.fullName)},children:(0,i.jsx)(R.BsTrash,{})})]})}],[ej,ew]=(0,o.useState)([]),[eS,ey]=(0,o.useState)([]),ek=(0,b.useDispatch)(),eC=e=>{(null==e?void 0:e.id)===""&&(e.id=null),f.default.post(r.BASE_URL+"/teacher/save",e,{headers:Y}).then(e=>{p.toast.success("Teacher saved successfully."),eD(),l(!1)}).catch(e=>{})},e_=(e,n)=>{window.confirm("Do you want to delete ".concat(n,"?"))&&f.default.delete(r.BASE_URL+"/kafera-mudiri/deletedTeacherWithUserId/"+e,{headers:Y}).then(e=>{var n;p.toast.error(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.message,{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"}),eD()}).catch(e=>{})},eI=e=>{f.default.put(r.BASE_URL+r.ADMIN.BUILDING_UPDATE,e,{headers:Y}).then(e=>{202===e.status&&p.toast.success("Building updated successfully!...")}).catch(e=>{})},eN=(0,b.useSelector)(e=>{var n;return null==e?void 0:null===(n=e.section)||void 0===n?void 0:n.section}),eD=()=>{n(!0),ew([]),f.default.get(r.BASE_URL+"/kafera-mudiri/getStatistics?id="+(null==eN?void 0:eN.id),{headers:Y}).then(e=>{var t,l;ew(null==e?void 0:null===(l=e.data)||void 0===l?void 0:null===(t=l.obj)||void 0===t?void 0:t.sort((e,n)=>e.fullName>n.fullName?1:-1).map((e,n)=>({...e,count:n+1}))),n(!1)}).catch(e=>ek((0,y.kafedraTeacherStatisticsFetchingError)(e)))},eT=(0,b.useSelector)(e=>{var n,t;return null==e?void 0:null===(t=e.section)||void 0===t?void 0:null===(n=t.section)||void 0===n?void 0:n.id}),[eF,eR]=(0,o.useState)({id:"",kafedraId:eT,fullName:"",rfid:"",login:"",email:"",passportNum:"",nationality:"",citizenship:"",position:"",workStatus:"",subjects:[],rate:""}),[ez,eA]=(0,o.useState)([]),[eE,eB]=(0,o.useState)(!1),eL=e=>{f.default.get(r.BASE_URL+"/kafera-mudiri/positionEdit?id="+e,{headers:Y}).then(e=>{var n,t,l,i,o;ey(null==e?void 0:null===(l=e.data)||void 0===l?void 0:null===(t=l.obj)||void 0===t?void 0:null===(n=t.positions)||void 0===n?void 0:n.map(e=>e.label)),eA(null==e?void 0:null===(o=e.data)||void 0===o?void 0:null===(i=o.obj)||void 0===i?void 0:i.subjects)}).catch(e=>{}),X(ej.find(n=>n.id===e)),eR(n=>{var t;return{...n,id:e,fullName:ej.find(n=>n.id===e).fullName,login:ej.find(n=>n.id===e).login,email:ej.find(n=>n.id===e).email,rfid:ej.find(n=>n.id===e).rfid,passportNum:ej.find(n=>n.id===e).passport,position:null===(t=ej.find(n=>n.id===e))||void 0===t?void 0:t.positions,nationality:ej.find(n=>n.id===e).nationality,citizenship:ej.find(n=>n.id===e).citizenship,workStatus:ej.find(n=>n.id===e).status,subjects:ej.find(n=>n.id===e).subjects,rate:ej.find(n=>n.id===e).rate}}),eB(!0)},eM=e=>{eR(n=>({...n,[e.target.name]:e.target.value}))},[eU,eH]=(0,o.useState)(""),[eO,eP]=(0,o.useState)(!1),eY=()=>{eP(!1)};return(0,o.useEffect)(()=>{eD()},[]),(0,i.jsxs)(eh,{children:[(0,i.jsxs)(ep,{children:["Table of Teachers",(0,i.jsxs)(ef,{children:[(0,i.jsx)(m.default,{variant:"contained",onClick:()=>{eP(!0)},children:"Monthly visitation statistics"}),(0,i.jsx)(m.default,{variant:"contained",color:"success",onClick:()=>{G({...W,id:null,userId:null,kafedraId:null,lessonDtos:null}),T()},endIcon:(0,i.jsx)(R.BsPersonFillAdd,{}),children:" Add Teacher"}),(0,i.jsx)(m.default,{variant:"contained",color:"inherit",onClick:()=>a("table"),endIcon:(0,i.jsx)(B.IoMdBookmarks,{}),children:" Table"})]})]}),(0,i.jsx)(s.default,{open:eO,onClose:eY,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(d.default,{sx:en,component:et,children:[(0,i.jsx)(er,{onClick:eY,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(u.RiCloseLine,{})}),(0,i.jsx)(z.default,{colName:"Teachers",isTeacher:!0,date:new Date,userName:eN.name,kafedraId:eN.id,url:"/kafera-mudiri/getStatisticssForRektor?kafedraId="})]})}),(0,i.jsx)(ec,{children:e?(0,i.jsx)(h.default,{}):(0,i.jsx)(v.DataGrid,{columns:Z,rows:ej,components:{Toolbar:v.GridToolbar},autoHeight:!0,rowsPerPageOptions:[10,20,30],initialState:{columns:{columnVisibilityModel:{articles:!1,card:!1,rfid:!1,email:!1,actions:!1,passport:!1}},pagination:{pageSize:20}}})}),(0,i.jsx)(s.default,{open:eE,onClose:F,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(d.default,{sx:el,component:ei,children:[(0,i.jsx)(er,{onClick:F,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(u.RiCloseLine,{})}),(0,i.jsxs)("h3",{style:{color:"".concat(r.mainColor)},children:["Edit ",null==V?void 0:V.fullName," teacher"]}),(0,i.jsxs)(eu,{children:[(0,i.jsx)(k.default,{id:"outlined-basicdfh",label:"Full Name",variant:"outlined",value:null==eF?void 0:eF.fullName,name:"fullName",onChange:eM}),(0,i.jsx)(k.default,{id:"outlined-basicdfh",label:"RFID",variant:"outlined",value:null==eF?void 0:eF.rfid,name:"rfid",onChange:eM,disabled:!0}),(0,i.jsxs)(C.default,{fullWidth:!0,children:[(0,i.jsx)(_.default,{id:"demo-simple-select-label",children:"Status"}),(0,i.jsxs)(S.default,{labelId:"demo-simple-select-label",id:"demo-simple-select",value:null==eF?void 0:eF.workStatus,label:"Status",name:"workStatus",onChange:eM,children:[(0,i.jsx)(I.default,{value:"ASOSIY",children:"ASOSIY"}),(0,i.jsx)(I.default,{value:"SOATBAY",children:"SOATBAY"}),(0,i.jsx)(I.default,{value:"ORINDOSH",children:"ORINDOSH"})]})]}),(0,i.jsx)(k.default,{id:"outlined-basicdfhe",label:"Login",variant:"outlined",value:null==eF?void 0:eF.login,name:"login",onChange:eM,disabled:!0}),(0,i.jsx)(k.default,{id:"outlined-basrwic",label:"Citizenship",variant:"outlined",value:null==eF?void 0:eF.citizenship,name:"citizenship",onChange:eM}),(0,i.jsx)(k.default,{id:"outlined-badjsic",label:"Email",variant:"outlined",value:null==eF?void 0:eF.email,name:"email",onChange:eM}),(0,i.jsx)(k.default,{id:"outlidfjned-basic",label:"Passport",variant:"outlined",value:null==eF?void 0:eF.passportNum,name:"passportNum",onChange:eM,disabled:!0}),(0,i.jsxs)(C.default,{fullWidth:!0,children:[(0,i.jsx)(_.default,{id:"demo-simple-select-label",children:"Position"}),(0,i.jsx)(S.default,{labelId:"demo-simple-select-label",id:"demo-simple-sretelect",value:null==eF?void 0:eF.position,label:"Position",name:"position",onChange:eM,children:null==eS?void 0:eS.map(e=>(0,i.jsx)(I.default,{value:e,children:e},e))})]}),(0,i.jsx)(k.default,{id:"outtylined-basic",label:"Nationality",variant:"outlined",value:null==eF?void 0:eF.nationality,name:"nationality",onChange:eM}),(0,i.jsx)(D.default,{multiple:!0,id:"checkboxes-tags-demo",options:ez,disableCloseOnSelect:!0,getOptionLabel:e=>e,value:eF.subjects,onChange:(e,n)=>{eR(e=>({...e,subjects:n}))},inputValue:eU,onInputChange:(e,n)=>{eH(n)},renderOption:(e,n,t)=>{let{selected:l}=t;return(0,i.jsxs)("li",{...e,children:[(0,i.jsx)(N.default,{icon:$,checkedIcon:ee,style:{marginRight:8},checked:l}),n]})},renderInput:e=>(0,i.jsx)(k.default,{...e,label:"Subjects",placeholder:"Subjects of Teacher"})}),(0,i.jsx)(k.default,{id:"outlined-basicdfhdsg",label:"Rate",variant:"outlined",type:"number",value:null==eF?void 0:eF.rate,name:"rate",onChange:eM})]}),(0,i.jsx)(d.default,{sx:{display:"flex",justifyContent:"center"},children:(0,i.jsx)(m.default,{endIcon:(0,i.jsx)(j.FaRegSave,{}),variant:"contained",onClick:()=>{var e;(null==eF?void 0:eF.fullName)!==""&&(null==eF?void 0:eF.fullName)!==null&&(null==eF?void 0:null===(e=eF.subjects)||void 0===e?void 0:e.length)!==0&&(null==eF?void 0:eF.subjects)!==null&&(null==eF?void 0:eF.position)!==""&&(null==eF?void 0:eF.position)!==null&&(null==eF?void 0:eF.workStatus)!==""&&(null==eF?void 0:eF.workStatus)!==null&&(null==eF?void 0:eF.login)!==""&&(null==eF?void 0:eF.login)!==null&&(null==eF?void 0:eF.passportNum)!==""&&(null==eF?void 0:eF.passportNum)!==null&&(null==eF?void 0:eF.rfid)!==""&&(null==eF?void 0:eF.rfid)!==null&&(null==eF?void 0:eF.rate)!==""&&(null==eF?void 0:eF.rate)!==null?(n(!0),f.default.post(r.BASE_URL+"/kafera-mudiri/changeTeacher",eF,{headers:Y}).then(e=>{var t,l,i;(null==e?void 0:null===(t=e.data)||void 0===t?void 0:t.success)?(p.toast.success(null==e?void 0:null===(l=e.data)||void 0===l?void 0:l.message),eB(!1),eD()):(p.toast.error(null==e?void 0:null===(i=e.data)||void 0===i?void 0:i.message),n(!1))}).catch(e=>{n(!1)})):p.toast.warning("Empty any fields..")},children:"Update"})})]})}),(0,i.jsx)(s.default,{open:t,onClose:F,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(d.default,{sx:ev,component:em,children:[(0,i.jsx)(er,{onClick:F,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(u.RiCloseLine,{})}),(0,i.jsx)(c.default,{title:"Add Teacher",formArr:[{label:"id",name:"id",placeholder:"Enter id of build",type:"text"},{label:"Select User:",name:"userId",placeholder:"Select User",type:"select"},{label:"Select Status:",name:"workerStatus",placeholder:"Select Status",type:"select"},{label:"Select Subjects:",name:"lessonDtos",placeholder:"Select Subjects",type:"select"},{label:"Select Position:",name:"positionId",placeholder:"Select Position",type:"select"},{label:"Rate:",name:"RateId",placeholder:"rate",type:"number"}],submitBtn:(null==W?void 0:W.id)!==null?"Update":"Save",onSubmit:e=>(null==W?void 0:W.id)!==null?eI(e):eC(e),updateItem:W})]})}),(0,i.jsx)(s.default,{open:A,onClose:U,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(d.default,{sx:ex,component:eg,children:[(0,i.jsx)(er,{onClick:U,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(u.RiCloseLine,{})}),(0,i.jsx)(x.default,{userName:V,userId:q,date:new Date,photo:K})]})}),(0,i.jsx)(s.default,{open:H,onClose:P,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(d.default,{sx:{...eb},children:[(0,i.jsx)(er,{onClick:()=>{O(!1)},whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(u.RiCloseLine,{})}),(0,i.jsx)(g.default,{selectId:q,photo:K})]})})]})}},55605:function(e,n,t){"use strict";t.r(n);var l=t("58865");t("33948"),t("57640"),t("9924"),t("15581"),t("34514"),t("2490"),t("57658");var i=t("85893"),o=t("67294"),a=t("30381"),r=t.n(a),d=t("71893"),u=t("16393"),s=t("94718"),c=t("45155"),f=t("27233"),p=t("55693"),h=t("7104"),v=t("79352"),m=t("97367"),x=t("48684"),g=t("50533"),b=t("90650"),j=t("57024"),w=t("37595"),S=t("70231");function y(){let e=(0,l._)(["\n  flex: 3;\n  display: flex;\n  font-size: ",";\n  border: 1px solid ",";\n  align-items: center;\n  justify-content: center;\n  padding: 5px 10px !important;\n"]);return y=function(){return e},e}function k(){let e=(0,l._)(["\n  width: 30px;\n  height: 30px;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  background-color: ",";\n  border-radius: 50%;\n  color: white;\n  font-size: 26px;\n  border: none;\n  position: absolute;\n  top: 10px;\n  right: 10px;\n"]);return k=function(){return e},e}function C(){let e=(0,l._)(["\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  font-size: 26px;\n  color: ",";\n"]);return C=function(){return e},e}function _(){let e=(0,l._)(["\n  display: flex;\n  gap: 20px;\n"]);return _=function(){return e},e}function I(){let e=(0,l._)(["\n  width: 100%;\n  min-height: 40px;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  font-size: 24px;\n  font-weight: bold;\n  border: 1px solid ",";\n  color: ",";\n  background-color: ",";\n  transition: 0.1s all ease-in;\n\n  &:hover {\n    filter: brightness(.7);\n  }\n\n  &:focus {\n    transform: scale(0.95);\n  }\n"]);return I=function(){return e},e}function N(){let e=(0,l._)(["\n  font-size: 12px;\n  width: 100%;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n"]);return N=function(){return e},e}function D(){let e=(0,l._)(["\n  width: 100%;\n  min-height: 40px;\n  border: 1px solid ",";\n  display: flex;\n  align-items: center;\n  flex-direction: column;\n"]);return D=function(){return e},e}function T(){let e=(0,l._)(["\n  flex: 1;\n  height: 47px;\n  display: flex;\n  //flex-direction: column;\n"]);return T=function(){return e},e}function F(){let e=(0,l._)(["\n  display: flex;\n  width: 1200px;\n  margin: 0 auto;\n"]);return F=function(){return e},e}function R(){let e=(0,l._)(["\n  width: 100%;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  gap: 10px;\n  font-size: 24px;\n"]);return R=function(){return e},e}function z(){let e=(0,l._)(["\n  width: 100%;\n    height: 70vh;\n  overflow: scroll;\n  display: flex;\n  flex-direction: column;\n  margin-top: 25px !important;\n"]);return z=function(){return e},e}function A(){let e=(0,l._)(["\n  width: 800px;\n  padding: 5px 10px !important;\n"]);return A=function(){return e},e}function E(){let e=(0,l._)(["\n  ","\n  ","\n"]);return E=function(){return e},e}let B=d.default.div(y(),e=>e.sz?"12px":"8px",u.mainColor),L=(0,d.default)(b.motion.button)(k(),u.mainColor),M=d.default.div(C(),u.mainColor),U=d.default.div(_()),H=d.default.button(I(),u.mainColor,e=>e.bg?"#fff":e.color?"green":"red",e=>e.bg?e.bg:"#fff"),O=d.default.span(N()),P=d.default.div(D(),u.mainColor),Y=d.default.div(T()),W=d.default.div(F()),G=d.default.div(R()),V=d.default.div(z()),X=d.default.div(A()),q={width:"40px!important",height:"40px!important",fontSize:"24px"},J={border:"none"},K={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"50vw",bgcolor:"background.paper",boxShadow:24,borderRadius:3,p:4},Q=d.default.div(E(),(0,j.small)({width:"80vw !important"}),(0,j.extrasmall)({width:"90vw !important"}));n.default=e=>{let{userName:n,kafedraId:t,date:l,forUser:a,url:d,isTeacher:b,colName:j}=e,[y,k]=(0,o.useState)(l),[C,_]=(0,o.useState)([]),[I,N]=(0,o.useState)(null),[D,T]=(0,o.useState)(null),[F,R]=(0,o.useState)(null),[z,A]=(0,o.useState)(!1),E=(e,n)=>{var t;T(new Date(y.getFullYear(),y.getMonth(),e)),R(null===(t=I[n])||void 0===t?void 0:t.monthly[e]),A(!0)},Z=()=>{A(!1)},{headers:$}=(0,u.getHeaders)(),ee=e=>e.getFullYear()+"."+(e.getMonth()+1)+"."+e.getDate();(0,o.useEffect)(()=>{_(()=>Array.from(Array(r()(y).daysInMonth()).keys()).map(e=>e+1)),ed()},[y]);let[en,et]=(0,o.useState)([]),el=()=>{null==I||I.map(e=>{let n=0;return null==C||C.map(t=>{(null==e?void 0:e.monthly[t])!=null&&n++}),et(e=>[...e,n]),e})};(0,o.useEffect)(()=>{N(b?eu:es)},[]);let[ei,eo]=(0,o.useState)(!1);(0,o.useEffect)(()=>{et([]),el()},[ei]);let[ea,er]=(0,o.useState)(!0),ed=async()=>{er(!0),await f.default.get("".concat(u.BASE_URL+d+t,"&date=").concat(ee(y)),{headers:$}).then(e=>{var n,t;N(null==e?void 0:null===(t=e.data)||void 0===t?void 0:null===(n=t.obj)||void 0===n?void 0:n.all),er(!1),eo(e=>!e)}).catch(e=>{})},eu=(0,g.useSelector)(e=>{var n,t;return null==e?void 0:null===(t=e.rektorTeachers)||void 0===t?void 0:null===(n=t.rektorTeachers)||void 0===n?void 0:n.allTeachers}),es=(0,g.useSelector)(e=>{var n,t;return null==e?void 0:null===(t=e.rektorStaffs)||void 0===t?void 0:null===(n=t.rektorStaffs)||void 0===n?void 0:n.allStaffs});return(0,i.jsx)(i.Fragment,{children:(0,i.jsxs)(X,{style:{width:"".concat(a||"100%")},children:[(0,i.jsx)(U,{children:(0,i.jsx)(M,{children:n})}),(0,i.jsx)("hr",{}),(0,i.jsxs)(G,{children:[(0,i.jsx)(s.default,{style:q,onClick:()=>{k(new Date(y.getFullYear(),y.getMonth()-1))},children:(0,i.jsx)(c.GrFormPrevious,{})}),(0,i.jsx)("input",{type:"month",id:"start",name:"start",min:"2022-04",value:r()(y).format("YYYY-MM"),onChange:e=>k(new Date(e.target.value)),style:J}),(0,i.jsx)(s.default,{style:q,onClick:()=>{k(new Date(y.getFullYear(),y.getMonth()+1))},children:(0,i.jsx)(c.GrFormNext,{})})]}),(0,i.jsx)(V,{children:ea?(0,i.jsx)(h.Skeleton,{animation:"wave",width:a||"100%",height:120}):(0,i.jsxs)(i.Fragment,{children:[(0,i.jsx)(W,{children:null==C?void 0:C.map((e,n)=>(0,i.jsxs)(i.Fragment,{children:[1===e&&(0,i.jsx)(B,{sz:!0,children:j},n),(0,i.jsx)(Y,{children:(0,i.jsxs)(P,{children:[e,(0,i.jsx)(O,{children:r()(new Date(y.getFullYear(),y.getMonth(),e)).format("ddd")})]})},e),e===(null==C?void 0:C.length)&&(0,i.jsx)(Y,{children:(0,i.jsx)(P,{children:"\u2211"})})]}))}),null==I?void 0:I.map((e,n)=>(0,i.jsxs)(W,{children:[(0,i.jsx)(B,{children:e.fullName}),null==C?void 0:C.map(t=>(0,i.jsxs)(i.Fragment,{children:[(0,i.jsx)(Y,{children:(0,i.jsx)(H,{color:e.monthly[t],onClick:()=>E(t,n),children:new Date(y.getFullYear(),y.getMonth(),t)<=new Date?e.monthly[t]?(0,i.jsx)(S.FaCircleCheck,{}):(0,i.jsx)(S.FaCircleXmark,{}):""})},t),t===(null==C?void 0:C.length)&&(0,i.jsx)(Y,{children:(0,i.jsx)(H,{color:0!==en[n],bg:0!==en[n]?"green":"red",children:en[n]})})]}))]}))]})}),(0,i.jsx)(p.default,{display:"flex",justifyContent:"end",mt:1,children:(0,i.jsx)(s.default,{onClick:()=>{if(!I)return;let e=[[j,...C.map(e=>e.toString()),"Total"],...I.map(e=>{let n=[e.fullName],t=0;return C.forEach(l=>{if(new Date(y.getFullYear(),y.getMonth(),l)<=new Date){let i=e.monthly[l]?"1":"0";n.push(i),t+=parseInt(i)}else n.push("")}),n.push(t.toString()),n})],n=w.utils.aoa_to_sheet(e),t=w.utils.book_new();w.utils.book_append_sheet(t,n,"Sheet1"),w.writeFile(t,"exported_data.xlsx")},variant:"contained",children:"Export"})}),(0,i.jsx)(m.default,{open:z,onClose:Z,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(p.default,{sx:K,component:Q,children:[(0,i.jsx)(L,{onClick:Z,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(v.RiCloseLine,{})}),(0,i.jsx)(x.default,{time:D,item:F})]})})]})})}}}]);