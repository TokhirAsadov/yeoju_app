/*! For license information please see 6727.87c31255.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["6727"],{73896:function(e,n,t){"use strict";t.r(n);var l=t("58865");t("33948"),t("57640"),t("9924");var i=t("85893"),o=t("67294"),d=t("71893"),r=t("50533"),a=t("16393"),s=t("89583"),u=t("94718"),c=t("30381"),p=t.n(c),v=t("86893"),f=t("63750"),h=t("27233"),x=t("72132"),m=t("97367"),g=t("55693"),b=t("79352"),w=t("55605"),j=t("53432"),S=t("16356"),y=t("61006"),I=t("87639"),k=t("90650"),C=t("57024");function _(){let e=(0,l._)(["\n  ",",\n  ","\n"]);return _=function(){return e},e}function U(){let e=(0,l._)(["\n","\n"]);return U=function(){return e},e}function R(){let e=(0,l._)(["\n  width: 30px;\n  height: 30px;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  background-color: ",";\n  border-radius: 50%;\n  color: white;\n  font-size: 26px;\n  border: none;\n  position: absolute;\n  top: 10px;\n  right: 10px;\n"]);return R=function(){return e},e}function B(){let e=(0,l._)(["\n  width: 100%;\n  display: flex;\n  gap: 10px;\n  margin: 5px !important;\n"]);return B=function(){return e},e}function N(){let e=(0,l._)(["\n  width: 30px;\n  height: 30px;\n  background-color: #fff;\n  font-size: 20px;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  border: 1px solid red;\n  border-radius: 0.5rem;\n  color: red;\n  padding: 5px !important;\n\n  &:hover {\n    transform: scale(1.1);\n  }\n\n"]);return N=function(){return e},e}function E(){let e=(0,l._)(["\n  width: 30px;\n  height: 30px;\n  background-color: #fff;\n  font-size: 20px;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  border: 1px solid ",";\n  border-radius: 0.5rem;\n  color: ",";\n  padding: 5px !important;\n\n  &:hover {\n    transform: scale(1.1);\n  }\n\n"]);return E=function(){return e},e}function A(){let e=(0,l._)(["\n  width: 100%;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  gap: 10px;\n  margin: 5px !important;\n"]);return A=function(){return e},e}function L(){let e=(0,l._)(["\n  width: 100%;\n  background-color: #fff;\n  border-radius: 10px;\n  display: flex;\n  align-items: center;\n  justify-content: space-between;\n"]);return L=function(){return e},e}function T(){let e=(0,l._)(["\n  display: flex;\n  flex-wrap: wrap;\n  gap: 20px;\n"]);return T=function(){return e},e}function D(){let e=(0,l._)(["\n  width: 100%;\n  display: flex;\n  justify-content: space-between;\n  flex-wrap: wrap;\n  gap: 20px;\n  color: ",";\n"]);return D=function(){return e},e}function H(){let e=(0,l._)(["\n  display: flex;\n  flex-direction: column;\n  gap: 20px;\n  border-radius: 10px;\n  margin-top: 10px !important;\n  margin-left: 10px !important;\n  padding: 5px 10px !important;\n"]);return H=function(){return e},e}let F=d.default.div(_(),(0,C.extrasmall)({width:"90vw !important"}),(0,C.small)({width:"90vw !important"})),O={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"85vw",height:"85vh",bgcolor:"background.paper",boxShadow:24,borderRadius:3,p:4},z={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"500px",padding:"15px ",bgcolor:"background.paper",boxShadow:24,borderRadius:3,p:3},P=d.default.div(U(),(0,C.extrasmall)({width:"95vw !important"})),M={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"95vw",minHeight:400,bgcolor:"background.paper",boxShadow:24,borderRadius:3,p:4},W=(0,d.default)(k.motion.button)(R(),a.mainColor),G=d.default.div(B()),K=d.default.button(N()),V=d.default.button(E(),a.mainColor,a.mainColor),q=d.default.div(A()),J=d.default.div(L()),Q=d.default.div(T()),X=d.default.h3(D(),a.mainColor),Y=d.default.div(H());n.default=()=>{let e=(0,r.useSelector)(e=>{var n;return null==e?void 0:null===(n=e.dekanat)||void 0===n?void 0:n.dekanat}),[n,t]=(0,o.useState)(!0),[l,d]=(0,o.useState)(!1),[c,k]=(0,o.useState)(""),C=[{field:"id",headerName:"ID",width:40,editable:!1},{field:"fullName",headerName:"Name",type:"string",width:300,editable:!1,renderCell:e=>{var n,t,l,o;return(0,i.jsxs)(G,{children:[(null==e?void 0:null===(n=e.row)||void 0===n?void 0:n.photo)?(0,i.jsx)("img",{src:(null==e?void 0:null===(t=e.row)||void 0===t?void 0:t.photo)?a.BASE_URL+"/attachment/download/"+(null==e?void 0:null===(o=e.row)||void 0===o?void 0:null===(l=o.photo)||void 0===l?void 0:l.id):"",width:"40px",height:"40px",alt:null==e?void 0:e.value,style:{borderRadius:"50%"}}):(0,i.jsx)("span",{style:{display:"flex",alignItems:"center",justifyContent:"center",fontSize:"20px"},children:(0,i.jsx)(s.FaUserAlt,{})}),(0,i.jsx)("span",{style:{display:"flex",alignItems:"center",justifyContent:"center",fontSize:"14px"},children:(0,i.jsx)(u.default,{onClick:()=>{var n;return ei(null==e?void 0:null===(n=e.row)||void 0===n?void 0:n.id)},children:null==e?void 0:e.value})})]})}},{field:"login",headerName:"Login",type:"string",width:300,editable:!1},{field:"email",headerName:"Email",type:"string",width:300,editable:!1},{field:"rfid",headerName:"RFID",type:"string",width:300,editable:!1},{field:"passport",headerName:"Passport",type:"string",width:300,editable:!1},{field:"countTouch",headerName:"Kelgan kunlari \n".concat(p()(new Date).format("MMM")),type:"string",width:170,editable:!1,renderCell:e=>(0,i.jsx)(q,{children:(0,i.jsxs)(u.default,{onClick:()=>{var n,t,l,i;return et(null==e?void 0:null===(n=e.row)||void 0===n?void 0:n.fullName,null==e?void 0:null===(t=e.row)||void 0===t?void 0:t.id,null==e?void 0:null===(i=e.row)||void 0===i?void 0:null===(l=i.photo)||void 0===l?void 0:l.id)},children:[null==e?void 0:e.value,(0,i.jsx)(f.BsCalendar2Date,{size:20})]})})},{field:"positions",headerName:"Shtat birligi va lavozimi",type:"string",width:190,editable:!1,renderCell:e=>(0,i.jsx)(q,{children:null==e?void 0:e.value})},{field:"STATUS",headerName:"Action",type:"string",width:100,editable:!1,renderCell:e=>(0,i.jsxs)(q,{children:[(0,i.jsx)(V,{onClick:()=>{var n,t,l;return er(null==e?void 0:null===(n=e.row)||void 0===n?void 0:n.id,null==e?void 0:null===(t=e.row)||void 0===t?void 0:t.fullName,null==e?void 0:null===(l=e.row)||void 0===l?void 0:l.positions)},children:(0,i.jsx)(v.FiEdit,{})}),(0,i.jsx)(K,{onClick:()=>{var n,t;return ea(null==e?void 0:null===(n=e.row)||void 0===n?void 0:n.id,null==e?void 0:null===(t=e.row)||void 0===t?void 0:t.fullName)},children:(0,i.jsx)(f.BsTrash,{})})]})}],[_,U]=(0,o.useState)([]),[R,B]=(0,o.useState)(!1),[N,E]=(0,o.useState)(""),[A,L]=(0,o.useState)(""),[T,D]=(0,o.useState)(!1),[H,Z]=(0,o.useState)(!1),[$,ee]=(0,o.useState)(!1),{headers:en}=(0,a.getHeaders)(),et=(e,n,t)=>(L(e),E(t),k(n),Z(!0)),el=()=>{Z(!1)},ei=e=>{k(e),D(!0)},eo=()=>{d(!1)},ed=()=>{B(!1),ee(!1)},er=(e,n,t)=>{ec(l=>({...l,sectionId:null,positionId:t,userId:{value:e,label:n}})),h.default.get(a.BASE_URL+"/staff/getStaffIdWithUserId/"+e,{headers:en}).then(e=>{var n;x.toast.success(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.message),ec(n=>{var t;return{...n,id:null==e?void 0:null===(t=e.data)||void 0===t?void 0:t.obj}}),ee(!0)}).catch(e=>{var n,t;x.toast.error(null==e?void 0:null===(t=e.response)||void 0===t?void 0:null===(n=t.data)||void 0===n?void 0:n.message,{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"})})},ea=(e,n)=>{h.default.get(a.BASE_URL+"/staff/getStaffIdWithUserId/"+e,{headers:en}).then(e=>{if(window.confirm("Do you want to delete ".concat(n,"?"))){var t;h.default.delete(a.BASE_URL+"/staff/deleteStaff/"+(null==e?void 0:null===(t=e.data)||void 0===t?void 0:t.obj),{headers:en}).then(e=>{var n;x.toast.error(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.message,{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"}),es()}).catch(e=>{var n,t;x.toast.error(null==e?void 0:null===(t=e.response)||void 0===t?void 0:null===(n=t.data)||void 0===n?void 0:n.message,{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"})})}}).catch(e=>{var n,t;x.toast.error(null==e?void 0:null===(t=e.response)||void 0===t?void 0:null===(n=t.data)||void 0===n?void 0:n.message)})};(0,o.useEffect)(()=>{es()},[]);let es=()=>{t(!0),U([]),h.default.get(a.BASE_URL+"/section/getStatisticsForDekan",{headers:en}).then(e=>{var n,l;U(null==e?void 0:null===(l=e.data)||void 0===l?void 0:null===(n=l.obj)||void 0===n?void 0:n.sort((e,n)=>e.fullName>n.fullName?1:-1)),t(!1)}).catch(e=>{})},[eu,ec]=(0,o.useState)({id:null,sectionId:null,positionId:null,userId:null}),ep=e=>{(null==e?void 0:e.id)!==void 0&&(null==e?void 0:e.id)!==null&&(null==e?void 0:e.id)!==""&&(null==e?void 0:e.userId)!==void 0&&(null==e?void 0:e.userId)!==null&&(null==e?void 0:e.userId)!==""&&(null==e?void 0:e.positionId)!==void 0&&(null==e?void 0:e.positionId)!==""&&(null==e?void 0:e.positionId)!==null?h.default.post(a.BASE_URL+"/staff/saveStaff",e,{headers:en}).then(e=>{x.toast.success("Staff updated successfully."),es(),ee(!1)}).catch(e=>{var n,t;x.toast.error(null==e?void 0:null===(t=e.response)||void 0===t?void 0:null===(n=t.data)||void 0===n?void 0:n.message,{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"})}):x.toast.warning("Empty any fields...")},ev=e=>{((null==e?void 0:e.id)===""||(null==e?void 0:e.id)===void 0)&&(e.id=null,e.sectionId=null),(null==e?void 0:e.id)===null&&(e.sectionId=null),(null==e?void 0:e.userId)!==void 0&&(null==e?void 0:e.userId)!==null&&(null==e?void 0:e.userId)!==""&&(null==e?void 0:e.positionId)!==void 0&&(null==e?void 0:e.positionId)!==""&&(null==e?void 0:e.positionId)!==null?(x.toast.success("all done add"),h.default.post(a.BASE_URL+"/staff/saveStaff",e,{headers:en}).then(e=>{x.toast.success("Staff saved successfully."),es(),B(!1)}).catch(e=>{var n,t;x.toast.error(null==e?void 0:null===(t=e.response)||void 0===t?void 0:null===(n=t.data)||void 0===n?void 0:n.message,{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"})})):x.toast.warning("Empty any fields...")};return(0,i.jsxs)(Y,{children:[(0,i.jsxs)(X,{children:["Table of Staffs",(0,i.jsxs)(Q,{children:[(0,i.jsx)(u.default,{variant:"contained",onClick:()=>{d(!0)},children:"Open Month Modal"}),(0,i.jsx)(u.default,{variant:"contained",color:"success",onClick:()=>{ec({...eu,id:null,userId:null,sectionId:null,positionId:null}),B(!0)},endIcon:(0,i.jsx)(f.BsPersonFillAdd,{}),children:"Add Staff"})]})]}),(0,i.jsx)(m.default,{open:l,onClose:eo,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(g.default,{sx:O,component:F,children:[(0,i.jsx)(W,{onClick:eo,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(b.RiCloseLine,{})}),(0,i.jsx)(w.default,{colName:"Staffs",isTeacher:!1,date:new Date(a.serverTimeStorage),userName:e.name,kafedraId:e.id,url:"/staff/getStatisticsForRektor?sectionId="})]})}),(0,i.jsx)(J,{children:n?(0,i.jsx)(j.default,{}):(0,i.jsx)(S.DataGrid,{checkboxSelection:!0,style:{width:"500px!important",minHeight:"300px!important"},columns:C,rows:_,components:{Toolbar:S.GridToolbar},autoHeight:!0,pageSize:10,initialState:{...null==_?void 0:_.initialState,columns:{columnVisibilityModel:{id:!1,login:!1,card:!1,rfid:!1,email:!1,passport:!1}}}})}),(0,i.jsx)(m.default,{open:H,onClose:el,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(g.default,{sx:M,children:[(0,i.jsx)(W,{onClick:el,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(b.RiCloseLine,{})}),(0,i.jsx)(y.default,{userName:A,userId:c,date:new Date(a.serverTimeStorage),photo:N})]})}),(0,i.jsx)(m.default,{open:R,onClose:ed,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(g.default,{sx:z,component:P,children:[(0,i.jsx)(W,{onClick:ed,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(b.RiCloseLine,{})}),(0,i.jsx)(I.default,{isSection:!1,title:"Add Staff",formArr:[{label:"id",name:"id",placeholder:"Enter id of staff",type:"text"},{label:"Select User:",name:"userId",placeholder:"Select User",type:"select"},{label:"Select Position:",name:"positionId",placeholder:"Select Position",type:"select"}],submitBtn:(null==eu?void 0:eu.id)!==null?"Update":"Save",onSubmit:e=>(null==eu?void 0:eu.id)!==null?ep(e):ev(e),updateItem:eu})]})}),(0,i.jsx)(m.default,{open:$,onClose:ed,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(g.default,{sx:z,component:P,children:[(0,i.jsx)(W,{onClick:ed,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(b.RiCloseLine,{})}),(0,i.jsx)(I.default,{isSection:!1,title:"Update Staff",formArr:[{label:"id",name:"id",placeholder:"Enter id of staff",type:"text"},{label:"Select User:",name:"userId",placeholder:"Select User",type:"select"},{label:"Select Position:",name:"positionId",placeholder:"Select Position",type:"select"}],submitBtn:(null==eu?void 0:eu.id)!==null?"Update":"Save",onSubmit:e=>(null==eu?void 0:eu.id)!==null?ep(e):ev(e),updateItem:eu})]})})]})}},87639:function(e,n,t){"use strict";t.r(n);var l=t("58865");t("25387"),t("72608"),t("2490"),t("33948"),t("31672"),t("59461"),t("57640"),t("9924");var i=t("85893"),o=t("67294"),d=t("71893"),r=t("39711"),a=t("16393"),s=t("23157"),u=t("41931"),c=t("27233"),p=t("53432"),v=t("50533"),f=t("94718"),h=t("55693"),x=t("57024"),m=t("89583");function g(){let e=(0,l._)(["\n  width: 100%;\n  border-radius: 10px;\n  color: ",";\n"]);return g=function(){return e},e}function b(){let e=(0,l._)(["\n  display: flex;\n  flex-direction: column;\n  align-items: stretch;\n  gap: 20px;\n  \n"]);return b=function(){return e},e}function w(){let e=(0,l._)(["\n  display: flex;\n  gap: 15px;\n  flex-wrap: wrap;\n  justify-content: space-between;\n  & > div{\n    width: 250px!important;\n  }\n  ","\n"]);return w=function(){return e},e}function j(){let e=(0,l._)(["\n  border: 1px solid ",";\n  height: 40px;\n  width: 200px;\n  padding-left: 10px!important;\n  border-radius: 5px;\n  display: ",";\n\n  &::placeholder{\n    font-size: 14px;\n    letter-spacing: 1.1px;\n    color: ",";\n  }\n  &:focus{\n    outline: none;\n  }\n"]);return j=function(){return e},e}function S(){let e=(0,l._)(["\n  font-size: 20px;\n"]);return S=function(){return e},e}function y(){let e=(0,l._)(["\n  font-size: 12px;\n  width: 100%;\n  display: flex;\n  justify-content: center;\n  margin-top: 10px!important;\n"]);return y=function(){return e},e}function I(){let e=(0,l._)(["\n\n"]);return I=function(){return e},e}function k(){let e=(0,l._)(["\n  color: ",";\n  margin-bottom: 20px!important;\n  text-align: start;\n"]);return k=function(){return e},e}function C(){let e=(0,l._)(["\n  text-decoration: none;\n  color: blue;\n"]);return C=function(){return e},e}let _=(0,u.default)(),U=e=>e.reduce((e,n)=>"lessonDtos"!==n.name?{...e,[n.name]:""}:{...e,[n.name]:[]},{}),R=d.default.form(g(),a.mainColor),B=d.default.div(b()),N=d.default.div(w(),(0,x.extrasmall)({flexDirection:"column"})),E=d.default.input(j(),a.mainColor,e=>e.display?"none":"block",a.mainColor),A=d.default.label(S()),L=d.default.div(y()),T=d.default.span(I()),D=d.default.h3(k(),a.mainColor),H=(0,d.default)(r.Link)(C());n.default=e=>{let{title:n,formArr:t,submitBtn:l,onSubmit:d,updateItem:r,redirect:u,isSection:x}=e,g=(0,v.useSelector)(e=>{var n;return null==e?void 0:null===(n=e.user)||void 0===n?void 0:n.user}),[b,w]=(0,o.useState)([]),[j,S]=(0,o.useState)([]),[y,I]=(0,o.useState)(!0);U(t);let[k,C]=(0,o.useState)(U(t)),F=(e,n)=>C(t=>({...t,[n]:e})),O=()=>{d(M(k))},{headers:z}=(0,a.getHeaders)();(0,o.useEffect)(()=>{C({...k,id:null==r?void 0:r.id,userId:null==r?void 0:r.userId,sectionId:null==r?void 0:r.sectionId}),c.default.get(a.BASE_URL+"/dekanat/getUserForDekanSave?bool="+x,{headers:z}).then(e=>{var n,t,l,i;w(null==e?void 0:null===(t=e.data)||void 0===t?void 0:null===(n=t.obj)||void 0===n?void 0:n.users),S(null==e?void 0:null===(i=e.data)||void 0===i?void 0:null===(l=i.obj)||void 0===l?void 0:l.positions),(null==r?void 0:r.positionId)!==null&&(null==r?void 0:r.positionId)!==""&&C(n=>{var t,l;return{...n,positionId:null==e?void 0:null===(l=e.data)||void 0===l?void 0:null===(t=l.obj)||void 0===t?void 0:t.positions.find(e=>(null==e?void 0:e.label)===(null==r?void 0:r.positionId))}}),I(!1)}).catch(e=>{})},[]);let P=(e,n)=>{e.length>2&&"userId"===n&&c.default.get(a.BASE_URL+"/user/getUserForTeacherSavingSearch?keyword="+e,{headers:z}).then(e=>{var n;w(null==e?void 0:null===(n=e.data)||void 0===n?void 0:n.obj)}).catch(e=>{})},M=e=>{var n,t;return{id:null==e?void 0:e.id,sectionId:null==e?void 0:e.sectionId,userId:null==e?void 0:null===(n=e.userId)||void 0===n?void 0:n.value,positionId:null==e?void 0:null===(t=e.positionId)||void 0===t?void 0:t.value}};return(0,i.jsx)(R,{children:y?(0,i.jsx)(p.default,{}):(0,i.jsxs)(i.Fragment,{children:[(0,i.jsx)(D,{children:n}),(0,i.jsx)(B,{children:null==t?void 0:t.map((e,n)=>{var t;let{label:l,name:o,placeholder:d,type:a,value:u}=e;if("select"!==a)return(0,i.jsx)(i.Fragment,{children:(0,i.jsx)(E,{display:"id"===o,id:o,name:o,type:a,value:k[o],onChange:e=>F(e,o),placeholder:d},n)});return(0,i.jsxs)(N,{children:[(0,i.jsx)(A,{htmlFor:o,children:l}),(0,i.jsx)(s.default,{width:"300px",closeMenuOnSelect:!0,components:_,isMulti:!1,options:"userId"===o?b:j,onChange:e=>F(e,o),value:k[o],name:o,isClearable:!0,onInputChange:e=>P(e,o),isDisabled:(null==g?void 0:g.id)===(null==r?void 0:null===(t=r.userId)||void 0===t?void 0:t.value)&&"userId"===o})]})})}),(0,i.jsx)(h.default,{sx:{mt:"20px",display:"flex",justifyContent:"end"},children:(0,i.jsxs)(f.default,{variant:"contained",onClick:e=>{e.preventDefault(),O()},children:[l.startsWith("Update")?(0,i.jsx)(m.FaEdit,{style:{marginRight:"10px"}}):(0,i.jsx)(m.FaSave,{style:{marginRight:"10px"}}),l]})}),!!u&&(0,i.jsxs)(L,{children:[(0,i.jsxs)(T,{children:[u.label,"\xa0"]}),(0,i.jsx)(H,{to:u.link.to,children:u.link.label})]})]})})}}}]);