/*! For license information please see 662.57fa05c5.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["662"],{64225:function(n,e,t){"use strict";t.r(e),t.d(e,{default:function(){return V}});var l=t("76150"),i=t("7409"),a=t("99282"),o=t("38956"),r=t("58865"),u=t("18965");t("69826"),t("31672"),t("59461"),t("2490"),t("41539"),t("92222"),t("57327"),t("88449"),t("19894"),t("21249"),t("57640"),t("9924");var s=t("85893"),c=t("67294"),d=t.n(c),f=t("71893"),v=t("28685"),h=t("71632"),p=t("79352"),m=t("7104"),x=t("61261"),g=t("55693"),j=t("5434"),b=t("74113"),_=t("63750"),w=t("94718"),y=t("97367"),C=t("60155"),k=t("89589"),S=t("50533"),I=t("27233"),L=t("72132");function E(){var n=(0,r._)(["\n  ","\n"]);return E=function(){return n},n}function A(){var n=(0,r._)(["\n  width: 100%;\n  display: flex;\n  gap: 5px;\n  align-items: start;\n  font-size: 16px;\n  text-align: start;\n  color: ",";\n  font-weight: bold;\n  ","\n"]);return A=function(){return n},n}function U(){var n=(0,r._)(["\n  padding: 15px;\n  display: flex;\n  flex-direction: column;\n  gap: 10px;\n  background-color: #fff;\n  border-radius: 0.5rem;\n  box-shadow: 1px 3px 8px 0 rgba(34, 60, 80, 0.2);\n\n  p {\n    display: flex;\n    align-items: center;\n    color: black;\n    font-size: 14px;\n  }\n\n"]);return U=function(){return n},n}function B(){var n=(0,r._)(["\n  width: 100%;\n  display: grid;\n  grid-template-columns: 1fr 1fr 1fr 1fr;\n  gap: 20px;\n  ","\n  ","\n  ","\n  ","\n  ","\n"]);return B=function(){return n},n}function R(){var n=(0,r._)(["\n  width: 100%;\n  padding: 1rem;\n\n  h2 {\n    color: ",";\n    font-size: 28px;\n  }\n"]);return R=function(){return n},n}var T={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:500,bgcolor:"background.paper",boxShadow:24,padding:"15px",borderRadius:"8px"},z=f.default.div(E(),(0,h.extrasmall)({width:"95% !important"})),N=f.default.div(A(),v.mainColor,(0,h.extrasmall)({fontSize:"14px"})),M=f.default.div(U()),D=f.default.div(B(),(0,h.xlarge)({gridTemplateColumns:"1fr 1fr 1fr  "}),(0,h.large)({gridTemplateColumns:"1fr 1fr 1fr  "}),(0,h.medium)({gridTemplateColumns:"1fr 1fr "}),(0,h.small)({gridTemplateColumns:"1fr 1fr "}),(0,h.extrasmall)({gridTemplateColumns:"1fr "})),P=f.default.div(R(),v.mainColor),V=function(){var n,e,t,r,f,h=(0,S.useSelector)(function(n){var e;return null===(e=n.section)||void 0===e?void 0:e.section}),E=(0,S.useSelector)(function(n){var e;return null===(e=n.user)||void 0===e?void 0:e.user}),A=[{title:"Update",icon:(0,s.jsx)(j.MdUpdate,{color:"#0087be"})},{title:"Delete",icon:(0,s.jsx)(j.MdDelete,{color:"red"})}],U=(0,v.getHeaders)().headers,B=(0,o._)((0,c.useState)({id:null,name:null,kafedraId:null==h?void 0:h.id}),2),R=B[0],V=B[1],F=(0,o._)((0,c.useState)([]),2),H=F[0],q=F[1],K=(0,o._)((0,c.useState)(!1),2),O=K[0],W=K[1],G=(0,o._)(d().useState(null),2),J=G[0],Q=G[1],X=(0,o._)((0,c.useState)(!0),2),Y=X[0],Z=X[1],$=!!J,nn=function(n,e){Q(n.currentTarget),V(function(n){var t;return(0,a._)((0,i._)({},n),{id:e,name:null==H?void 0:null===(t=H.find(function(n){return(null==n?void 0:n.subjectId)===e}))||void 0===t?void 0:t.subjectName})})},ne=function(){return W(!0)},nt=function(){W(!1),V(function(n){return(0,a._)((0,i._)({},n),{name:null,id:null})})};var nl=(n=(0,l._)(function(n){return(0,u._)(this,function(e){switch(e.label){case 0:return[4,I.default.post("".concat(v.BASE_URL,"/lesson/createLessonV2"),n,{headers:U}).then(function(n){var e;L.toast.success(null==n?void 0:null===(e=n.data)||void 0===e?void 0:e.message),ni(),nt()}).catch(function(n){console.log(n)})];case 1:return e.sent(),[2]}})}),function(e){return n.apply(this,arguments)});var ni=(e=(0,l._)(function(){return(0,u._)(this,function(n){switch(n.label){case 0:return[4,I.default.get("".concat(v.BASE_URL,"/lesson/getAllLessonByKafedraOwnerId/").concat(null==E?void 0:E.id),{headers:U}).then(function(n){var e;q(null==n?void 0:null===(e=n.data)||void 0===e?void 0:e.obj),console.log(n.data)}).catch(function(n){console.log(n)})];case 1:return n.sent(),[2]}})}),function(){return e.apply(this,arguments)});(0,c.useEffect)(function(){(null==R?void 0:R.name)&&(null==R?void 0:R.name)!==""?Z(!1):Z(!0)},[R]);var na=function(n){Q(null),"Update"===n?ne():"Delete"===n&&nu(null==R?void 0:R.id)};var no=(t=(0,l._)(function(){return(0,u._)(this,function(n){switch(n.label){case 0:return[4,I.default.get("".concat(v.BASE_URL,"/lesson/checkLessonNameAlreadyExists?subjectName=").concat(null==R?void 0:R.name),{headers:U}).then(function(n){var e,t;(null==n?void 0:null===(e=n.data)||void 0===e?void 0:e.success)?L.toast.error("\u26A0\uFE0F"+(null===(t=n.data)||void 0===t?void 0:t.message)):nr()}).catch(function(n){console.log(n)})];case 1:return n.sent(),[2]}})}),function(){return t.apply(this,arguments)});var nr=(r=(0,l._)(function(){return(0,u._)(this,function(n){switch(n.label){case 0:return[4,I.default.put("".concat(v.BASE_URL,"/lesson/updateLessonV2"),R,{headers:U}).then(function(n){var e,t,l;(null==n?void 0:null===(e=n.data)||void 0===e?void 0:e.success)?L.toast.success(null==n?void 0:null===(t=n.data)||void 0===t?void 0:t.message):L.toast.error(null==n?void 0:null===(l=n.data)||void 0===l?void 0:l.message),ni(),nt()}).catch(function(n){console.log(n)})];case 1:return n.sent(),[2]}})}),function(){return r.apply(this,arguments)});var nu=(f=(0,l._)(function(n){return(0,u._)(this,function(e){switch(e.label){case 0:if(!window.confirm("Are you sure you want to delete lesson?"))return[3,2];return[4,I.default.delete("".concat(v.BASE_URL,"/lesson/deleteLesson/").concat(n),{headers:U}).then(function(e){var t;L.toast.success(null==e?void 0:null===(t=e.data)||void 0===t?void 0:t.message),q(null==H?void 0:H.filter(function(e){return e.subjectId!==n})),V(function(n){return(0,a._)((0,i._)({},n),{name:null,id:null})})}).catch(function(n){console.log(n)})];case 1:return e.sent(),[3,3];case 2:V(function(n){return(0,a._)((0,i._)({},n),{name:null,id:null})}),e.label=3;case 3:return[2]}})}),function(n){return f.apply(this,arguments)});return(0,c.useEffect)(function(){ni()},[]),(0,s.jsxs)(P,{children:[(0,s.jsxs)(g.default,{display:"flex",justifyContent:"space-between",alignItems:"center",mb:2,children:[(0,s.jsx)("h2",{children:"Subjects"}),(0,s.jsx)(w.default,{variant:"contained",onClick:ne,children:"create a new subject"})]}),(0,s.jsx)(D,{children:null==H?void 0:H.map(function(n,e){return(0,s.jsx)(M,{children:(0,s.jsxs)(g.default,{display:"flex",alignItems:"flex-start",children:[(0,s.jsx)(g.default,{width:"100%",children:(0,s.jsxs)(N,{children:[(0,s.jsx)(g.default,{width:"30px",height:"30px",children:(0,s.jsx)(p.RiBookMarkFill,{size:30})}),(0,s.jsx)("span",{children:null==n?void 0:n.subjectName})]})}),(0,s.jsx)(b.default,{"aria-label":"actions",id:"long-button","aria-controls":$?"long-menu":void 0,"aria-expanded":$?"true":void 0,"aria-haspopup":"true",onClick:function(e){return nn(e,null==n?void 0:n.subjectId)},size:"small",children:(0,s.jsx)(_.BsThreeDotsVertical,{})})]})},null==n?void 0:n.subjectId)})}),(0,s.jsx)(y.default,{open:O,onClose:nt,"aria-labelledby":"modal-modal-title","aria-describedby":"modal-modal-description",children:(0,s.jsxs)(g.default,{sx:T,component:z,children:[(0,s.jsxs)(g.default,{display:"flex",justifyContent:"space-between",alignItems:"center",children:[(0,s.jsx)(k.default,{variant:"h6",color:v.mainColor,mt:1,children:(null==R?void 0:R.id)===null?"Create a new subject":"Update subject"}),(0,s.jsxs)(b.default,{onClick:nt,children:[" ",(0,s.jsx)(C.IoClose,{size:22})]})]}),(0,s.jsxs)(g.default,{children:[(0,s.jsx)(m.Stack,{sx:{height:"150px",padding:"0 10px"},direction:"row",justifyContent:"center",alignItems:"center",children:(0,s.jsx)(m.TextField,{fullWidth:!0,value:null==R?void 0:R.name,required:!0,label:"Subject name",onChange:function(n){return V(function(e){return(0,a._)((0,i._)({},e),{name:n.target.value})})}})}),(0,s.jsxs)(m.Stack,{direction:"row",spacing:2,justifyContent:"flex-end",alignItems:"center",children:[(0,s.jsx)(w.default,{variant:"outlined",onClick:nt,children:"Cancel"}),(0,s.jsx)(w.default,{variant:"contained",onClick:(null==R?void 0:R.id)===null?function(){console.log(R,"save method"),I.default.get("".concat(v.BASE_URL,"/lesson/checkLessonNameAlreadyExists?subjectName=").concat(null==R?void 0:R.name),{headers:U}).then(function(n){var e,t,l;console.log(null===(e=n.data)||void 0===e?void 0:e.success),(null==n?void 0:null===(t=n.data)||void 0===t?void 0:t.success)?L.toast.error("\u26A0\uFE0F"+(null===(l=n.data)||void 0===l?void 0:l.message)):nl(R)}).catch(function(n){console.log(n)})}:no,disabled:Y,children:(null==R?void 0:R.id)===null?"save":"update"})]})]})]})}),(0,s.jsx)(m.Menu,{id:"long-menu",MenuListProps:{"aria-labelledby":"long-button"},anchorEl:J,open:$,onClose:na,PaperProps:{style:{maxHeight:180,width:"20ch"}},children:A.map(function(n,e){return(0,s.jsx)(x.default,{onClick:function(){return na(n.title)},children:(0,s.jsxs)(g.default,{display:"flex",gap:"5px",alignItems:"center",children:[n.icon," ",n.title]})},e)})})]})}}}]);