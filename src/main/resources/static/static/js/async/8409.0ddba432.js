/*! For license information please see 8409.0ddba432.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["8409"],{23228:function(n,t,e){"use strict";e.r(t),e.d(t,{default:function(){return ni}});var r=e("76150"),i=e("38956"),o=e("58865"),l=e("37098"),a=e("18965");e("91038"),e("78783"),e("66992"),e("33948"),e("41539"),e("21249"),e("57640"),e("9924"),e("92222"),e("39714"),e("15581"),e("34514"),e("2490"),e("57658"),e("91058");var u=e("85893"),c=e("67294"),s=e("30381"),d=e.n(s),f=e("71893"),h=e("28685"),x=e("94718"),p=e("45155"),v=e("27233"),m=e("55693"),g=e("7104"),j=e("79352"),w=e("97367"),y=e("71042"),_=e("50533"),b=e("4590"),k=e("90650"),S=e("71632"),C=e("37595"),F=e("70231");function Y(){var n=(0,o._)(["\n  flex: 3;\n  display: flex;\n  font-size: ",";\n  border: 1px solid ",";\n  align-items: center;\n  justify-content: center;\n  padding: 5px 10px !important;\n"]);return Y=function(){return n},n}function D(){var n=(0,o._)(["\n  width: 30px;\n  height: 30px;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  background-color: ",";\n  border-radius: 50%;\n  color: white;\n  font-size: 26px;\n  border: none;\n  position: absolute;\n  top: 10px;\n  right: 10px;\n"]);return D=function(){return n},n}function M(){var n=(0,o._)(["\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  font-size: 26px;\n  color: ",";\n"]);return M=function(){return n},n}function z(){var n=(0,o._)(["\n  display: flex;\n  gap: 20px;\n"]);return z=function(){return n},n}function E(){var n=(0,o._)(["\n  width: 100%;\n  min-height: 40px;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  font-size: 24px;\n  font-weight: bold;\n  border: 1px solid ",";\n  color: ",";\n  background-color: ",";\n  transition: 0.1s all ease-in;\n\n  &:hover {\n    filter: brightness(.7);\n  }\n\n  &:focus {\n    transform: scale(0.95);\n  }\n"]);return E=function(){return n},n}function T(){var n=(0,o._)(["\n  font-size: 12px;\n  width: 100%;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n"]);return T=function(){return n},n}function N(){var n=(0,o._)(["\n  width: 100%;\n  min-height: 40px;\n  border: 1px solid ",";\n  display: flex;\n  align-items: center;\n  flex-direction: column;\n"]);return N=function(){return n},n}function H(){var n=(0,o._)(["\n  flex: 1;\n  height: 47px;\n  display: flex;\n  //flex-direction: column;\n"]);return H=function(){return n},n}function A(){var n=(0,o._)(["\n  display: flex;\n  width: 1200px;\n  margin: 0 auto;\n"]);return A=function(){return n},n}function I(){var n=(0,o._)(["\n  width: 100%;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  gap: 10px;\n  font-size: 24px;\n"]);return I=function(){return n},n}function R(){var n=(0,o._)(["\n  width: 100%;\n  overflow: scroll;\n  display: flex;\n  flex-direction: column;\n  margin-top: 25px !important;\n"]);return R=function(){return n},n}function G(){var n=(0,o._)(["\n  width: 800px;\n  padding: 5px 10px !important;\n"]);return G=function(){return n},n}function L(){var n=(0,o._)(["\n  ","\n  ","\n"]);return L=function(){return n},n}var U={width:"1200px",maxHeight:318,margin:"0 auto"},B=f.default.div(Y(),function(n){return n.sz?"12px":"8px"},h.mainColor),P=(0,f.default)(k.motion.button)(D(),h.mainColor),X=f.default.div(M(),h.mainColor),q=f.default.div(z()),J=f.default.button(E(),h.mainColor,function(n){return n.bg?"#fff":n.color?"green":"red"},function(n){return n.bg?n.bg:"#fff"}),K=f.default.span(T()),O=f.default.div(N(),h.mainColor),Q=f.default.div(H()),V=f.default.div(A()),W=f.default.div(I()),Z=f.default.div(R()),$=f.default.div(G()),nn={width:"40px!important",height:"40px!important",fontSize:"24px"},nt={border:"none"},ne={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:"50vw",minHeight:500,bgcolor:"background.paper",boxShadow:24,borderRadius:3,p:4},nr=f.default.div(L(),(0,S.small)({width:"80vw !important"}),(0,S.extrasmall)({width:"90vw !important"})),ni=function(n){var t,e=n.userName,o=n.kafedraId,s=n.date,f=n.forUser,k=n.url,S=n.isTeacher,Y=n.colName,D=(0,i._)((0,c.useState)(s),2),M=D[0],z=D[1],E=(0,i._)((0,c.useState)([]),2),T=E[0],N=E[1],H=(0,i._)((0,c.useState)(null),2),A=H[0],I=H[1],R=(0,i._)((0,c.useState)(null),2),G=R[0],L=R[1],ni=(0,i._)((0,c.useState)(null),2),no=ni[0],nl=ni[1],na=(0,i._)((0,c.useState)(!1),2),nu=na[0],nc=na[1],ns=function(n,t){var e;L(new Date(M.getFullYear(),M.getMonth(),n)),nl(null===(e=A[t])||void 0===e?void 0:e.monthly[n]),nc(!0)},nd=function(){nc(!1)},nf=(0,h.getHeaders)().headers;(0,c.useEffect)(function(){N(function(){return Array.from(Array(d()(M).daysInMonth()).keys()).map(function(n){return n+1})}),nb()},[M]);var nh=(0,i._)((0,c.useState)([]),2),nx=nh[0],np=nh[1],nv=function(){null==A||A.map(function(n){var t=0;return null==T||T.map(function(e){(null==n?void 0:n.monthly[e])!=null&&t++}),np(function(n){return(0,l._)(n).concat([t])}),n})};(0,c.useEffect)(function(){I(S?nk:nS)},[]);var nm=(0,i._)((0,c.useState)(!1),2),ng=nm[0],nj=nm[1];(0,c.useEffect)(function(){np([]),nv()},[ng]);var nw=(0,i._)((0,c.useState)(!0),2),ny=nw[0],n_=nw[1];var nb=(t=(0,r._)(function(){return(0,a._)(this,function(n){switch(n.label){case 0:var t;return n_(!0),[4,v.default.get("".concat(h.BASE_URL+k+o,"&date=").concat((t=M).getFullYear()+"."+(t.getMonth()+1)+"."+t.getDate()),{headers:nf}).then(function(n){var t,e;I(null==n?void 0:null===(e=n.data)||void 0===e?void 0:null===(t=e.obj)||void 0===t?void 0:t.all),n_(!1),nj(function(n){return!n})}).catch(function(n){console.log(n)})];case 1:return n.sent(),[2]}})}),function(){return t.apply(this,arguments)}),nk=(0,_.useSelector)(function(n){var t,e;return null==n?void 0:null===(e=n.rektorTeachers)||void 0===e?void 0:null===(t=e.rektorTeachers)||void 0===t?void 0:t.allTeachers}),nS=(0,_.useSelector)(function(n){var t,e;return null==n?void 0:null===(e=n.rektorStaffs)||void 0===e?void 0:null===(t=e.rektorStaffs)||void 0===t?void 0:t.allStaffs});return(0,u.jsx)(u.Fragment,{children:(0,u.jsxs)($,{style:{width:"".concat(f||"100%")},children:[(0,u.jsx)(q,{children:(0,u.jsx)(X,{children:e})}),(0,u.jsx)("hr",{}),(0,u.jsxs)(W,{children:[(0,u.jsx)(x.default,{style:nn,onClick:function(){z(new Date(M.getFullYear(),M.getMonth()-1))},children:(0,u.jsx)(p.GrFormPrevious,{})}),(0,u.jsx)("input",{type:"month",id:"start",name:"start",min:"2022-04",value:d()(M).format("YYYY-MM"),onChange:function(n){return z(new Date(n.target.value))},style:nt}),(0,u.jsx)(x.default,{style:nn,onClick:function(){z(new Date(M.getFullYear(),M.getMonth()+1))},children:(0,u.jsx)(p.GrFormNext,{})})]}),(0,u.jsx)(Z,{children:ny?(0,u.jsx)(g.Skeleton,{animation:"wave",width:f||"100%",height:120}):(0,u.jsxs)(u.Fragment,{children:[(0,u.jsx)(V,{children:null==T?void 0:T.map(function(n,t){return(0,u.jsxs)(u.Fragment,{children:[1===n&&(0,u.jsx)(B,{sz:!0,children:Y},t),(0,u.jsx)(Q,{children:(0,u.jsxs)(O,{children:[n,(0,u.jsx)(K,{children:d()(new Date(M.getFullYear(),M.getMonth(),n)).format("ddd")})]})},n),n===(null==T?void 0:T.length)&&(0,u.jsx)(Q,{children:(0,u.jsx)(O,{children:"\u2211"})})]})})}),(0,u.jsx)(b.default,{style:U,children:null==A?void 0:A.map(function(n,t){return(0,u.jsxs)(V,{children:[(0,u.jsx)(B,{children:n.fullName}),null==T?void 0:T.map(function(e){return(0,u.jsxs)(u.Fragment,{children:[(0,u.jsx)(Q,{children:(0,u.jsx)(J,{color:n.monthly[e],onClick:function(){return ns(e,t)},children:new Date(M.getFullYear(),M.getMonth(),e)<=new Date?n.monthly[e]?(0,u.jsx)(F.FaCircleCheck,{}):(0,u.jsx)(F.FaCircleXmark,{}):""})},e),e===(null==T?void 0:T.length)&&(0,u.jsx)(Q,{children:(0,u.jsx)(J,{color:0!==nx[t],bg:0!==nx[t]?"green":"red",children:nx[t]})})]})})]})})})]})}),(0,u.jsx)(m.default,{display:"flex",justifyContent:"end",mt:1,children:(0,u.jsx)(x.default,{onClick:function(){if(A){var n=[[Y].concat((0,l._)(T.map(function(n){return n.toString()})),["Total"])].concat((0,l._)(A.map(function(n){var t=[n.fullName],e=0;return T.forEach(function(r){if(new Date(M.getFullYear(),M.getMonth(),r)<=new Date){var i=n.monthly[r]?"1":"0";t.push(i),e+=parseInt(i)}else t.push("")}),t.push(e.toString()),t}))),t=C.utils.aoa_to_sheet(n),e=C.utils.book_new();C.utils.book_append_sheet(e,t,"Sheet1"),C.writeFile(e,"exported_data.xlsx")}},variant:"contained",children:"Export"})}),(0,u.jsx)(w.default,{open:nu,onClose:nd,"aria-labelledby":"keep-mounted-modal-title","aria-describedby":"keep-mounted-modal-description",children:(0,u.jsxs)(m.default,{sx:ne,component:nr,children:[(0,u.jsx)(P,{onClick:nd,whileHover:{rotate:180,scale:1.1},whileTap:{scale:.9},transition:{duration:.3},children:(0,u.jsx)(j.RiCloseLine,{})}),(0,u.jsx)(y.default,{time:G,item:no})]})})]})})}}}]);