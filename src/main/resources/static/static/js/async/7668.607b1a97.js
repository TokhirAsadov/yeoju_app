/*! For license information please see 7668.607b1a97.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["7668"],{773:function(n,t,e){"use strict";e.r(t),e.d(t,{default:function(){return T}});var i=e("7409"),r=e("99282"),o=e("38956"),a=e("58865");e("57327"),e("88449"),e("19894"),e("2490"),e("41539"),e("2707");var u=e("85893"),l=e("67294"),d=e.n(l),c=e("71893"),s=e("28685"),f=e("45184"),p=e("16356"),x=e("55693"),m=e("79352"),h=e("55453"),g=e("97367"),v=e("27233"),b=e("72132"),_=e("94718"),j=e("63750"),D=e("89583"),I=e("5434");function y(){var n=(0,a._)(["\n  width: 30px;\n  height: 30px;\n  display: flex;\n  align-items: center;\n  justify-content: center; \n  background-color: rgba(255,255,255,0.6);\n  border-radius: 50%;\n  color: ",";\n  font-size: 26px;\n  border: none;\n  position: absolute;\n  top: 10px;\n  right: 10px;\n"]);return y=function(){return n},n}function E(){var n=(0,a._)(["\n  width: 100%;\n  display: flex;\n  gap: 10px;\n  margin: 5px!important;\n"]);return E=function(){return n},n}function w(){var n=(0,a._)(["\n  margin-top: 5px;\n  float: ",";\n  width: ",";\n  display: flex;\n  align-items: center;\n  justify-content: flex-start;\n  color: #ffffff;\n  gap: 10px;\n  background-color: ",";\n  padding: ",";\n  font-size: 16px; \n  border: none;\n  border-radius: 8px;\n  cursor: pointer;\n  transition: all 0.2s ease; \n  letter-spacing: 1.2px;\n  \n  &:focus{\n    transform: scale(0.95);\n  }\n"]);return w=function(){return n},n}function A(){var n=(0,a._)(["\n  width: 100%;\n  background-color: #fff;\n  border-radius: 10px;\n  display: flex;\n  align-items: center;\n  justify-content: space-between;\n"]);return A=function(){return n},n}function B(){var n=(0,a._)(["\n  margin: 10px 15px 20px;\n  color: ",";\n"]);return B=function(){return n},n}function S(){var n=(0,a._)(["\n   padding:10px!important; \n"]);return S=function(){return n},n}var k={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:365,bgcolor:"background.paper",boxShadow:24,borderRadius:2,p:4},L=c.default.button(y(),s.mainColor),U=c.default.div(E());c.default.button(w(),function(n){return n.float},function(n){return n.width?n.width:"100%"},function(n){return n.bg},function(n){return n.padding?n.padding:"5px 10px"});var N=c.default.div(A()),C=c.default.h3(B(),s.mainColor),z=c.default.div(S()),T=function(){var n=(0,o._)((0,l.useState)(!0),2),t=n[0],e=n[1],a=(0,o._)((0,l.useState)({}),2),c=a[0],y=a[1],E=[{field:"id",headerName:"ID",width:40,editable:!1},{field:"name",headerName:"Name",type:"string",width:300,editable:!1},{field:"state",headerName:"State",type:"string",width:240,editable:!1,renderCell:function(n){return(0,u.jsxs)(U,{children:[(0,u.jsx)(_.default,{size:"small",startIcon:(0,u.jsx)(D.FaEdit,{size:17}),variant:"contained",onClick:function(t){t.preventDefault(),Y(n.id)},children:"Update"}),(0,u.jsx)(_.default,{size:"small",startIcon:(0,u.jsx)(I.MdDelete,{size:17}),variant:"contained",color:"error",onClick:function(t){t.preventDefault(),P(n.id)},children:"Delete"})]})}}],w=(0,o._)((0,l.useState)([{id:1,name:"Tohir Asadov",state:"123456"},{id:2,name:"Tohir Asadov",state:"123456"}]),2),A=w[0],B=w[1],S=(0,o._)(d().useState(!1),2),T=S[0],G=S[1],M=function(){return G(!0)},R=function(){G(!1)},F=(0,s.getHeaders)().headers,H=function(n){(null==n?void 0:n.id)===""&&(n.id=null),v.default.post(s.BASE_URL+s.ADMIN.BUILDING_SAVE,n,{headers:F}).then(function(n){b.toast.success("Building success saved"),q()}).catch(function(n){console.log(n),b.toast.error(n.message)})},P=function(n){v.default.get(s.BASE_URL+s.ADMIN.BUILDING_GET_ELEMENT_BY_ID+n,{headers:F}).then(function(t){if(200===t.status){var e,i;window.confirm("Do you agree to delete "+(null===(i=t.data)||void 0===i?void 0:null===(e=i.obj)||void 0===e?void 0:e.name)+" building?")&&V(n)}}).catch(function(n){console.log(n)})},V=function(n){v.default.delete(s.BASE_URL+s.ADMIN.BUILDING_DELETE+n,{headers:F}).then(function(t){204===t.status&&(b.toast.success("Deleted building successfully!..."),B(A.filter(function(t){return t.id!==n})),q())}).catch(function(n){console.log(n)})},Y=function(n){v.default.get(s.BASE_URL+s.ADMIN.BUILDING_GET_ELEMENT_BY_ID+n,{headers:F}).then(function(n){var t;y(n.data.obj),(null===(t=n.data)||void 0===t?void 0:t.obj)!==null&&M()}).catch(function(n){console.log(n)})},W=function(n){v.default.put(s.BASE_URL+s.ADMIN.BUILDING_UPDATE,n,{headers:F}).then(function(n){202===n.status&&b.toast.success("Building updated successfully!..."),q()}).catch(function(n){console.log(n)})};(0,l.useEffect)(function(){q()},[]);var q=function(){v.default.get(s.BASE_URL+s.ADMIN.ALL_BUILDINGS,{headers:F}).then(function(n){var t=n.data.obj.sort(function(n,t){return n.name>t.name?1:-1});e(!1),B(t)}).catch(function(n){console.log(n)})};return(0,u.jsxs)(z,{children:[(0,u.jsxs)(x.default,{sx:{display:"flex",justifyContent:"space-between",alignItems:"center"},children:[(0,u.jsxs)(C,{children:[(0,u.jsx)(D.FaListUl,{size:22})," Table of Buildings"]}),(0,u.jsx)(_.default,{sx:{margin:"0 0 0 auto"},variant:"contained",color:"success",onClick:function(){y((0,r._)((0,i._)({},c),{id:null,name:null})),M()},endIcon:(0,u.jsx)(j.BsBuildingAdd,{}),children:" Add Building"})]}),(0,u.jsx)(g.default,{open:T,onClose:R,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,u.jsxs)(x.default,{sx:k,children:[(0,u.jsx)(L,{onClick:R,children:(0,u.jsx)(m.RiCloseLine,{})}),(0,u.jsx)(h.default,{title:(null==c?void 0:c.id)!==null?"UPDATE BUILDING":"ADD BUILDING",formArr:[{label:"id",name:"id",placeholder:"Enter id of build",type:"text"},{label:"add build",name:"name",placeholder:"Enter name of build",type:"text"}],submitBtn:(null==c?void 0:c.id)!==null?"Update":"Save",onSubmit:function(n){return(null==c?void 0:c.id)!==null?W(n):H(n)},updateItem:c})]})}),(0,u.jsx)(N,{children:t?(0,u.jsx)(f.default,{}):(0,u.jsx)(p.DataGrid,{checkboxSelection:!0,style:{width:"500px!important",minHeight:"300px!important"},columns:E,rows:A,components:{Toolbar:p.GridToolbar},autoHeight:!0,pageSize:50,initialState:(0,r._)((0,i._)({},A.initialState),{columns:{columnVisibilityModel:{id:!1,login:!1,card:!1,passport:!1}}})})})]})}},55453:function(n,t,e){"use strict";e.r(t),e.d(t,{default:function(){return L}});var i=e("27412"),r=e("7409"),o=e("99282"),a=e("38956"),u=e("58865");e("85827"),e("25387"),e("72608"),e("2490"),e("41539"),e("21249"),e("57640"),e("9924"),e("23157");var l=e("85893"),d=e("67294"),c=e("71893"),s=e("39711"),f=e("28685"),p=e("89583");function x(){var n=(0,u._)(["\n  width: 100%;\n  color: ",";\n"]);return x=function(){return n},n}function m(){var n=(0,u._)(["\n  display: flex;\n  flex-direction: column;\n  gap: 10px;\n"]);return m=function(){return n},n}function h(){var n=(0,u._)(["\n  border: 1px solid ",";\n  margin: 0 auto;\n  height: 40px;\n  width: 200px;\n  padding-left: 10px!important;\n  border-radius: 5px;\n  display: ",";\n  \n  &::placeholder{\n    font-size: 14px;\n    letter-spacing: 1.1px;\n    color: ",";\n  }\n  &:focus{\n    outline: none;\n  }\n"]);return h=function(){return n},n}function g(){var n=(0,u._)(["\n  width: 200px;\n  margin: 0 auto;\n  height: 30px;\n  margin-top: 30px!important;\n  background-color: ",";\n  color: white;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  gap: 10px;\n  font-size: 16px;\n  letter-spacing: 0.7px;\n  font-weight: 400;\n  cursor: pointer;\n  transition: all ease 0.4s;\n  border: none;\n  border-radius: 5px;\n  \n  &:focus{\n    transform: scale(0.9);\n  }\n"]);return g=function(){return n},n}function v(){var n=(0,u._)(["\n  font-size: 12px;\n  width: 100%;\n  display: flex;\n  justify-content: center;\n  margin-top: 10px!important;\n"]);return v=function(){return n},n}function b(){var n=(0,u._)(["\n\n"]);return b=function(){return n},n}function _(){var n=(0,u._)(["\n  font-size: 18px;\n  color: ",";\n  margin-bottom: 20px!important;\n"]);return _=function(){return n},n}function j(){var n=(0,u._)(["\n  text-decoration: none;\n  color: blue;\n"]);return j=function(){return n},n}var D=function(n){return n.reduce(function(n,t){return(0,o._)((0,r._)({},n),(0,i._)({},t.name,""))},{})},I=c.default.form(x(),f.mainColor),y=c.default.div(m()),E=c.default.input(h(),f.mainColor,function(n){return n.display?"none":""},f.mainColor),w=c.default.button(g(),f.mainColor),A=c.default.div(v()),B=c.default.span(b()),S=c.default.h3(_(),f.mainColor),k=(0,c.default)(s.Link)(j()),L=function(n){var t=n.title,e=n.formArr,u=n.submitBtn,c=n.onSubmit,s=n.updateItem,f=n.redirect,x=D(e),m=(0,a._)((0,d.useState)(D(e)),2),h=m[0],g=m[1];return(0,d.useEffect)(function(){(null==s?void 0:s.name)?g((0,o._)((0,r._)({},h),{id:null==s?void 0:s.id,name:null==s?void 0:s.name})):g((0,o._)((0,r._)({},h),{id:null==s?void 0:s.id,nameEn:null==s?void 0:s.nameEn,nameRu:null==s?void 0:s.nameRu,nameUz:null==s?void 0:s.nameUz}))},[]),(0,l.jsxs)(I,{children:[(0,l.jsx)(S,{children:t}),(0,l.jsx)(y,{children:null==e?void 0:e.map(function(n,t){n.label;var e=n.name,a=n.placeholder,u=n.type;return n.value,(0,l.jsx)(E,{display:"id"===e,id:e,name:e,type:u,value:h[e],onChange:function(n){var t;return t=n,g(function(n){return(0,o._)((0,r._)({},n),(0,i._)({},t.target.name,t.target.value))})},placeholder:a},t)})}),(0,l.jsxs)(w,{onClick:function(n){n.preventDefault(),c(h,function(){g(x)})},children:[t.startsWith("UPDATE")?(0,l.jsx)(p.FaEdit,{}):(0,l.jsx)(p.FaSave,{}),u]}),!!f&&(0,l.jsxs)(A,{children:[(0,l.jsxs)(B,{children:[f.label,"\xa0"]}),(0,l.jsx)(k,{to:f.link.to,children:f.link.label})]})]})}}}]);