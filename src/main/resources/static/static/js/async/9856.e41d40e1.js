/*! For license information please see 9856.e41d40e1.js.LICENSE.txt */
(self.webpackChunkkiut_client=self.webpackChunkkiut_client||[]).push([["9856"],{68036:function(e,t,n){"use strict";n.r(t);var i=n("58865");n("33948"),n("57640"),n("9924");var l=n("85893"),o=n("67294"),r=n("71893"),a=n("47229"),d=n("16393"),s=n("50533"),u=n("27233"),c=n("53432"),f=n("90650"),h=n("31942");function p(){let e=(0,i._)(["\n  width: 100%;\n  height: 100%;\n  display: flex;\n  align-items: center;\n  justify-content: center;\n  flex-direction: column;\n"]);return p=function(){return e},e}let x=r.default.div(p());t.default=e=>{let{seriesK:t,isSpinner:n}=e,[i,r]=(0,o.useState)(!1),[p,m]=(0,o.useState)(""),[v,g]=(0,o.useState)(!1),[b,j]=(0,o.useState)(""),[y,w]=(0,o.useState)(!1),S=(0,s.useSelector)(e=>{var t;return null==e?void 0:null===(t=e.section)||void 0===t?void 0:t.section}),[C,k]=(0,o.useState)({chart:{type:"donut",width:380,events:{dataPointSelection:function(e,t,n){1===n.dataPointIndex?g(!1):g(!0),1===n.dataPointIndex?j("Absentees"):j("At work"),w(!1),D(n.dataPointIndex),r(!0)}}},labels:["At work","Absentees"],title:{text:"Statistics Of Today",style:{fontSize:"12px",fontWeight:"bold",fontFamily:void 0,color:"".concat(d.mainColor)}},responsive:[{breakpoint:480,options:{chart:{width:200},legend:{position:"bottom"}}}],colors:[function(e){let{value:t,seriesIndex:n,w:i}=e;return"".concat(d.mainColor)},function(e){let{value:t,seriesIndex:n,w:i}=e;return t<i.config.series[0]/5?(m("#fff700"),"#fff700"):t>i.config.series[0]/5&&t<i.config.series[0]/2?(m("#f88017"),"#f88017"):(m("#f00"),"#f00")}],plotOptions:{pie:{expandOnClick:!1,size:400,height:"5%",donut:{size:"55%",labels:{show:!0,total:{show:!0,showAlways:!0,fontSize:"18px",color:"".concat(d.mainColor)}}}}}}),[T,z]=(0,o.useState)([]),D=e=>{u.default.get(d.BASE_URL+"/kafedra/getStatisticsForKafedraDashboard?index="+e+"&kafedraId="+S.id).then(e=>{var t,n;z(null==e?void 0:null===(n=e.data)||void 0===n?void 0:null===(t=n.obj)||void 0===t?void 0:t.map((e,t)=>({...e,id:t}))),w(!0)}).catch(e=>{})};return(0,l.jsxs)(l.Fragment,{children:[i&&(0,l.jsx)(h.default,{setExpanded:()=>r(!1),isDefault:v,openColor:p,title:b,openTable:y,data:T}),(0,l.jsx)(f.motion.div,{layoutId:"kafedraId",style:{width:"100%",height:"100%",display:"flex",alignItems:"center",justifyContent:"center",flexDirection:"column"},children:(0,l.jsx)(x,{children:n?(0,l.jsx)(c.default,{}):(0,l.jsx)(a.default,{options:C,series:t,type:"donut",width:"350"})})})]})}},92355:function(e,t,n){"use strict";n.r(t);var i=n("58865");n("33948"),n("25387"),n("72608"),n("2490"),n("31672"),n("59461"),n("57658"),n("57640"),n("9924");var l=n("85893"),o=n("67294"),r=n("71893"),a=n("68036"),d=n("27233"),s=n("50533"),u=n("16393"),c=n("2585"),f=n("44178"),h=n("30381"),p=n.n(h),x=n("98888"),m=n("63750"),v=n("57024"),g=n("52861"),b=n("74113"),j=n("60155"),y=n("97367"),w=n("55693"),S=n("89589"),C=n("7104"),k=n("94718"),T=n("911"),z=n("72132"),D=n("66270"),I=n("93065");function _(){let e=(0,i._)(["\n    ","\n"]);return _=function(){return e},e}function H(){let e=(0,i._)(["\n    color: ",";\n    position: absolute;\n    top: 40%;\n    right: -8px;\n    font-size: 11px;\n    rotate: 90deg;\n"]);return H=function(){return e},e}function E(){let e=(0,i._)(["\n    font-size: 8px;\n    width: 100%;\n    display: flex;\n    text-align: start;\n"]);return E=function(){return e},e}function N(){let e=(0,i._)(["\n    flex: 1;\n    display: flex;\n    align-items: center;\n    flex-direction: column;\n    gap: 10px;\n    font-size: ",";\n    padding: 0.95rem !important;\n    background-color: ",";\n    color: ",";\n    border: 1px solid lightgray;\n    position: relative;\n"]);return N=function(){return e},e}function R(){let e=(0,i._)(["\n    width: 100%;\n    display: flex;\n"]);return R=function(){return e},e}function A(){let e=(0,i._)(["\n    width: 100%;\n    display: flex;\n    margin: 0 auto;\n    flex-direction: column;\n"]);return A=function(){return e},e}function Y(){let e=(0,i._)(["\n    flex: 1;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    font-size: ",";\n    font-weight: bold;\n    padding: 0.95rem !important;\n    background-color: ",";\n    color: #ffffff;\n    border: 1px solid lightgray;\n"]);return Y=function(){return e},e}function M(){let e=(0,i._)(["\n    flex: 1.6;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    padding: 10px !important;\n    background-color: ",";\n    font-weight: bold;\n    font-size: 12px;\n    color: #ffffff;\n    text-align: center;\n    border: 1px solid lightgray;\n"]);return M=function(){return e},e}function W(){let e=(0,i._)(["\n    width: 100%;\n    display: flex;\n    margin: 0 auto;\n"]);return W=function(){return e},e}function F(){let e=(0,i._)(["\n    width: 100%;\n"]);return F=function(){return e},e}function O(){let e=(0,i._)(["\n    width: 100%;\n    overflow: scroll;\n"]);return O=function(){return e},e}function P(){let e=(0,i._)(["\n    width: 1800px;\n    margin: 0 auto;\n    display: flex;\n    flex-direction: column;\n"]);return P=function(){return e},e}function B(){let e=(0,i._)(["\n    position: absolute;\n    top: 10px;\n    right: 10px;\n    width: 80px;\n    display: flex;\n    justify-content: flex-start;\n    align-items: center;\n"]);return B=function(){return e},e}function L(){let e=(0,i._)(["\n    width: 100px;\n    height: 100px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    font-size: 40px;\n    background: #f7f7f7;\n    border-radius: 50%;\n"]);return L=function(){return e},e}function K(){let e=(0,i._)(["\n    position: relative;\n    width: 100%;\n    height: 100%;\n    display: flex;\n    align-items: center;\n    flex-wrap: wrap;\n    justify-content: space-around;\n    gap: 30px;\n"]);return K=function(){return e},e}function U(){let e=(0,i._)(["\n    padding: 10px;\n    margin: 0 20px;\n    display: flex;\n    align-items: center;\n    flex: 1;\n    border-radius: 0.75rem;\n    background-color: #fff;\n    min-height: 250px;\n    color: ",";\n"]);return U=function(){return e},e}function G(){let e=(0,i._)(["\n    position: relative;\n    flex: 1;\n    margin: 0 20px;\n    display: flex;\n    align-items: center;\n    justify-content: center;\n    border-radius: 0.75rem;\n    background-color: #fff;\n    min-height: 250px;\n"]);return G=function(){return e},e}function V(){let e=(0,i._)(["\n    width: 100%;\n    display: flex;\n    flex-wrap: wrap;\n    gap: 10px;\n    ","\n"]);return V=function(){return e},e}function q(){let e=(0,i._)(["\n    display: block;\n    font-size: 25px;\n    color: ",";\n"]);return q=function(){return e},e}function X(){let e=(0,i._)(["\n"]);return X=function(){return e},e}function J(){let e=(0,i._)(["\n    font-size: 24px;\n    color: ",";\n"]);return J=function(){return e},e}function Q(){let e=(0,i._)(["\n    display: flex;\n    flex-direction: column;\n    gap: 20px;\n    min-height: 500px;\n    border-radius: 10px;\n    padding: 1rem;\n    margin-bottom: 20px;\n"]);return Q=function(){return e},e}let Z={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:500,bgcolor:"background.paper",boxShadow:24,padding:"15px",borderRadius:"8px"},$=r.default.div(_(),(0,v.extrasmall)({width:"95% !important"}));r.default.span(H(),e=>null==e?void 0:e.color),r.default.div(E()),r.default.div(N(),e=>e.sz,e=>e.bg||"#fff",e=>e.cl||"#000"),r.default.div(R()),r.default.div(A()),r.default.div(Y(),e=>e.sz,u.mainColor),r.default.div(M(),u.mainColor),r.default.div(W()),r.default.div(F()),r.default.div(O()),r.default.div(P());let ee=r.default.div(B()),et=r.default.span(L()),en=r.default.div(K()),ei=r.default.div(U(),u.mainColor),el=r.default.div(G()),eo=r.default.div(V(),(0,v.extrasmall)({flexDirection:"column",gap:"30px"})),er=r.default.span(q(),u.mainColor),ea=r.default.div(X()),ed=r.default.div(J(),u.mainColor),es=r.default.div(Q());t.default=()=>{let e=(0,s.useSelector)(e=>e.hourSection),t=(0,s.useSelector)(e=>{var t;return null==e?void 0:null===(t=e.section)||void 0===t?void 0:t.section}),n=(0,s.useSelector)(e=>{var t;return null==e?void 0:null===(t=e.user)||void 0===t?void 0:t.user}),[i,r]=(0,o.useState)(null),[h,v]=(0,o.useState)([0,0]),[_,H]=(0,o.useState)(!0),[E,N]=(0,o.useState)([]),[R,A]=(0,o.useState)([]),[Y,M]=(0,o.useState)(!1),[W,F]=(0,o.useState)([]),[O,P]=(0,o.useState)(0),[B,L]=(0,o.useState)(!1),[K,U]=(0,o.useState)({}),{headers:G}=(0,u.getHeaders)(),V=(0,s.useDispatch)(),{request:q}=(0,x.useHttp)(),X=()=>L(!1);(0,o.useEffect)(()=>{(null==t?void 0:t.id)&&I.default.get("/timeTableByWeekOfYear/getTeacherTimeTableAndStatisticsForKafedra",{params:{kafedraId:null==t?void 0:t.id,year:p()(u.serverTimeStorage).format("YYYY"),month:p()(u.serverTimeStorage).format("MM"),day:p()(u.serverTimeStorage).format("D"),week:p()(u.serverTimeStorage).isoWeek(),weekday:p()(u.serverTimeStorage).format("d")}}).then(e=>{var t;F(null==e?void 0:null===(t=e.data)||void 0===t?void 0:t.obj.reduce((e,t)=>{let n=e.find(e=>e.teacherData.id===t.teacherData.id);return n?n.shows=[...n.shows,...t.shows]:e.push(t),e},[]))}).catch(e=>{F([])})},[]),(0,o.useEffect)(()=>{U(t)},[t]);let J=()=>{P(setInterval(()=>{Q()},6e4))};(0,o.useEffect)(()=>{t&&r(null==t?void 0:t.id)},[t]),(0,o.useEffect)(()=>{(null==t?void 0:t.id)&&I.default.get("/kafedra/getComeCountTodayStatistics?id="+(null==t?void 0:t.id),{headers:G}).then(e=>{var t,n,i,l,o,r,a,d,s,u,c,f;let h=[];(null==e?void 0:null===(n=e.data)||void 0===n?void 0:null===(t=n.obj)||void 0===t?void 0:t.comeCount)!==null?h.push(null==e?void 0:null===(l=e.data)||void 0===l?void 0:null===(i=l.obj)||void 0===i?void 0:i.comeCount):h.push(0),(null==e?void 0:null===(r=e.data)||void 0===r?void 0:null===(o=r.obj)||void 0===o?void 0:o.comeCount)!==null?h.push((null==e?void 0:null===(d=e.data)||void 0===d?void 0:null===(a=d.obj)||void 0===a?void 0:a.allCount)-(null==e?void 0:null===(u=e.data)||void 0===u?void 0:null===(s=u.obj)||void 0===s?void 0:s.comeCount)):h.push(null==e?void 0:null===(f=e.data)||void 0===f?void 0:null===(c=f.obj)||void 0===c?void 0:c.allCount),v(()=>h),H(!1)}).catch(e=>{})},[]),(0,o.useEffect)(()=>{Q(),J()},[]),(0,o.useEffect)(()=>{(null==R?void 0:R.length)===0?M(!0):M(!1)},[R]);let Q=async()=>{await I.default.get("/user/notification?week=".concat(p()(u.serverTimeStorage).isoWeek(),"&year=").concat(p()(u.serverTimeStorage).year()),{headers:G}).then(e=>{var t;N(null==e?void 0:null===(t=e.data)||void 0===t?void 0:t.obj)}).catch(e=>{M(!0)})};(0,o.useEffect)(()=>{clearInterval(O)},[Y]),(0,o.useEffect)(()=>{(null==E?void 0:E.length)===0?clearInterval(O):eu(W)},[E,W]);let eu=t=>{let n=[];null==E||E.map((i,l)=>{var o,r;let a=null==t?void 0:t.find(e=>{var t;return(null==e?void 0:null===(t=e.teacherData)||void 0===t?void 0:t.id)===(null==i?void 0:i.id)}),d=new Date(u.serverTimeStorage);d.setHours(d.getHours(),0,0);let s=null===(o=e.find(e=>p()(e.start).format("HH:mm:ss")===p()(d).format("HH:mm:ss")))||void 0===o?void 0:o.number;(null==a?void 0:null===(r=a.shows)||void 0===r?void 0:r.find(e=>(null==e?void 0:e.hourNumber)===s+2))&&n.push(i)}),0!==n.length?A(n):A([])},ec=async()=>{(null==K?void 0:K.name.trim().length)!==0&&n&&await d.default.put("".concat(u.BASE_URL,"/kafera-mudiri/changeNameOfKafedra"),K,{headers:G}).then(e=>{var t;z.toast.success(null==e?void 0:null===(t=e.data)||void 0===t?void 0:t.message),V((0,T.fetchSection)(q)),X()}).catch(e=>{var t,n;z.toast.error(null==e?void 0:null===(n=e.response)||void 0===n?void 0:null===(t=n.data)||void 0===t?void 0:t.message)})};return(0,l.jsxs)(es,{children:[(0,l.jsx)(ed,{children:(0,l.jsxs)(eo,{children:[(0,l.jsx)(ei,{children:(0,l.jsxs)(en,{children:[(0,l.jsx)(b.default,{onClick:()=>L(!0),sx:{position:"absolute",top:0,right:0},title:"change kafedra name",children:(0,l.jsx)(m.BsThreeDotsVertical,{})}),(0,l.jsx)(et,{children:(0,l.jsx)(c.GiTeacher,{})}),(0,l.jsx)(ea,{children:(0,l.jsx)(er,{children:null==t?void 0:t.name})})]})}),(0,l.jsxs)(el,{children:[!Y&&0!==R.length&&(0,l.jsx)(ee,{children:(0,l.jsx)(f.default,{data:R})}),null!==i&&(null==h?void 0:h.reduce((e,t)=>e+t))?(0,l.jsx)(a.default,{seriesK:h,isSpinner:_}):"Not exists teachers"]})]})}),(0,l.jsx)("hr",{style:{width:"100%",minHeight:"2px",backgroundColor:u.mainColor}}),(0,l.jsx)(D.default,{}),(0,l.jsx)(y.default,{open:B,"aria-labelledby":"modal-modal-title","aria-describedby":"modal-modal-description",children:(0,l.jsxs)(w.default,{sx:Z,component:$,children:[(0,l.jsxs)(w.default,{display:"flex",justifyContent:"space-between",alignItems:"center",children:[(0,l.jsx)(S.default,{variant:"h6",color:u.mainColor,mt:1,children:"Update department name"}),(0,l.jsxs)(b.default,{onClick:X,children:[" ",(0,l.jsx)(j.IoClose,{size:22})]})]}),(0,l.jsxs)(w.default,{children:[(0,l.jsx)(C.Stack,{sx:{height:"150px",padding:"0 10px"},direction:"row",justifyContent:"center",alignItems:"center",children:(0,l.jsx)(g.default,{fullWidth:!0,required:!0,value:null==K?void 0:K.name,label:"Department",onChange:e=>U(t=>({...t,name:e.target.value}))})}),(0,l.jsxs)(C.Stack,{direction:"row",spacing:2,justifyContent:"flex-end",alignItems:"center",children:[(0,l.jsx)(k.default,{variant:"outlined",onClick:X,children:"Cancel"}),(0,l.jsx)(k.default,{variant:"contained",onClick:ec,children:"Update"})]})]})]})})]})}},31942:function(e,t,n){"use strict";n.r(t),n.d(t,{default:function(){return v}});var i=n("58865"),l=n("85893"),o=n("16356");n("67294");var r=n("71893"),a=n("53432"),d=n("16393"),s=n("97367"),u=n("55693"),c=n("89589"),f=n("74113"),h=n("51649"),p=n("57024");function x(){let e=(0,i._)(["\n","\n"]);return x=function(){return e},e}function m(){let e=(0,i._)(["\n  width: 100% ;\n  display: flex;\n  gap: 10px;\n  margin: 5px !important;\n"]);return m=function(){return e},e}function v(e){let{sty:t,data:n,setExpanded:i,openColor:r,isDefault:p,title:x,openTable:m}=e,v=[{field:"id",headerName:"ID",width:40,editable:!1},{field:"fullName",headerName:"Name",type:"string",width:300,editable:!1,renderCell:e=>{var t,n,i;return(0,l.jsxs)(j,{children:[(0,l.jsx)("img",{src:(null==e?void 0:null===(t=e.row)||void 0===t?void 0:t.photo)?d.BASE_URL+"/attachment/download/"+(null==e?void 0:null===(i=e.row)||void 0===i?void 0:null===(n=i.photo)||void 0===n?void 0:n.id):"",width:"50px",height:"50px",alt:null==e?void 0:e.value,style:{borderRadius:"50%"}}),(0,l.jsx)("span",{style:{display:"flex",alignItems:"center",justifyContent:"center",fontSize:"14px"},children:null==e?void 0:e.value})]})}},{field:"login",headerName:"Login",type:"string",width:300,editable:!1},{field:"email",headerName:"Email",type:"string",width:300,editable:!1},{field:"rfid",headerName:"RFID",type:"string",width:300,editable:!1},{field:"passport",headerName:"Passport",type:"string",width:300,editable:!1},{field:"positions",headerName:"Shtat birligi va lavozimi",type:"string",width:300,editable:!1}];return(0,l.jsx)(s.default,{open:m,onClose:i,"aria-labelledby":"modal-modal-title","aria-describedby":"modal-modal-description",children:(0,l.jsxs)(u.default,{sx:g,component:b,children:[(0,l.jsxs)(u.default,{sx:{display:"flex",justifyContent:"space-between",alignItems:"center",mb:1},children:[(0,l.jsxs)(c.default,{id:"modal-modal-title",variant:"h6",component:"h2",sx:{color:p?"#0096DB":r},children:[x,"-",n.length]}),(0,l.jsx)(f.default,{onClick:i,children:(0,l.jsx)(h.IoMdClose,{})})]}),(0,l.jsx)(u.default,{children:m?(0,l.jsx)(o.DataGrid,{density:"comfortable",style:{width:"100%",minHeight:"600px!important",borderRadius:"0.3rem",zIndex:"1000000000000000",backgroundColor:"#fff"},columns:v,rows:n,components:{Toolbar:o.GridToolbar},autoHeight:!0,pageSize:5,rowsPerPageOptions:[5],initialState:{...n.initialState,columns:{columnVisibilityModel:{id:!1,login:!1,card:!1,rfid:!1,email:!1,passport:!1}}}}):(0,l.jsx)(a.default,{})})]})})}let g={position:"absolute",top:"50%",left:"50%",transform:"translate(-50%, -50%)",width:500,minHeight:400,bgcolor:"background.paper",border:"none",outline:"none",boxShadow:24,borderRadius:1,p:2},b=r.default.div(x(),(0,p.extrasmall)({width:"95% !important"})),j=r.default.div(m())},44178:function(e,t,n){"use strict";n.r(t),n("33948");var i=n("85893"),l=n("67294"),o=n("90650"),r=n("31942"),a=n("70231");let d={animationOne:{scale:1.1,transition:{yoyo:1/0,duration:.25}},hover:{scale:1,textShadow:"0 0 8px rgb(255,255,255)",boxShadow:"0 0 8px #f00"},focus:{scale:.9}};t.default=e=>{let{data:t,count:n}=e,[s,u]=(0,l.useState)(!1);return(0,i.jsxs)(o.AnimateSharedLayout,{children:[s&&(0,i.jsx)(r.default,{sty:{left:"10px"},setExpanded:()=>u(!1),isDefault:!1,openColor:"#f00",title:"Teachers who are late for class",openTable:(null==t?void 0:t.length)!==0,data:t}),(0,i.jsx)(o.motion.div,{layoutId:"kafedraId",style:{width:"100%",height:"100%",display:"flex",alignItems:"center",justifyContent:"center",flexDirection:"column"},children:(0,i.jsxs)(o.motion.button,{style:{width:"80px",minHeight:"80px",display:"flex",alignItems:"center",justifyContent:"center",color:"#fff",backgroundColor:"#f00",border:"none",fontSize:"24px",borderRadius:"50%"},variants:d,animate:"animationOne",whileHover:"hover",onClick:()=>u(!0),children:[(0,i.jsx)(a.FaTriangleExclamation,{}),"\xa0",null==t?void 0:t.length]})})]})}},44924:function(e,t,n){"use strict";n.r(t),n.d(t,{default:function(){return s}}),n("88449"),n("19894"),n("2490"),n("57640"),n("9924"),n("33792"),n("31672"),n("59461");var i=n("85893");n("67294");var l=n("50533"),o=n("7104"),r=n("30381"),a=n.n(r),d=n("55693");function s(e){let{data:t,enterRoomData:n,dateInput:r,serverTime:s}=e,u=(0,l.useSelector)(e=>e.hourSection),c=null==n?void 0:n.filter(e=>{var t;return(null==e?void 0:null===(t=e.rooms)||void 0===t?void 0:t.length)>0}).map(e=>e.rooms).flat(),f=(e,t,n)=>{var l;let d=a()(e),c=a()(null===(l=u.find(e=>e.number===t))||void 0===l?void 0:l.start).date(d.date()).diff(d,"milliseconds"),f=a().utc(Math.abs(c)).format("mm:ss"),h=a()(r).set({hour:a()(n).hours(),minute:a()(n).minutes()});if(a()(s).isAfter(h)){if(!e)return(0,i.jsx)(o.Chip,{label:"Missed",color:"error",size:"small"});if(c<0)return(0,i.jsx)(o.Tooltip,{title:d.format("HH:mm"),arrow:!0,placement:"top",children:(0,i.jsx)(o.Chip,{label:(0,i.jsx)("p",{style:{fontSize:11},children:f}),color:"warning",size:"small"})});else if(c>0)return(0,i.jsx)(o.Tooltip,{title:d.format("HH:mm"),arrow:!0,placement:"top",children:(0,i.jsx)(o.Chip,{label:(0,i.jsx)("p",{style:{fontSize:11},children:f}),color:"success",size:"small"})});else return(0,i.jsx)(o.Chip,{label:"On time",color:"default",size:"small"})}};return(0,i.jsx)(o.TableContainer,{component:o.Paper,sx:{overflow:"auto"},children:(0,i.jsxs)(o.Table,{sx:{minWidth:2200},children:[(0,i.jsx)(o.TableHead,{children:(0,i.jsxs)(o.TableRow,{children:[(0,i.jsx)(o.TableCell,{align:"center",sx:{position:"sticky",left:0,backgroundColor:"#fff",zIndex:1,borderRight:"1px solid rgba(224, 224, 224, 1)",maxWidth:150},children:(0,i.jsx)("strong",{children:"Teachers"})}),u.map(e=>(0,i.jsx)(o.TableCell,{align:"center",sx:{borderRight:"1px solid rgba(224, 224, 224, 1)",width:250},children:(0,i.jsx)("strong",{children:e.hour})},e.number))]})}),(0,i.jsx)(o.TableBody,{children:null==t?void 0:t.map(e=>{var t;let n=c.filter(t=>t.id===e.teacherData.id);return(0,i.jsxs)(o.TableRow,{children:[(0,i.jsx)(o.TableCell,{align:"center",sx:{position:"sticky",left:0,backgroundColor:"#f8f8f8",zIndex:1,borderRight:"1px solid rgba(224, 224, 224, 1)",width:200,height:120},children:null===(t=e.teacherData)||void 0===t?void 0:t.fullName}),u.map(t=>{var l,r,a,s;let u=null==e?void 0:e.shows.find(e=>e.hourNumber===t.number),c=null==n?void 0:n.find(t=>(null==t?void 0:t.room.substring(0,5))===(null==u?void 0:u.room.substring(0,5))&&t.id===e.teacherData.id),h=null==c?void 0:null===(r=c.times)||void 0===r?void 0:null===(l=r.filter(e=>e.section===t.number))||void 0===l?void 0:l.sort((e,t)=>e.time-t.time)[0];return(0,i.jsx)(o.TableCell,{align:"center",sx:{position:"relative",borderRight:"1px solid rgba(224, 224, 224, 1)",width:200},children:u&&(0,i.jsxs)(i.Fragment,{children:[(0,i.jsx)(o.Typography,{sx:{position:"absolute",top:5,left:"50%",transform:"translateX(-50%)"},fontSize:14,fontWeight:"bold",children:u.room.substring(0,5)}),(0,i.jsx)(o.Typography,{fontSize:10,children:null==u?void 0:u.lessonName}),(0,i.jsx)(d.default,{sx:{position:"absolute",bottom:5,right:5},children:f(null==h?void 0:h.time,null==h?void 0:h.section,t.start)}),(0,i.jsx)(d.default,{sx:{position:"absolute",bottom:5,left:5},children:(0,i.jsx)(o.Tooltip,{title:(null==u?void 0:null===(a=u.groups)||void 0===a?void 0:a.length)>1&&(null==u?void 0:u.groups.join(",")),arrow:!0,placement:"top",children:(0,i.jsx)(o.Chip,{label:(0,i.jsxs)("p",{style:{fontSize:10,fontWeight:"bold"},children:[null==u?void 0:u.groups[0],(null==u?void 0:null===(s=u.groups)||void 0===s?void 0:s.length)>1&&"..."]}),color:"default",size:"small"})})})]})},"".concat(e.teacherData.id,"-").concat(t.number))})]},e.teacherData.id)})})]})})}},66270:function(e,t,n){"use strict";n.r(t),n.d(t,{default:function(){return p}}),n("33948"),n("25387"),n("72608"),n("2490"),n("31672"),n("59461"),n("57658");var i=n("85893"),l=n("67294"),o=n("50533"),r=n("14628"),a=n("96413"),d=n("7104"),s=n("30381"),u=n.n(s),c=n("93065"),f=n("44924"),h=n("53432");function p(){let e=(0,o.useSelector)(e=>e.serverTime.time),t=(0,o.useSelector)(e=>{var t;return null==e?void 0:null===(t=e.section)||void 0===t?void 0:t.section}),[n,s]=(0,l.useState)([]),[p,x]=(0,l.useState)(null),[m,v]=(0,l.useState)(e?u()(e):u()()),[g,b]=(0,l.useState)(!1),[j,y]=(0,l.useState)("");(0,l.useEffect)(()=>{e&&v(u()(e))},[e]),(0,l.useEffect)(()=>{m&&w(m)},[]);let w=async e=>{if((null==t?void 0:t.id)&&e.isValid()){b(!0),y("");try{let{data:n}=await c.default.get("/timeTableByWeekOfYear/getTeacherTimeTableAndStatisticsForKafedra",{params:{kafedraId:t.id,year:e.format("YYYY"),month:e.format("MM"),day:e.format("D"),week:e.isoWeek(),weekday:e.format("d")}}),{obj:i=[],secondObj:l=null,time:o}=n;localStorage.setItem("serverTime",o);let r=i.reduce((e,t)=>{let n=e.find(e=>e.teacherData.id===t.teacherData.id);return n?n.shows=[...n.shows,...t.shows]:e.push(t),e},[]);s(r),x(l)}catch(e){y(e.response.data.message)}finally{b(!1)}}},S=e=>{let{content:t}=e;return(0,i.jsx)(d.Card,{children:(0,i.jsx)(d.CardContent,{children:(0,i.jsx)(d.Box,{sx:{display:"flex",justifyContent:"center"},children:t})})})};return(0,i.jsxs)("div",{children:[(0,i.jsx)(r.LocalizationProvider,{dateAdapter:a.AdapterMoment,children:(0,i.jsx)(r.DatePicker,{label:"Select Date",inputFormat:"DD/MM/YYYY",value:m,onChange:e=>{let t=u()(e);v(t),w(t)},renderInput:e=>(0,i.jsx)(d.TextField,{...e,sx:{width:165,margin:"10px 0"},size:"small"})})}),g?(0,i.jsx)(S,{content:(0,i.jsx)(h.default,{})}):j?(0,i.jsx)(S,{content:j}):n.length?(0,i.jsx)(f.default,{data:n,dateInput:m,enterRoomData:p,serverTime:e}):(0,i.jsx)(S,{content:"No timetable data available"})]})}}}]);