/*! For license information please see 1139.d1152b9e.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["1139"],{99879:function(n,e,t){"use strict";t.r(e);var i=t("58865");t("33948"),t("88449"),t("19894"),t("2490"),t("57640"),t("9924"),t("17727");var o=t("85893"),a=t("67294"),l=t.n(a),r=t("71893"),d=t("16393"),u=t("16356"),s=t("97367"),c=t("79352"),f=t("55693"),p=t("51772"),m=t("27233"),x=t("72132"),h=t("57024"),g=t("94718"),b=t("5434"),v=t("89583");function j(){let n=(0,i._)(["\n    width: 30px;\n    height: 30px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    background-color: rgba(255, 255, 255, 0.6);\n    border-radius: 50%;\n    color: ",";\n    font-size: 26px;\n    border: none;\n    position: absolute;\n    top: 10px;\n    right: 10px;\n"]);return j=function(){return n},n}function _(){let n=(0,i._)(["\n    width: 100%;\n    display: flex;\n    gap: 10px;\n    margin: 5px !important;\n"]);return _=function(){return n},n}function y(){let n=(0,i._)(["\n    margin-top: 1rem;\n    width: 100%;\n    background-color: #fff;\n    border-radius: 10px;\n    display: flex;\n    align-items: center;\n    justify-content: space-between;\n"]);return y=function(){return n},n}function E(){let n=(0,i._)(["\n    padding: 0 10px;\n    display: flex;\n    align-items: center;\n    justify-content: space-between;\n\n    p {\n        color: ",";\n        font-size: 25px;\n        font-weight: bold;\n\n        ","\n\n    }\n"]);return E=function(){return n},n}function w(){let n=(0,i._)(["\n    padding: 1rem;\n    .MuiDataGrid-columnHeaderTitleContainer {\n        justify-content: center;\n    }\n"]);return w=function(){return n},n}let A={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:365,bgcolor:"background.paper",borderRadius:2,boxShadow:24,p:4},C=r.default.button(j(),d.mainColor),D=r.default.div(_()),R=r.default.div(y()),k=r.default.h1(E(),d.mainColor,(0,h.extrasmall)({textAlign:"start",fontSize:"15px"})),S=r.default.div(w());e.default=()=>{let[n,e]=(0,a.useState)(!1),[t,i]=l().useState(!1),[r,h]=(0,a.useState)([]),{headers:j}=(0,d.getHeaders)(),_=()=>i(!0),y=()=>i(!1),[E,w]=(0,a.useState)({}),M=[{field:"count",headerName:"\u2116",width:40,align:"center",justifyContent:"center",editable:!1},{field:"name",headerName:"Name",width:300,align:"center",justifyContent:"center",editable:!1},{field:"actions",headerName:"Actions",width:240,align:"center",justifyContent:"center",editable:!1,renderCell:n=>(0,o.jsxs)(D,{children:[(0,o.jsx)(g.default,{startIcon:(0,o.jsx)(v.FaEdit,{size:17}),size:"small",variant:"contained",onClick:e=>{e.preventDefault(),T(n.id)},children:"Update"}),(0,o.jsx)(g.default,{startIcon:(0,o.jsx)(v.FaTrash,{size:17}),size:"small",variant:"contained",color:"error",onClick:e=>{e.preventDefault(),z(n.id)},children:"Delete"})]})}],O=n=>{m.default.post(d.BASE_URL+d.ADMIN.ROOM_SAVE,n,{headers:j}).then(n=>{x.toast.success("Room success saved"),I()}).catch(n=>{x.toast.error(n.message)})},z=n=>{m.default.get(d.BASE_URL+d.ADMIN.ROOM_GET_ELEMENT_BY_ID+n,{headers:j}).then(e=>{if(200===e.status){var t,i;window.confirm("Do you agree to delete "+(null===(i=e.data)||void 0===i?void 0:null===(t=i.obj)||void 0===t?void 0:t.name)+" room?")&&L(n)}}).catch(n=>{})},L=n=>{m.default.delete(d.BASE_URL+d.ADMIN.ROOM_DELETE+n,{headers:j}).then(e=>{204===e.status&&(x.toast.success("Deleted room successfully!..."),h(r.filter(e=>e.id!==n)),I())}).catch(n=>{})},T=n=>{m.default.get(d.BASE_URL+d.ADMIN.ROOM_GET_ELEMENT_BY_ID+n,{headers:j}).then(n=>{var e;w(n.data.obj),(null===(e=n.data)||void 0===e?void 0:e.obj)!==null&&_()}).catch(n=>{})},U=n=>{m.default.put(d.BASE_URL+d.ADMIN.ROOM_UPDATE,n,{headers:j}).then(n=>{202===n.status&&x.toast.success("Room updated successfully!..."),I()}).catch(n=>{})};(0,a.useEffect)(()=>{I()},[]);let I=()=>{e(!0),m.default.get(d.BASE_URL+d.ADMIN.ALL_ROOMS,{headers:j}).then(n=>{var e;h(null===(e=n.data.obj.sort((n,e)=>n.name>e.name?1:-1))||void 0===e?void 0:e.map((n,e)=>({...n,count:e+1})))}).catch(n=>{}).finally(()=>{e(!1)})};return(0,o.jsxs)(S,{children:[(0,o.jsx)(s.default,{open:t,onClose:y,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,o.jsxs)(f.default,{sx:A,children:[(0,o.jsx)(C,{onClick:y,children:(0,o.jsx)(c.RiCloseLine,{})}),(0,o.jsx)(p.default,{title:(null==E?void 0:E.id)!==null?"UPDATE ROOM":"ADD ROOM",formArr:[{label:"add room",name:"name",placeholder:"Enter name of room",type:"text"}],submitBtn:(null==E?void 0:E.id)!==null?"Update":"Save",updateItem:E,onSubmit:n=>(null==E?void 0:E.id)!==null?U(n):O(n)})]})}),(0,o.jsxs)(k,{children:[(0,o.jsxs)("p",{children:[(0,o.jsx)(v.FaList,{})," Table of Rooms"]}),(0,o.jsx)(g.default,{variant:"contained",color:"success",endIcon:(0,o.jsx)(b.MdAddHome,{}),onClick:()=>{w({...E,id:null,name:null}),_()},children:" Add Room"})]}),(0,o.jsx)(R,{children:(0,o.jsx)(u.DataGrid,{style:{width:"500px!important",minHeight:"300px!important"},columns:M,loading:n,rows:r,components:{Toolbar:u.GridToolbar},autoHeight:!0,rowsPerPageOptions:[25,50,75,100],initialState:{columns:{columnVisibilityModel:{id:!1,login:!1,card:!1,passport:!1}},pagination:{pageSize:25}}})})]})}},51772:function(n,e,t){"use strict";t.r(e);var i=t("58865");t("25387"),t("72608"),t("2490"),t("33948"),t("57640"),t("9924");var o=t("85893"),a=t("67294"),l=t("71893"),r=t("39711"),d=t("16393"),u=t("89583");function s(){let n=(0,i._)(["\n  width: 100%;\n  color: ",";\n"]);return s=function(){return n},n}function c(){let n=(0,i._)(["\n  display: flex;\n  flex-direction: column;\n  gap: 10px;\n"]);return c=function(){return n},n}function f(){let n=(0,i._)(["\n  border: 1px solid ",";\n  margin: 0 auto;\n  height: 40px;\n  width: 200px;\n  padding-left: 10px!important;\n  border-radius: 5px;\n  display: ",";\n  \n  &::placeholder{\n    font-size: 14px;\n    letter-spacing: 1.1px;\n    color: ",";\n  }\n  &:focus{\n    outline: none;\n  }\n"]);return f=function(){return n},n}function p(){let n=(0,i._)(["\n  width: 200px;\n  margin: 0 auto;\n  height: 30px;\n  margin-top: 30px!important;\n  background-color: ",";\n  color: white;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  gap: 10px;\n  font-size: 16px;\n  letter-spacing: 0.7px;\n  font-weight: 400;\n  cursor: pointer;\n  transition: all ease 0.4s;\n  border: none;\n  border-radius: 5px;\n  \n  &:focus{\n    transform: scale(0.9);\n  }\n"]);return p=function(){return n},n}function m(){let n=(0,i._)(["\n  font-size: 12px;\n  width: 100%;\n  display: flex;\n  justify-content: center;\n  margin-top: 10px!important;\n"]);return m=function(){return n},n}function x(){let n=(0,i._)(["\n\n"]);return x=function(){return n},n}function h(){let n=(0,i._)(["\n  font-size: 18px;\n  color: ",";\n  margin-bottom: 20px!important;\n"]);return h=function(){return n},n}function g(){let n=(0,i._)(["\n  text-decoration: none;\n  color: blue;\n"]);return g=function(){return n},n}let b=n=>n.reduce((n,e)=>({...n,[e.name]:""}),{}),v=l.default.form(s(),d.mainColor),j=l.default.div(c()),_=l.default.input(f(),d.mainColor,n=>n.display?"none":"",d.mainColor),y=l.default.button(p(),d.mainColor),E=l.default.div(m()),w=l.default.span(x()),A=l.default.h3(h(),d.mainColor),C=(0,l.default)(r.Link)(g());e.default=n=>{let{title:e,formArr:t,submitBtn:i,onSubmit:l,updateItem:r,redirect:d}=n,s=b(t),[c,f]=(0,a.useState)(b(t)),p=n=>f(e=>({...e,[n.target.name]:n.target.value})),m=()=>l(c,()=>{f(s)});return(0,a.useEffect)(()=>{(null==r?void 0:r.name)?f({...c,id:null==r?void 0:r.id,name:null==r?void 0:r.name}):f({...c,id:null==r?void 0:r.id,nameEn:null==r?void 0:r.nameEn,nameRu:null==r?void 0:r.nameRu,nameUz:null==r?void 0:r.nameUz})},[]),(0,o.jsxs)(v,{children:[(0,o.jsx)(A,{children:e}),(0,o.jsx)(j,{children:null==t?void 0:t.map((n,e)=>{let{label:t,name:i,placeholder:a,type:l,value:r}=n;return(0,o.jsx)(_,{display:"id"===i,id:i,name:i,type:l,value:c[i],onChange:n=>p(n),placeholder:a},e)})}),(0,o.jsxs)(y,{onClick:n=>{n.preventDefault(),m()},children:[e.startsWith("UPDATE")?(0,o.jsx)(u.FaEdit,{}):(0,o.jsx)(u.FaSave,{}),i]}),!!d&&(0,o.jsxs)(E,{children:[(0,o.jsxs)(w,{children:[d.label,"\xa0"]}),(0,o.jsx)(C,{to:d.link.to,children:d.link.label})]})]})}}}]);