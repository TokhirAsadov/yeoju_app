/*! For license information please see 2148.6af12d56.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["2148"],{17171:function(e,n,l){"use strict";var o=l("5751");Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var i=o(l("64938")),t=l("85893"),d=(0,i.default)((0,t.jsx)("path",{d:"M19 3H5c-1.11 0-2 .9-2 2v14c0 1.1.89 2 2 2h14c1.11 0 2-.9 2-2V5c0-1.1-.89-2-2-2zm-9 14-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"}),"CheckBox");n.default=d},39628:function(e,n,l){"use strict";var o=l("5751");Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var i=o(l("64938")),t=l("85893"),d=(0,i.default)((0,t.jsx)("path",{d:"M19 5v14H5V5h14m0-2H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2z"}),"CheckBoxOutlineBlank");n.default=d},17716:function(e,n,l){"use strict";l.r(n);var o=l("58865");l("33948"),l("88449"),l("19894"),l("2490"),l("25387"),l("72608"),l("31672"),l("59461"),l("68216"),l("79433");var i=l("85893"),t=l("67294"),d=l("71893"),a=l("16393"),r=l("27233"),u=l("72132"),s=l("53432"),c=l("16356"),v=l("89583"),h=l("86893"),p=l("63750"),f=l("79352"),m=l("57024"),g=l("90650"),x=l("55693"),b=l("52861"),j=l("44118"),w=l("97367"),k=l("15352"),y=l("39628"),C=l("17171"),S=l("94718"),R=l("7104");function E(){let e=(0,o._)(["\n    height: 250px;\n    padding-top: 10px;\n    display: grid;\n    overflow-y: scroll;\n    grid-template-columns: repeat(3, 1fr);\n    gap: 30px;\n    ","\n    ","\n    ","\n"]);return E=function(){return e},e}function O(){let e=(0,o._)(["\n    width: 30px;\n    height: 30px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    background-color: ",";\n    border-radius: 50%;\n    color: white;\n    font-size: 26px;\n    border: none;\n    position: absolute;\n    top: 10px;\n    right: 10px;\n"]);return O=function(){return e},e}function U(){let e=(0,o._)(["\n    ","\n    ","\n    ","\n"]);return U=function(){return e},e}function _(){let e=(0,o._)(["\n    width: 30px;\n    height: 30px;\n    background-color: #fff;\n    font-size: 20px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    border: 1px solid red;\n    border-radius: 0.5rem;\n    color: red;\n    padding: 5px !important;\n\n    &:hover {\n        transform: scale(1.1);\n    }\n\n"]);return _=function(){return e},e}function z(){let e=(0,o._)(["\n    width: 30px;\n    height: 30px;\n    background-color: #fff;\n    font-size: 20px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    border: 1px solid ",";\n    border-radius: 0.5rem;\n    color: ",";\n    padding: 5px !important;\n\n    &:hover {\n        transform: scale(1.1);\n    }\n\n"]);return z=function(){return e},e}function B(){let e=(0,o._)(["\n    width: 100%;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    gap: 10px;\n    margin: 5px !important;\n"]);return B=function(){return e},e}function L(){let e=(0,o._)(["\n    display: flex;\n    flex-direction: column;\n    gap: 20px;\n    padding: 1rem;\n"]);return L=function(){return e},e}let I=(0,i.jsx)(y.default,{fontSize:"small"}),A=(0,i.jsx)(C.default,{fontSize:"small"}),H=d.default.div(E(),(0,m.medium)({height:"100%",gridTemplateColumns:"1fr",gap:"15px"}),(0,m.small)({height:"100%",gridTemplateColumns:"1fr",gap:"15px"}),(0,m.extrasmall)({height:"100%",gridTemplateColumns:"1fr",gap:"15px"})),N=(0,d.default)(g.motion.button)(O(),a.mainColor),P={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"80vw",display:"flex",flexDirection:"column",gap:"20px",bgcolor:"background.paper",boxShadow:24,borderRadius:3,p:4},K=d.default.div(U(),(0,m.medium)({width:"97vw !important",height:"97vh !important"}),(0,m.small)({width:"97vw !important",height:"97vh !important"}),(0,m.extrasmall)({width:"97vw !important",height:"97vh !important"})),T=d.default.button(_()),V=d.default.button(z(),a.mainColor,a.mainColor),D=d.default.div(B()),F=d.default.div(L());n.default=()=>{let[e,n]=(0,t.useState)(!1),[l,o]=(0,t.useState)([]),[d,m]=(0,t.useState)([]),[g,y]=(0,t.useState)(!1),[C,E]=(0,t.useState)([]),[O,U]=(0,t.useState)([]),[_,z]=(0,t.useState)({id:null,nameEn:null,nameRu:null,nameUz:null,owner:null,room:null,phone:null,roles:[],positions:[]}),[B,L]=(0,t.useState)(null),M=[{field:"id",headerName:"ID",width:40,editable:!1},{field:"nameEn",headerName:"English name",type:"string",width:300,editable:!1},{field:"nameUz",headerName:"O`zbekcha nomi",type:"string",width:300,editable:!1},{field:"nameRu",headerName:"\u0420\u0443\u0441\u0441\u043A\u043E\u0435 \u0438\u043C\u044F",type:"string",width:300,editable:!1},{field:"owner",headerName:"Kafedra mudiri",type:"string",width:300,editable:!1,renderCell:e=>{var n,l;return(0,i.jsx)("div",{children:null==e?void 0:(l=e.row)===null||void 0===l?void 0:(n=l.owner)===null||void 0===n?void 0:n.label})}},{field:"room",headerName:"Room",type:"string",width:100,editable:!1},{field:"phone",headerName:"Phone",type:"string",width:200,editable:!1},{field:"service",headerName:"Service",type:"string",width:100,editable:!1,renderCell:e=>(0,i.jsxs)(D,{children:[(0,i.jsx)(V,{onClick:()=>{var n;return Y(null==e?void 0:(n=e.row)===null||void 0===n?void 0:n.id)},children:(0,i.jsx)(h.FiEdit,{})}),(0,i.jsx)(T,{onClick:()=>{var n,l;return Q(null==e?void 0:(n=e.row)===null||void 0===n?void 0:n.id,null==e?void 0:(l=e.row)===null||void 0===l?void 0:l.nameEn)},children:(0,i.jsx)(p.BsTrash,{})})]})}],{headers:G}=(0,a.getHeaders)();(0,t.useEffect)(()=>{J(),q()},[]);let q=()=>{r.default.get(a.BASE_URL+"/kafedra/allKafedra",{headers:G}).then(e=>{var l;o(null==e?void 0:(l=e.data)===null||void 0===l?void 0:l.obj),n(!1)}).catch(e=>{})},J=()=>{r.default.get(a.BASE_URL+"/dekanat/getDatasForSavedKafedraMudiri").then(e=>{var n,l,o,i,t,d;m(null==e?void 0:(l=e.data)===null||void 0===l?void 0:(n=l.obj)===null||void 0===n?void 0:n.users),E(null==e?void 0:(i=e.data)===null||void 0===i?void 0:(o=i.obj)===null||void 0===o?void 0:o.roles),U(null==e?void 0:(d=e.data)===null||void 0===d?void 0:(t=d.obj)===null||void 0===t?void 0:t.positions)}).catch(e=>{})},Q=(e,n)=>{z({id:null,nameEn:null,nameRu:null,nameUz:null,owner:null,room:null,phone:null,roles:[],positions:[]}),window.confirm("Do you want to delete ".concat(n,"?"))&&(r.default.delete("".concat(a.BASE_URL,"/kafedra/deleteKafedra/").concat(e),{headers:G}).then(n=>{var i;o(null==l?void 0:l.filter(n=>(null==n?void 0:n.id)!==e)),u.toast.success(null==n?void 0:(i=n.data)===null||void 0===i?void 0:i.message,{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"})}).catch(e=>{}),u.toast.success("Deleted successfully",{position:"top-right",autoClose:!1,hideProgressBar:!1,closeOnClick:!0,pauseOnHover:!0,draggable:!0,progress:void 0,theme:"colored"}))},W=e=>{e.length>4?r.default.get(a.BASE_URL+"/user/getUsersForUserRole?keyword="+e,{headers:G}).then(e=>{var n;m(((n=e.data)===null||void 0===n?void 0:n.obj).reduce((e,n)=>e.find(e=>e.label===n.label)?e:e.concat([n]),[]))}).catch(e=>{}):m([])},X=()=>{y(!1)},Y=e=>{y(!0),r.default.get("".concat(a.BASE_URL,"/kafedra/getKafedraV3ById?id=").concat(e),{headers:G}).then(e=>{var n,l;L(null==e?void 0:(l=e.data)===null||void 0===l?void 0:(n=l.obj)===null||void 0===n?void 0:n.owner),z(n=>{var l,o,i,t,d,a,r,u,s,c,v,h,p,f,m,g,x,b,j,w,k;return{...n,id:null==e?void 0:(o=e.data)===null||void 0===o?void 0:(l=o.obj)===null||void 0===l?void 0:l.id,nameEn:null==e?void 0:(t=e.data)===null||void 0===t?void 0:(i=t.obj)===null||void 0===i?void 0:i.nameEn,nameRu:null==e?void 0:(a=e.data)===null||void 0===a?void 0:(d=a.obj)===null||void 0===d?void 0:d.nameRu,nameUz:null==e?void 0:(u=e.data)===null||void 0===u?void 0:(r=u.obj)===null||void 0===r?void 0:r.nameUz,owner:null==e?void 0:(c=e.data)===null||void 0===c?void 0:(s=c.obj)===null||void 0===s?void 0:s.owner,ownerId:null==e?void 0:(p=e.data)===null||void 0===p?void 0:(h=p.obj)===null||void 0===h?void 0:(v=h.owner)===null||void 0===v?void 0:v.value,room:null==e?void 0:(m=e.data)===null||void 0===m?void 0:(f=m.obj)===null||void 0===f?void 0:f.room,phone:null==e?void 0:(x=e.data)===null||void 0===x?void 0:(g=x.obj)===null||void 0===g?void 0:g.phone,roles:null==e?void 0:(j=e.data)===null||void 0===j?void 0:(b=j.obj)===null||void 0===b?void 0:b.roles,positions:null==e?void 0:(k=e.data)===null||void 0===k?void 0:(w=k.obj)===null||void 0===w?void 0:w.positions}})}).catch(e=>{})};(0,t.useEffect)(()=>{e&&q()},[e]),(0,t.useEffect)(()=>{},[_]);let Z=e=>{z(n=>({...n,[e.target.name]:e.target.value}))},$=e=>{delete e.owner,delete e.id,Object.values(e).every(e=>null!==e&&e)?r.default.post(a.BASE_URL+"/kafedra/createKafedraV2",{...e,id:null},{headers:G}).then(n=>{y(!1),q(),u.toast.success("".concat(null==e?void 0:e.name," kafedra has been saved successfully!."))}).catch(e=>{var n,l;u.toast.error(null==e?void 0:(l=e.response)===null||void 0===l?void 0:(n=l.data)===null||void 0===n?void 0:n.message)}):u.toast.warning("Empty any field..")},ee=e=>{delete e.owner,Object.values(e).every(e=>null!==e&&e)?r.default.post(a.BASE_URL+"/kafedra/createKafedraV2",e,{headers:G}).then(e=>{y(!1),q(),u.toast.success("Updated kafedra successfully!.")}).catch(e=>{}):u.toast.warning("Empty any field..")};return(0,i.jsxs)(F,{children:[(0,i.jsx)(x.default,{display:"flex",justifyContent:"end",children:(0,i.jsx)(S.default,{sx:{width:"220px"},variant:"contained",onClick:()=>{L(null),z({id:null,nameEn:null,nameRu:null,nameUz:null,owner:null,room:null,phone:null,roles:[],positions:[]}),y(!0)},children:" Add new department"})}),e&&(null==l?void 0:l.length)>0?(0,i.jsx)(s.default,{}):(0,i.jsx)(R.Card,{children:(0,i.jsx)(R.CardContent,{children:(0,i.jsx)(c.DataGrid,{checkboxSelection:!0,sx:{minHeight:"670px!important",overflow:"scroll"},columns:M,rows:l,components:{Toolbar:c.GridToolbar},pageSize:10,initialState:{...l.initialState,columns:{columnVisibilityModel:{id:!1,nameRu:!1}}}})})}),(0,i.jsx)(w.default,{open:g,onClose:X,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,i.jsxs)(x.default,{sx:P,component:K,children:[(0,i.jsx)(N,{onClick:X,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,i.jsx)(f.RiCloseLine,{})}),(0,i.jsx)("h3",{style:{color:"".concat(a.mainColor)},children:(null==_?void 0:_.id)!==null&&(null==_?void 0:_.id)?"Update kafedra":"Create new kafedra"}),(0,i.jsxs)(H,{children:[(0,i.jsx)(b.default,{id:"outlined-basic",label:"English name",variant:"outlined",value:null==_?void 0:_.nameEn,name:"nameEn",onChange:Z}),(0,i.jsx)(b.default,{id:"outlined-basic",label:"\u0420\u0443\u0441\u0441\u043A\u043E\u0435 \u0438\u043C\u044F",variant:"outlined",value:null==_?void 0:_.nameRu,name:"nameRu",onChange:Z}),(0,i.jsx)(b.default,{id:"outlined-basic",label:"O`zbekcha nomi",variant:"outlined",value:null==_?void 0:_.nameUz,name:"nameUz",onChange:Z}),(0,i.jsx)(j.default,{id:"checkboxes-tags-demo",options:d,onFocus:()=>m([]),disableCloseOnSelect:!0,getOptionLabel:e=>null==e?void 0:e.label,value:B,onChange:(e,n)=>{L(n),z(e=>({...e,ownerId:null==n?void 0:n.value}))},onInputChange:(e,n)=>{(null==e?void 0:e.type)==="change"&&W(n)},renderOption:(e,n,l)=>{let{selected:o}=l;return(0,i.jsxs)("li",{...e,children:[(0,i.jsx)(k.default,{icon:I,checkedIcon:A,style:{marginRight:8},checked:o}),null==n?void 0:n.label]})},renderInput:e=>(0,i.jsx)(b.default,{...e,label:"Owner",placeholder:"Owner"})}),(0,i.jsx)(b.default,{id:"outlined-basic",label:"Room",variant:"outlined",value:null==_?void 0:_.room,name:"room",onChange:Z}),(0,i.jsx)(b.default,{id:"outlined-basic",label:"Phone",variant:"outlined",value:null==_?void 0:_.phone,name:"phone",onChange:Z}),(0,i.jsx)(j.default,{multiple:!0,id:"checkboxes-tags-demo",options:C,disableCloseOnSelect:!0,getOptionLabel:e=>e,value:null==_?void 0:_.roles,onChange:(e,n)=>{z(e=>({...e,roles:n}))},renderOption:(e,n,l)=>{let{selected:o}=l;return(0,i.jsxs)("li",{...e,children:[(0,i.jsx)(k.default,{icon:I,checkedIcon:A,style:{marginRight:8},checked:o}),n]})},renderInput:e=>(0,i.jsx)(b.default,{...e,label:"Roles",placeholder:"Roles"})}),(0,i.jsx)(j.default,{multiple:!0,id:"checkboxes-tags-demo",options:O,disableCloseOnSelect:!0,getOptionLabel:e=>e,value:null==_?void 0:_.positions,onChange:(e,n)=>{z(e=>({...e,positions:n}))},renderOption:(e,n,l)=>{let{selected:o}=l;return(0,i.jsxs)("li",{...e,children:[(0,i.jsx)(k.default,{icon:I,checkedIcon:A,style:{marginRight:8},checked:o}),n]})},renderInput:e=>(0,i.jsx)(b.default,{...e,label:"Positions",placeholder:"Positions"})})]}),(0,i.jsx)(x.default,{sx:{display:"flex",justifyContent:"end"},children:(0,i.jsx)(S.default,{variant:"contained",endIcon:(null==_?void 0:_.id)!==null&&(null==_?void 0:_.id)?(0,i.jsx)(h.FiEdit,{}):(0,i.jsx)(v.FaSave,{}),onClick:()=>(null==_?void 0:_.id)!==null&&(null==_?void 0:_.id)?ee(_):$(_),children:(null==_?void 0:_.id)!==null&&(null==_?void 0:_.id)?"Update":" Save"})})]})})]})}}}]);